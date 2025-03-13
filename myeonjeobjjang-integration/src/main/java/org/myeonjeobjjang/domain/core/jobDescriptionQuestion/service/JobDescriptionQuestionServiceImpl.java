package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescription.service.JobDescriptionService;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.entity.JobDescriptionQuestion;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.repository.JobDescriptionQuestionRepository;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.IntegrationJobDescriptionQuestionRequest;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.IntegrationJobDescriptionQuestionResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.myeonjeobjjang.domain.core.jobDescriptionQuestion.JobDescriptionQeustionErrorCode.DUPLICATE_JOB_DESCRIPTION_QUESTION;
import static org.myeonjeobjjang.domain.core.jobDescriptionQuestion.JobDescriptionQeustionErrorCode.JOB_DESCRIPTION_QUESTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class JobDescriptionQuestionServiceImpl implements JobDescriptionQuestionService {
    private final JobDescriptionQuestionRepository jobDescriptionQuestionRepository;

    private final JobDescriptionService jobDescriptionService;

    @Override
    public IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse create(IntegrationJobDescriptionQuestionRequest.IntegrationJobDescriptionQuestionCreateRequest request) {
        JobDescription jd = jobDescriptionService.findById(request.jobDescriptionId());
        if (jobDescriptionQuestionRepository.findJobDescriptionQuestionByJobDescriptionAndQuestionNumber(jd, request.questionNumber()).isPresent())
            throw new BaseException(DUPLICATE_JOB_DESCRIPTION_QUESTION);
        JobDescriptionQuestion newJobDescriptionQuestion = JobDescriptionQuestion.builder()
            .questionNumber(request.questionNumber())
            .question(request.question())
            .jobDescription(jd)
            .build();
        JobDescriptionQuestion savedJobDescriptionQuestion = jobDescriptionQuestionRepository.save(newJobDescriptionQuestion);
        return new IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse(
            savedJobDescriptionQuestion.getQuestionNumber(),
            savedJobDescriptionQuestion.getJobDescriptionQuestionId(),
            savedJobDescriptionQuestion.getQuestion(),
            savedJobDescriptionQuestion.getJobDescription().getJobDescriptionId());
    }

    @Override
    public IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse get(Long jobDescriptionId) {
        JobDescriptionQuestion jdq = jobDescriptionQuestionRepository.findById(jobDescriptionId)
            .orElseThrow(() -> new BaseException(JOB_DESCRIPTION_QUESTION_NOT_FOUND));
        return new IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse(
            jdq.getQuestionNumber(), jdq.getJobDescriptionQuestionId(), jdq.getQuestion(), jdq.getJobDescription().getJobDescriptionId()
        );
    }

    @Override
    public List<IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse> findByJobDescriptionId(Long jobDescriptionId) {
        JobDescription jd = jobDescriptionService.findById(jobDescriptionId);
        return findByJobDescription(jd);
    }

    @Override
    public List<IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse> findByJobDescription(JobDescription jobDescription) {
        List<JobDescriptionQuestion> jdql = jobDescriptionQuestionRepository.findByJobDescription(jobDescription, Sort.by("questionNumber").ascending());
        return jdql.stream().map(jdq ->
                new IntegrationJobDescriptionQuestionResponse.IntegrationJobDescriptionQuestionInfoResponse(
                    jdq.getQuestionNumber(),
                    jdq.getJobDescriptionQuestionId(),
                    jdq.getQuestion(),
                    jdq.getJobDescription().getJobDescriptionId()))
            .toList();
    }
}
