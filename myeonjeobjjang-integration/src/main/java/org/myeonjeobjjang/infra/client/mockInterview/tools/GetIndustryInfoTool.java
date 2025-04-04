package org.myeonjeobjjang.infra.client.mockInterview.tools;

import org.myeonjeobjjang.domain.core.industry.entity.Industry;
import org.myeonjeobjjang.domain.core.industry.repository.IndustryRepository;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetIndustryInfoTool {
    @Autowired
    IndustryRepository industryRepository;

    @Tool(description = """
        This tool provides industry-specific information about the company involved in the ongoing interview. Use this tool whenever you need insights about the company's industry to craft better prompts or deliver more relevant and informed responses during the mock interview.
        """)
    public Industry getIndustryInfo(
        ToolContext toolContext
    ) {
        return industryRepository.findById((Long) toolContext.getContext().get("industryId")).orElse(null);
    }
}
