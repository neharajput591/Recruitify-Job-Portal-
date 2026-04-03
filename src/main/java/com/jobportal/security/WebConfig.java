package com.jobportal.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/profilePic/**")
                .addResourceLocations("file:profilePic/");


        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        registry.addResourceHandler("/companyKyc/**")
                .addResourceLocations("file:companyKyc/");

        registry.addResourceHandler("/companyLogo/**")
                .addResourceLocations("file:companyLogo/");
    }

}
