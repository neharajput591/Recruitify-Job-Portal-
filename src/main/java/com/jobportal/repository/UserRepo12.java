package com.jobportal.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jobportal.model.User12;

public interface UserRepo12 extends MongoRepository<User12,String> {
    
    boolean existsByEmail(String email);

     Optional<User12> findByEmail(String email);

     List<User12> findByVerify(String verifyOrNot);

     List<User12> findByRoleAndVerify(String role, String verify);

     List<User12> findByRole(String role1);

    int countByRole(String role);

    int countByCreatedDateBetween(String role,Date start,Date end);

    List<User12> findByRoleOrderByCreatedDateDesc(String role1);

    List<User12> findByRoleAndVerifyOrderByCreatedDateDesc(String role, String verify);

    

}
