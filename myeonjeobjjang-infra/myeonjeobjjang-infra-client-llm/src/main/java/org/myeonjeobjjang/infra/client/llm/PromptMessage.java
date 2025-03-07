package org.myeonjeobjjang.infra.client.llm;

public enum PromptMessage {
    TechRecruitment_System("""
        Answer with next JSON format. EXCEPT markdown format.
        {
          "technicalCapabilityKeywords" : ""
          "projectExperiences" :  ""
          "specialty" : ""
          "mattersRequiringVerificationByTheApplicant" : ""
          "reasonsForTheApplicantsInadequacy" : ""
        }
        
        You are an assistant for talent recruitment tasks.
        Use the following pieces of retrieved context to answer the question.
        Answer based on context, but point out any doubts.
        If you don't know the answer, just say that you don.t know.
        Answer in Korean.
        """),
    TechRecruitment_UserQuestion("""
        Is this person suitable as a %s server developer?
        More specifically, it tells you the skills your project is based on.
        This person told me what role they all had on the project.
        """),
    TechRecruitment_UserMessagePrompt("""
        #Context :
        %s
        #Question :
        "%s"
        """)
    ;
    public final String message;
    PromptMessage(String message) {
        this.message = message;
    }
}
