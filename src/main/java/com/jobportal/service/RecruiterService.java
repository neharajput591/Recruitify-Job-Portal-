package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.User12;
import com.jobportal.repository.UserRepo12;

@Service
public class RecruiterService {

    @Autowired
    UserRepo12 user12Repo;

    
    
}
