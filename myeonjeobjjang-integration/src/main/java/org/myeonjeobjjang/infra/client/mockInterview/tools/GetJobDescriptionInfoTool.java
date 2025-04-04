package org.myeonjeobjjang.infra.client.mockInterview.tools;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescription.repository.JobDescriptionRepository;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetJobDescriptionInfoTool {
    @Autowired
    JobDescriptionRepository jobDescriptionRepository;

    @Tool(description = """
        This tool retrieves detailed information about the job position being discussed in the ongoing interview. Use this tool to access role-specific data (e.g., responsibilities, required skills) for crafting context-aware questions or evaluating responses more effectively during mock interviews.
        """)
    public JobDescription jobDescriptionInfo(
        ToolContext toolContext
    ) {
        return jobDescriptionRepository.findById((Long) toolContext.getContext().get("jobDescriptionId")).orElse(null);
    }
}
