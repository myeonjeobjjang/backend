package org.myeonjeobjjang.infra.client.mockInterview.tools;

import org.myeonjeobjjang.domain.core.jobPosting.entity.JobPosting;
import org.myeonjeobjjang.domain.core.jobPosting.repository.JobPostingRepository;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetJobPostingInfoTool {
    @Autowired
    JobPostingRepository jobPostingRepository;

    @Tool(description = """
        This tool provides detailed information about the job posting for the ongoing interview. Use this tool to access specific details from the job description, such as responsibilities, qualifications, and requirements, to create more accurate prompts or deliver tailored responses during the mock interview.
        """)
    public JobPosting getJobPostingInfo(
        ToolContext toolContext
    ) {
        return jobPostingRepository.findById((Long) toolContext.getContext().get("jobPostingId")).orElse(null);
    }
}
