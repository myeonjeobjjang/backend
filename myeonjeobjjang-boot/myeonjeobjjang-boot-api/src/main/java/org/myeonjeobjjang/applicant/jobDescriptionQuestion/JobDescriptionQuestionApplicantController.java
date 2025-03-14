package org.myeonjeobjjang.applicant.jobDescriptionQuestion;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.applicant.jobDescriptionQuestion.dto.JobDescriptionQuestionApplicantResponse.JobDescriptionQuestionInfoApplicantResponse;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.JobDescriptionQuestionService;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionResponse.JobDescriptionQuestionInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applicant/jobDescriptionQuestions")
@RequiredArgsConstructor
public class JobDescriptionQuestionApplicantController {
    private final JobDescriptionQuestionService jobDescriptionQuestionService;

    @GetMapping("/{jobDescriptionQuestionId}")
    public ResponseEntity<JobDescriptionQuestionInfoResponse> get(@PathVariable Long jobDescriptionQuestionId) {
        return ResponseEntity.ok(jobDescriptionQuestionService.get(jobDescriptionQuestionId));
    }

    @GetMapping("/jobDescriptions/{jobDescriptionId}")
    public ResponseEntity<JobDescriptionQuestionInfoApplicantResponse> findByJobDescriptionId(@PathVariable Long jobDescriptionId) {
        return ResponseEntity.ok(JobDescriptionQuestionInfoApplicantResponse.toDto(
            jobDescriptionQuestionService.findByJobDescriptionId(jobDescriptionId)
        ));
    }
}
