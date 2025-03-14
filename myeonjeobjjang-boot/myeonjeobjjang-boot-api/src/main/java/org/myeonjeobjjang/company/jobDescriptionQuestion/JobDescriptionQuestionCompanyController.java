package org.myeonjeobjjang.company.jobDescriptionQuestion;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.company.jobDescriptionQuestion.dto.JobDescriptionQuestionCompanyRequest.JobDescriptionQuestionCreateCompanyRequest;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.JobDescriptionQuestionService;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionRequest.JobDescriptionQuestionCreateRequest;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company/jobDescriptionQuestions")
@RequiredArgsConstructor
public class JobDescriptionQuestionCompanyController {
    private final JobDescriptionQuestionService jobDescriptionQuestionService;

    @PostMapping
    public ResponseEntity<JobDescriptionQuestionResponse.JobDescriptionQuestionInfoResponse> create(
        @RequestBody @Validated JobDescriptionQuestionCreateCompanyRequest request
    ) {
        return ResponseEntity.ok(jobDescriptionQuestionService.create(new JobDescriptionQuestionCreateRequest(
            request.questionNumber(), request.question(), request.jobDescriptionId()
        )));
    }
}
