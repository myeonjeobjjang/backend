package org.myeonjeobjjang.infra.client.mockInterview.supervisorPattern;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.client.mockInterview.memory.ConversationLogRDBReadOnlyMemory;
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

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MockInterviewSubordinateAgent {
    private final OpenAiChatModel openAiChatModel;
    private final ConversationLogRDBReadOnlyMemory rdbChatReadOnlyMemory;

    private final int CHAT_HISTORY_WINDOW_SIZE = 20;

    private final GetCompanyInfoTool getCompanyInfoTool;
    private final GetIndustryInfoTool getIndustryInfoTool;
    private final GetJobDescriptionInfoTool getJobDescriptionInfoTool;
    private final GetJobPostingInfoTool getJobPostingInfoTool;
    private final RetrieveCoverLetterTool retrieveCoverLetterTool;
    private final RetrieveResumeTool retrieveResumeTool;

    /**
     * 인사 전문가 (HR Specialist)<BR>
     * - 역할: 면접 과정 관리, 조직 적합성 평가<BR>
     * - 태도: 친절하고 편안한 분위기를 조성하며, 지원자의 긴장을 풀어줌<BR>
     * - 기대: 조직 문화와 가치에 적합한 인재인지 확인. 솔직함과 진지한 태도를 중요시함
     */
    @Tool(name = "HRSpecialist", description = """
        This tool represents an HR Specialist who evaluates the candidate's organizational fit and cultural alignment. The HR Specialist provides insights into the company's values, culture, and how the candidate aligns with these aspects. Use this tool to ask questions related to organizational compatibility, soft skills, and overall fit within the company's environment.
        """)
    public String hrSpecialList(
        ToolContext toolContext,
        String message
    ) {
        return getAgentResponse(toolContext, message, """
            You are an HR Specialist participating in a mock interview process. Your role is to evaluate the candidate's organizational fit and cultural alignment based on the information provided.
                            
            When responding:
            1. Focus on assessing whether the candidate aligns with the company's values and culture.
            2. Use tools such as company information, industry data, and job descriptions to provide detailed insights.
            3. Provide clear and actionable feedback for the Hiring Supervisor.
                            
            Example question from Supervisor: "Does this candidate's experience align with our company's values?"
            """);
    }

    /**
     * 실무진 (Team Leader/Technical Expert)<BR>
     * - 역할: 직무 관련 기술 및 경험 평가<BR>
     * - 태도: 직무 관련 질문을 통해 지원자의 전문성을 검증하며, 논리적이고 실질적인 대화를 선호<BR>
     * - 기대: 문제 해결 능력, 기술적 역량, 그리고 협업 가능성을 기대
     */
    @Tool(name = "TechnicalExpert", description = """
        This tool represents a Technical Expert who assesses the candidate's technical skills, problem-solving abilities, and job-specific expertise. The Technical Expert provides detailed feedback on the candidate's technical qualifications based on their resume, cover letter, and job description. Use this tool to ask questions about technical proficiency, skill validation, and task-specific capabilities.
        """)
    public String technicalExpert(
        ToolContext toolContext,
        String message
    ) {
        return getAgentResponse(toolContext, message, """
            You are a Technical Expert evaluating a candidate's technical skills and problem-solving abilities during a mock interview process.
                            
            When responding:
            1. Focus on assessing the candidate's technical expertise and ability to perform job-specific tasks.
            2. Use tools such as job descriptions and resumes to validate their qualifications.
            3. Provide detailed feedback about their technical strengths and areas for improvement.
                            
            Example question from Supervisor: "Based on their resume, does this candidate have sufficient experience with [specific skill]?"
            """);
    }

    /**
     * 전문 분야 전문가(Subject Matter Expert, SME)<BR>
     * - 주요 역할: 해당 직무의 전문 지식을 바탕으로 지원자의 기술적 역량과 적합성을 평가합니다.<BR>
     * - SME는 직무와 관련된 심층적인 질문을 던지고, 지원자의 기술적 능력을 검증하며, 채용 관리자에게 피드백을 제공합니다.
     */
    @Tool(name = "SubjectMatterExpert", description = """
        This tool represents a Subject Matter Expert who evaluates the candidate's domain-specific knowledge and practical application of skills. The SME provides insights into the candidate's expertise in specialized areas relevant to the job role. Use this tool to ask questions about deep technical knowledge, industry-specific trends, and problem-solving within the domain.
        """)
    public String subjectMatterExpert(
        ToolContext toolContext,
        String message,
        @ToolParam(description = """
            Specify the domain or specific task that the Subject Matter Expert should evaluate. This parameter helps focus the evaluation on a particular area of expertise relevant to the job role. Examples include "financial modeling," "machine learning algorithms," "supply chain optimization," or "legal contract drafting.
            """)
        String specificTask
    ) {
        return getAgentResponse(toolContext, message, String.format("""
            You are a Subject Matter Expert evaluating a candidate's proficiency in a specific domain during a mock interview process.
                            
            When responding:
            1. Focus on assessing the candidate's domain knowledge and ability to apply it in practical scenarios.
            2. Use tools such as job descriptions, resumes, and cover letters to provide evidence-based feedback.
            3. Provide specific examples or scenarios where their expertise may be applied.
                            
            Example question from Supervisor: "Can you evaluate whether this candidate's domain knowledge is sufficient for %s?"
            """, specificTask));
    }

    /**
     * 외부 패널 멤버 (External Panel Member)<BR>
     * - 역할: 객관적 평가 제공.<BR>
     * - 태도: 객관적이고 공정한 관점으로 지원자를 평가하며 독립적인 의견을 제공<BR>
     * - 기대: 편향되지 않은 평가를 통해 지원자의 다각적 적합성을 분석<BR>
     * - 주요 역할: 조직 외부 또는 다른 부서에서 온 구성원이 지원자를 평가함으로써 새로운 관점과 객관성을 제공합니다.<BR>
     * - 이는 편향을 줄이고 더 균형 잡힌 결정을 내리는 데 유용합니다.
     */
    @Tool(name = "ExternalPanelMember", description = """
        This tool represents an External Panel Member who provides an unbiased evaluation of the candidate's overall suitability for the role. The External Panel Member offers objective feedback on communication skills, adaptability, and potential for growth. Use this tool to ask questions aimed at gaining a balanced perspective on the candidate's strengths and weaknesses.
        """)
    public String externalPanelMember(
        ToolContext toolContext,
        String message
    ) {
        return getAgentResponse(toolContext, message, """
            You are an External Panel Member providing an unbiased evaluation of a candidate during a mock interview process.
                            
            When responding:
            1. Focus on providing an objective assessment of the candidate's overall suitability for the role.
            2. Consider factors such as communication skills, adaptability, and potential for growth.
            3. Provide balanced feedback that highlights both strengths and weaknesses.
                            
            Example question from Supervisor: "Based on their responses so far, how would you rate this candidate's communication skills?"
            """);
    }

    /**
     * 미래 동료 (Potential Coworker/Team Member)<BR>
     * - 주요 역할: 팀 내 문화와 협업 가능성을 평가합니다.<BR>
     * - 태도: 지원자가 팀 내에서 잘 융화될 수 있는지 확인하며 협력적인 대화를 나눔<BR>
     * - 기대: 팀워크와 커뮤니케이션 능력을 중점적으로 봄<BR>
     * - 미래 동료와의 면접은 지원자가 팀에 잘 융화될 수 있는지 확인하고, 지원자에게 팀 환경에 대한 정보를 제공하는 기회를 제공합니다.<BR>
     */
    @Tool(name = "TeamMember", description = """
        This tool represents a Team Member who evaluates the candidate's ability to collaborate effectively within a team environment. The Team Member provides feedback on interpersonal skills, teamwork capabilities, and how well the candidate might integrate into existing team dynamics. Use this tool to ask questions about collaboration experiences and cultural fit within the team.
        """)
    public String teamMember(
        ToolContext toolContext,
        String message
    ) {
        return getAgentResponse(toolContext, message, """
            You are a Team Member evaluating whether a candidate would fit well within your team during a mock interview process.
                            
            When responding:
            1. Focus on assessing the candidate's teamwork abilities and interpersonal skills.
            2. Use tools such as resumes or cover letters to understand their past collaboration experiences.
            3. Provide feedback on how well they might integrate into your team dynamics.
                            
            Example question from Supervisor: "Does this candidate demonstrate strong teamwork skills based on their past experience?"
            """);
    }

    /**
     * 임원 (Executive)<BR>
     * - 역할: 전략적 사고와 조직 적합성 평가<BR>
     * - 태도: 전략적 관점에서 지원자를 평가하며 심층적인 질문을 통해 리더십과 조직 적합성을 분석<BR>
     * - 기대: 미래 성장 가능성과 회사의 장기적인 목표에 부합하는 인재를 기대<BR>
     * - 주요 역할: 조직의 전략적 목표와 문화 적합성을 최종적으로 검토합니다.<BR>
     * - 임원 면접은 특히 중간 관리자 이상의 직책에서 중요하며, 조직 전체 관점에서 지원자를 평가합니다.
     */
    @Tool(name = "Executive", description = """
        This tool represents an Executive who assesses the candidate's strategic thinking, leadership potential, and alignment with organizational goals. The Executive provides high-level feedback on how the candidate can contribute to long-term success and growth within the company. Use this tool to ask questions about leadership qualities, strategic priorities, and vision alignment.
        """)
    public String executive(
        ToolContext toolContext,
        String message
    ) {
        return getAgentResponse(toolContext, message, """
            You are an Executive evaluating a candidate's strategic thinking and alignment with organizational goals during a mock interview process.
                            
            When responding:
            1. Focus on assessing the candidate's leadership potential and ability to contribute to long-term organizational success.
            2. Use tools such as job descriptions and company information to align your evaluation with strategic priorities.
            3. Provide high-level feedback that considers both immediate impact and future potential.
                            
            Example question from Supervisor: "Does this candidate demonstrate leadership qualities that align with our strategic goals?"
            """);
    }

    private String getAgentResponse(ToolContext toolContext, String userMessage, String systemMessage) {
        return ChatClient.builder(openAiChatModel).build()
            .prompt()
            .options(getOpenAiChatOptions(toolContext))
            .system(systemMessage)
            .advisors(
                getMessageChatReadOnlyMemoryAdvisor((String) toolContext.getContext().get("conversationId")),
                new SimpleLoggerAdvisor()
            )
            .user(userMessage)
            .tools(getCompanyInfoTool, getIndustryInfoTool, getJobDescriptionInfoTool, getJobPostingInfoTool, retrieveCoverLetterTool, retrieveResumeTool)
            .toolContext(toolContext.getContext())
            .call()
            .content();
    }

    private OpenAiChatOptions getOpenAiChatOptions(ToolContext toolContext) {
        return OpenAiChatOptions.builder()
            .temperature(1.0)
            .model(OpenAiApi.ChatModel.GPT_4_O)
            .metadata(Map.of("conversationId", (String) toolContext.getContext().get("conversationId")))
            .build();
    }

    private MessageChatMemoryAdvisor getMessageChatReadOnlyMemoryAdvisor(String conversationId) {
        return new MessageChatMemoryAdvisor(rdbChatReadOnlyMemory, conversationId, CHAT_HISTORY_WINDOW_SIZE);
    }
}
