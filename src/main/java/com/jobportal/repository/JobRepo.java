package com.jobportal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobportal.model.Jobs;

public interface JobRepo extends MongoRepository<Jobs, String> {

    
    List<Jobs> getByRecruiterId(String recruiterId);

    int countByPostedDateBetween(Date start, Date end);

    long count();

    List<Jobs> findAllByOrderByPostedDateDesc();

List<Jobs> findByTitleContainingIgnoreCaseOrSkillsRequiredContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(
        String title, String skills, String company);
}
