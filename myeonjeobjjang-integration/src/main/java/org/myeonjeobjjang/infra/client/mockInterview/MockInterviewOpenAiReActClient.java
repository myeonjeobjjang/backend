package org.myeonjeobjjang.infra.client.mockInterview;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.client.mockInterview.dto.MockInterviewClientRequest.MockInterviewChatRequest;
import org.myeonjeobjjang.infra.client.mockInterview.tools.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MockInterviewOpenAiReActClient implements MockInterviewClient {
    private final OpenAiChatModel openAiChatModel;
    private final ConversationLogRDBMemory rdbChatMemory;

    private final int CHAT_HISTORY_WINDOW_SIZE = 20;

    private final GetCompanyInfoTool getCompanyInfoTool;
    private final GetIndustryInfoTool getIndustryInfoTool;
    private final GetJobDescriptionInfoTool getJobDescriptionInfoTool;
    private final GetJobPostingInfoTool getJobPostingInfoTool;
    private final RetrieveCoverLetterTool retrieveCoverLetterTool;
    private final RetrieveResumeTool retrieveResumeTool;

    private static Map<String, Object> toolObjectMap = new HashMap<>();
    private static Map<String, Method> toolMethodMap = new HashMap<>();

    /**
     * Tool의 ToolContext 사용전 파라미터 직접 제공 방식에 적합한 ReAct 패턴 에이전트<br>사용하려면 ToolContext에 적합하도록 수정 필요
     *
     * @param request MockInterviewChatRequest
     * @return String
     */

    @Deprecated
    @Override
    public String mockInterviewChat(MockInterviewChatRequest request) {
        String userMessage = request.userMessage();
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = getMessageChatMemoryAdvisor(request.conversationId());
        final String toolInfos = getToolInfos(
            getCompanyInfoTool,
            getIndustryInfoTool,
            getJobDescriptionInfoTool,
            getJobPostingInfoTool,
            retrieveCoverLetterTool,
            retrieveResumeTool
        );
        ToolContext toolContext = new ToolContext(Map.of(
            "industryId", request.industryId(),
            "companyId", request.companyId(),
            "jobDescriptionId", request.jobDescriptionId(),
            "jobPostingId", request.jobPostingId(),
            "conversationId", request.conversationId()
        ));
        ReActResponse response = null;
        while (!(Objects.requireNonNull(response = ChatClient.builder(openAiChatModel).build()
            .prompt()
            .options(OpenAiChatOptions.builder()
                .temperature(1.0)
                .model(OpenAiApi.ChatModel.GPT_4_O)
                .metadata(Map.of("conversationId", request.conversationId()))
                .build())
            .system(String.format(SYSTEM_PROMPT, request.industryId(), request.companyId(), request.jobDescriptionId(), request.jobPostingId(), request.conversationId(), toolInfos))
            .advisors(messageChatMemoryAdvisor, new SimpleLoggerAdvisor())
            .toolContext(toolContext.getContext())
            .user(userMessage)
            .call()
            .entity(ReActResponse.class)))
            .action()
            .equals("Final Answer")
        ) {
            Object object = toolObjectMap.get(response.action());
            Method method = toolMethodMap.get(response.action());
            Object[] arguments = response.action_input();
            Object invoke = null;
            try {
                invoke = method.invoke(object, arguments);
            } catch (IllegalAccessException | InvocationTargetException e) {
                return "invoke를 실패 했습니다.";
            }
            userMessage = "action_response : " + invoke.toString()
                .replace("{", "&#123;").replace("}", "&#125;")
            // 현재 Spring AI의 StringTemplate의 문제로 '{' 나 '}'를 사용할 경우 프롬프트가 제대로 생성되지 않고 invoke 오류가 생기고 있음
            // &#123; -> { , &#125; -> } 을 뜻하기 때문에 파싱에는 문제가 생기지 않으면서 gpt는 이해할 대체 방식이라 여겨 문자를 대치함
            ;
        }
        return response.action_input()[0];
    }

    private String getToolInfos(Object... objects) {
        StringBuffer sb = new StringBuffer();
        for (Object o : objects) {
            Class<?> clazz = o.getClass();
            for (Method m : clazz.getMethods()) {
                toolObjectMap.put(m.getName(), o);
                toolMethodMap.put(m.getName(), m);
                Tool toolAnnotation = m.getAnnotation(Tool.class);
                if (toolAnnotation == null) continue;
                sb.append(m.getName()).append(" (");
                sb.append(toolAnnotation.description()).append(") : Require Parameters (");
                Annotation[][] parameterAnnotations = m.getParameterAnnotations();
                int paramNum = 0;
                for (Annotation[] annotations : parameterAnnotations) {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof ToolParam) {
                            ToolParam toolParam = (ToolParam) annotation;
                            sb.append("\"arg").append(paramNum).append("\" : ");
                            sb.append(toolParam.description()).append(", ");
                        }
                    }
                }
                sb.append(")\n");
            }
        }
        return sb.toString();
    }

    private MessageChatMemoryAdvisor getMessageChatMemoryAdvisor(String conversationId) {
        return new MessageChatMemoryAdvisor(rdbChatMemory, conversationId, CHAT_HISTORY_WINDOW_SIZE);
    }

    public record ReActResponse(
        String action,
        String[] action_input
    ) {
    }

    // origin prompt : https://smith.langchain.com/hub/hwchase17/react-chat-json
    String SYSTEM_PROMPT = """
        Assistant is an experienced professional interviewer with decades of experience in technical recruitment. Assistant's role is to conduct realistic job interviews to assess candidates thoroughly. Assistant maintain a professional, analytical demeanor while verifying candidate qualifications through precise questioning.

        ### Core Interviewer Characteristics
        Demonstrate professionalism derived from extensive experience
        Analyze responses deeply to understand candidate's true intentions
        Maintain concise communication, avoiding unnecessary conversation
        Project a cool, analytical demeanor throughout the interview
        Verify experience authenticity through targeted follow-up questions
        Focus on technical assessment specific to the position requirements
        Ask only one-sentence questions at a time
        Thoroughly analyze each response before formulating Assistant's next question

        ### Interview Structure
        1.Background Verification
        Use resume similarity search to verify key qualifications
        Ask focused verification questions about background
        Follow up immediately on vague responses with requests for specific details
        2.Technical Assessment
        Use job role information to identify required technical skills
        Ask specific technical questions based on role requirements
        Increase question difficulty based on candidate responses
        3.Experience Verification
        Request concrete examples of relevant skills and experiences
        Use personal statement search to verify consistency with written materials
        Ask detailed follow-up questions to authenticate experiences mentioned
        4.Company/Industry Knowledge
        Test candidate's knowledge of the company and industry
        Assess understanding of how their skills apply to company needs
        Evaluate preparation and potential fit
        5.Brief Closure
        End with a professional, concise closing statement

        ### Question Guidelines
        Limit each question to exactly one sentence
        After each response, thoroughly analyze before proceeding to next question
        For vague answers, immediately request specific examples
        Verify technical skills through targeted, job-relevant questions
        Check consistency between verbal claims and written materials
        For each significant experience claimed, ask at least one verification follow-up question
        Look for specific details that only someone with genuine experience would know
        Assistant's primary objective is to conduct a challenging, thorough interview that effectively assesses the candidate's technical qualifications and experience authenticity without unnecessary conversation.

        Assistant has access to tools to retrieve specific information

        Answer Final Answer's return in Korean.

        TOOLS
        ------
        Assistant can ask the user to use tools to look up information that may be helpful in answering the users original question. The tools the human can use are:

        %s

        RESPONSE FORMAT INSTRUCTIONS
        ----------------------------

        When responding to me, please output a response in one of two formats:

        **Option 1:**
        Use this if you want the human to use a tool.
        Markdown code snippet formatted in the following schema:

        ```json
        {{
            "action": string, \\ The action to take. Must be one of {tool_names}
            "action_input": List<string> \\ The input to the action (e.g. ["arg0", "arg1", "arg2"])
        }}
        ```

        **Option #2:**
        Use this if you want to respond directly to the human. Markdown code snippet formatted in the following schema:

        ```json
        {{
            "action": "Final Answer",
            "action_input": List<string> \\ You should put what you want to return to use here (e.g. [final answer])
        }}
        ```
        """;
}
