package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescription.service.JobDescriptionService;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.entity.JobDescriptionQuestion;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.repository.JobDescriptionQuestionRepository;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionRequest.JobDescriptionQuestionCreateRequest;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionResponse.JobDescriptionQuestionInfoResponse;
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
    public JobDescriptionQuestionInfoResponse create(JobDescriptionQuestionCreateRequest request) {
        JobDescription jd = jobDescriptionService.findById(request.jobDescriptionId());
        if (jobDescriptionQuestionRepository.findJobDescriptionQuestionByJobDescriptionAndQuestionNumber(jd, request.questionNumber()).isPresent())
            throw new BaseException(DUPLICATE_JOB_DESCRIPTION_QUESTION);
        JobDescriptionQuestion newJobDescriptionQuestion = request.toEntity(jd);
        JobDescriptionQuestion savedJobDescriptionQuestion = jobDescriptionQuestionRepository.save(newJobDescriptionQuestion);
        return JobDescriptionQuestionInfoResponse.toDto(savedJobDescriptionQuestion);
    }

    @Override
    public JobDescriptionQuestionInfoResponse get(Long jobDescriptionId) {
        JobDescriptionQuestion jdq = jobDescriptionQuestionRepository.findById(jobDescriptionId)
            .orElseThrow(() -> new BaseException(JOB_DESCRIPTION_QUESTION_NOT_FOUND));
        return JobDescriptionQuestionInfoResponse.toDto(jdq);
    }

    @Override
    public List<JobDescriptionQuestionInfoResponse> findByJobDescriptionId(Long jobDescriptionId) {
        JobDescription jd = jobDescriptionService.findById(jobDescriptionId);
        return findByJobDescription(jd);
    }

    @Override
    public List<JobDescriptionQuestionInfoResponse> findByJobDescription(JobDescription jobDescription) {
        List<JobDescriptionQuestion> jdql = jobDescriptionQuestionRepository
            .findByJobDescription(jobDescription, Sort.by("questionNumber").ascending());
        return jdql.stream().map(JobDescriptionQuestionInfoResponse::toDto).toList();
    }
}
