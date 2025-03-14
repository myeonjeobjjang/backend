package org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service;

import org.myeonjeobjjang.domain.core.jobDescription.entity.JobDescription;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionRequest.JobDescriptionQuestionCreateRequest;
import org.myeonjeobjjang.domain.core.jobDescriptionQuestion.service.dto.JobDescriptionQuestionResponse.JobDescriptionQuestionInfoResponse;

import java.util.List;

public interface JobDescriptionQuestionService {
    JobDescriptionQuestionInfoResponse create(JobDescriptionQuestionCreateRequest request);

    JobDescriptionQuestionInfoResponse get(Long jobDescriptionId);

    List<JobDescriptionQuestionInfoResponse> findByJobDescriptionId(Long jobDescriptionId);

    List<JobDescriptionQuestionInfoResponse> findByJobDescription(JobDescription jobDescription);
}
