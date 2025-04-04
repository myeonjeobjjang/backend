package org.myeonjeobjjang.infra.client.mockInterview.tools;

import org.myeonjeobjjang.domain.core.company.entity.Company;
import org.myeonjeobjjang.domain.core.company.repository.CompanyRepository;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetCompanyInfoTool {
    @Autowired
    CompanyRepository companyRepository;

    @Tool(description = """
        This tool provides detailed information about the company involved in the ongoing interview. Use this tool whenever you need specific company details to create better prompts or provide more tailored responses during the mock interview.
        """)
    public Company getCompanyInfo(
        ToolContext toolContext
    ) {
        return companyRepository.findById((Long) toolContext.getContext().get("companyId")).orElse(null);
    }
}
