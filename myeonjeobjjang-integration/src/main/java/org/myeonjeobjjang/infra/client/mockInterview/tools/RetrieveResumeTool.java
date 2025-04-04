package org.myeonjeobjjang.infra.client.mockInterview.tools;

import org.myeonjeobjjang.domain.core.vectordb.VectorDBService;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RetrieveResumeTool {
    @Autowired
    VectorDBService vectorDBService;
    private static final int TOP_K = 3;

    @Tool(description = """
        This tool retrieves specific information from the interviewee's resume. Use this tool to extract relevant details, such as work experience, education, or skills, to create more tailored and context-aware prompts during the mock interview.
        """)
    public List<Document> retrieveResume(
        @ToolParam(description = """
            This parameter specifies the subject or theme you want to extract from the resume, such as 'work experience', 'educational background', or 'technical skills'.
            """)
        String query,
        ToolContext toolContext
    ) {
        Map<String, Object> documentMetadata = new HashMap<>();
        documentMetadata.put("category", "resume");
        documentMetadata.put("conversation_id", toolContext.getContext().get("conversationId"));
        return vectorDBService.retrievedDocs(query, TOP_K, documentMetadata);
    }
}
