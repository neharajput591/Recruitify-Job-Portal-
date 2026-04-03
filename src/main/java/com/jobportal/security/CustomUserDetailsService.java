package com.jobportal.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import com.jobportal.model.User12;
import com.jobportal.repository.UserRepo12;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo12 user12Repo;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User12 dbUser = user12Repo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));


        System.out.println("Role from DB: " + dbUser.getRole());
        return org.springframework.security.core.userdetails.User
                .withUsername(dbUser.getEmail())
                .password(dbUser.getPassword())
                .disabled(!dbUser.isEnabled())
                .roles(dbUser.getRole().trim().toUpperCase())
                .build();
    }
}