package com.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
import java.util.HashMap;

import com.jobportal.model.Jobs;
import com.jobportal.model.User12;
import com.jobportal.repository.JobRepo;
import com.jobportal.repository.UserRepo12;
import com.jobportal.repository.UnusedRepo.CandidateRepo;
import com.jobportal.service.JobService;
import com.jobportal.service.User12Service;


@Controller
public class AuthController {

    // @Autowired
    // UserRepo userRepo;

    @Autowired
    UserRepo12 userRepo12;

    @Autowired
    CandidateRepo candidateRepo;

    @Autowired
    User12Service userService;

    // @Autowired
    // RecruiterRepo recruiterRepo;


    @Autowired
    JobRepo jobRepo;

    @Autowired
    JobService jobService;

    @GetMapping("/")
    public String home( @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,Model model){

        //  List<Jobs> job = jobRepo.findAll();

          Page<Jobs> jobPage = jobService.getJobs(page, size);

        model.addAttribute("job", jobPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobPage.getTotalPages());

        // model.addAttribute("job", job);
        return "newindexYoutube";
    }


//     @PostMapping("/verify-otp")
// public String verifyOtp(@RequestParam String email,
//                         @RequestParam String otp,
//                         Model model) {

//     boolean isValid = userService.verifyOtp(email, otp);

//     if (isValid) {
//         return "login";
//     } else {
//         model.addAttribute("error", "Invalid OTP");
//         return "otp-page";
//     }
// }


    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {

        // model.addAttribute("userdata", new User());
        model.addAttribute("userdata" , new User12());
        return "register";
    }

        // ✅ Register
    @PostMapping("/RegisterData12")
    public String register(@ModelAttribute User12 user, Model model) {

        if (userRepo12.existsByEmail(user.getEmail())) {
            model.addAttribute("errorMessage", "This email is already registered!");
            model.addAttribute("userdata", user);
            return "register";
        }

        userService.registerUser(user);
        model.addAttribute("email", user.getEmail());

        return "otp-page";
    }

        @PostMapping("/verify-otp")
public String verifyOtp(@RequestParam String email,
                        @RequestParam String otp,
                        Model model) {

    boolean isValid = userService.verifyOtp(email, otp);

    if (isValid) {
        return "login";
    } else {
        model.addAttribute("error", "Invalid OTP");
        return "otp-page";
    }
}

    @PostMapping("/resend-otp")
    public String resendOtp(@RequestParam String email, Model model) {
        userService.resendOtp(email);

        model.addAttribute("message", "OTP Sent Again");
        model.addAttribute("email", email);
        return "otp-page";
    }


      @GetMapping("/index1/search")
    public String searchJobs(@RequestParam("keyword") String keyword, Model model) {

        System.out.println("Keyword: " + keyword);
        // List<Jobs> jobs = jobRepo.searchJobs(keyword);

        List<Jobs> jobs = jobRepo
                .findByTitleContainingIgnoreCaseOrSkillsRequiredContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(
                        keyword, keyword, keyword);

        model.addAttribute("jobsOnSearch", jobs);

        List<Jobs> job = jobRepo.findAll();

        model.addAttribute("job", job);
        return "newindexYoutube";
    }

    @PostMapping("/api/auth/send-otp")
    @ResponseBody
    public Map<String, Object> sendOtp(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (userRepo12.existsByEmail(email)) {
                response.put("success", false);
                response.put("message", "Email already registered!");
            } else {
                userService.sendOtpForRegistration(email);
                response.put("success", true);
                response.put("message", "OTP sent successfully!");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error sending OTP: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/api/auth/verify-otp-ajax")
    @ResponseBody
    public Map<String, Object> verifyOtpAjax(@RequestParam String email, @RequestParam String otp) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isValid = userService.verifyOtpForRegistration(email, otp);
            if (isValid) {
                response.put("success", true);
                response.put("message", "OTP verified successfully!");
            } else {
                response.put("success", false);
                response.put("message", "Invalid or expired OTP!");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error verifying OTP: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/complete-registration")
    public String showCompleteRegistration(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "complete-registration";
    }

    @PostMapping("/api/auth/complete-registration")
    @ResponseBody
    public Map<String, Object> completeRegistration(@RequestParam String email, 
                                                   @RequestParam String name, 
                                                   @RequestParam String password,
                                                   @RequestParam String role) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.completeRegistration(email, name, password, role);
            response.put("success", true);
            response.put("message", "Registration completed successfully! Redirecting to login...");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error completing registration: " + e.getMessage());
        }
        return response;
    }
}
