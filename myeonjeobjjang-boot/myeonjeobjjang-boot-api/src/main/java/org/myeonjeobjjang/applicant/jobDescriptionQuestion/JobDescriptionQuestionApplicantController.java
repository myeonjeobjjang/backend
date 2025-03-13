package org.myeonjeobjjang.applicant.jobDescriptionQuestion;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.JobDescriptionQuestionService;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.IntegrationJobDescriptionQuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applicant/jobDescriptionQuestions")
@RequiredArgsConstructor
public class JobDescriptionQuestionApplicantController {
    private final JobDescriptionQuestionService jobDescriptionQuestionService;

    @GetMapping("/{jobDescriptionQuestionId}")
    public ResponseEntity<IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse> get(@PathVariable Long jobDescriptionQuestionId) {
        return ResponseEntity.ok(jobDescriptionQuestionService.get(jobDescriptionQuestionId));
    }

    public record JobDescriptionQuestionInfoResponse(
        List<IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse> jobDescriptionQuestionInfos
    ) {}

    @GetMapping("/jobDescriptions/{jobDescriptionId}")
    public ResponseEntity<JobDescriptionQuestionInfoResponse> findByJobDescriptionId(@PathVariable Long jobDescriptionId) {
        return ResponseEntity.ok(new JobDescriptionQuestionInfoResponse(jobDescriptionQuestionService.findByJobDescriptionId(jobDescriptionId)));
    }
}
