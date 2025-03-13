package org.myeonjeobjjang.company.jobDescriptionQuestion;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.JobDescriptionQuestionService;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.IntegrationJobDescriptionQuestionRequest;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.IntegrationJobDescriptionQuestionResponse;
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

    public record JobDescriptionQuestionCreateRequest(
        @Positive
        Long questionNumber,
        @NotEmpty
        String question,
        @Positive
        Long jobDescriptionId
    ) {}

    @PostMapping
    public ResponseEntity<IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse> create(@RequestBody @Validated JobDescriptionQuestionCreateRequest request) {
        return ResponseEntity.ok(jobDescriptionQuestionService.create(new IntegrationJobDescriptionQuestionRequest.IntegrationJobDescriptionQuestionCreateRequest(
            request.questionNumber(), request.question(), request.jobDescriptionId()
        )));
    }
}
