package com.jobportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jobportal.model.Application;

public interface ApplicationRepo extends MongoRepository<Application, String> {

    List<Application> findByJobId(String job_id);

    List<Application> findByCandidateId(String candidateId);

    List<Application> findByCandidateIdAndJobId(String candidateId, String jobId);

    List<Application> findByJobIdOrderByAppliedDateDesc(String jobId);

    List<Application> findAllByOrderByAppliedDateDesc();

    
}
