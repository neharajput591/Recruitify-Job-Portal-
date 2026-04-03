// package com.jobportal.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.jobportal.model.Jobs;
// import com.jobportal.model.User12;
// import com.jobportal.repository.JobRepo;
// import com.jobportal.repository.UserRepo12;
// import com.jobportal.repository.UnusedRepo.CandidateRepo;
// import com.jobportal.service.JobService;
// import com.jobportal.service.User12Service;


// @Controller
// public class AuthControllerBackup {

//     // @Autowired
//     // UserRepo userRepo;

//     @Autowired
//     UserRepo12 userRepo12;

//     @Autowired
//     CandidateRepo candidateRepo;

//     @Autowired
//     User12Service userService;

//     // @Autowired
//     // RecruiterRepo recruiterRepo;


//     @Autowired
//     JobRepo jobRepo;

//     @Autowired
//     JobService jobService;

//     @GetMapping("/")
//     public String home( @RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "10") int size,Model model){

//         //  List<Jobs> job = jobRepo.findAll();

//           Page<Jobs> jobPage = jobService.getJobs(page, size);

//         model.addAttribute("job", jobPage.getContent());
//         model.addAttribute("currentPage", page);
//         model.addAttribute("totalPages", jobPage.getTotalPages());

//         // model.addAttribute("job", job);
//         return "newindexYoutube";
//     }


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


//     // @GetMapping("/")
//     // public String home(Model model) {

//     //     List<Jobs> job = jobRepo.findAll();

//     //     model.addAttribute("job", job);
//     //     return "index1";
//     // }

//     @GetMapping("/login")
//     public String login() {

//         return "login";
//     }

//     @GetMapping("/register")
//     public String register(Model model) {

//         // model.addAttribute("userdata", new User());
//         model.addAttribute("userdata" , new User12());
//         return "register";
//     }

//         // ✅ Register
//     @PostMapping("/RegisterData12")
//     public String register(@ModelAttribute User12 user, Model model) {

//         if (userRepo12.existsByEmail(user.getEmail())) {
//             model.addAttribute("errorMessage", "This email is already registered!");
//             model.addAttribute("userdata", user);
//             return "register";
//         }

//         userService.registerUser(user);
//         model.addAttribute("email", user.getEmail());

//         return "otp-page";
//     }

//     // ✅ Verify OTP
//     // @PostMapping("/verify-otp")
//     // public String verifyOtp(@RequestParam String email,
//     //                         @RequestParam String otp,
//     //                         Model model) {

//     //     boolean isValid = userService.verifyOtp(email, otp);

//     //     if (isValid) {
//     //         return "login";
//     //     } else {
//     //         model.addAttribute("error", "Invalid or Expired OTP");
//     //         return "otp-page";
//     //     }
//     // }

//     @PostMapping("/resend-otp")
//     public String resendOtp(@RequestParam String email, Model model) {
//         userService.resendOtp(email);

//         model.addAttribute("message", "OTP Sent Again");
//         model.addAttribute("email", email);
//         return "otp-page";
//     }


//     // @PostMapping("/RegisterData")
//     // public String saveUser(@ModelAttribute("userdata") User user) {

//     // user.setPassword(passwordEncoder.encode(user.getPassword()));

//     // userRepo.save(user);

//     // return "redirect:/login?registerSuccess=true";
//     // }

//     // @PostMapping("/RegisterData")
//     // public ResponseEntity<String> registerUser(@RequestBody User user) {
//     // if (userRepo.existsByEmail(user.getEmail())) {
//     // return ResponseEntity.badRequest().body("Email is already registered");
//     // }

//     // userRepo.save(user);
//     // return ResponseEntity.ok("Registration successful");
//     // }


//     // @PostMapping("/RegisterData")
//     // public String registerUser(@ModelAttribute("userdata") User12 user, Model model) {

//     //     if (userRepo12.existsByEmail(user.getEmail())) {
//     //         model.addAttribute("errorMessage", "This email is already registered!");
//     //         return "register"; 
//     //     }

//     //     user.setPassword(passwordEncoder.encode(user.getPassword()));
//     //     // Save the new user
//     //     user = userRepo12.save(user);

//     //     if ("candidate".equalsIgnoreCase(user.getRole())) {
           

        
//     //     } else if ("recruiter".equalsIgnoreCase(user.getRole())) {
          
//     //     }


//     //     return "redirect:/login?registerSuccess=true"; 
//     // }


    

//     // @GetMapping("/candidate_home")
//     // public String candidateHome(Model model) {

//     //     model.addAttribute("candidateProfile", new Candidate());

//     //     return "candidate_home";
//     // }

//     // @GetMapping("/recruiter_home")
//     // public String recruiterHome() {
//     //     return "recruiter_home";
//     // }

//     // @GetMapping("/admin_home")
//     // public String adminHome() {
//     //     return "admin_home";
//     // }


//     // @PostMapping("/index1/search")

//       @GetMapping("/index1/search")
//     public String searchJobs(@RequestParam("keyword") String keyword, Model model) {

//         System.out.println("Keyword: " + keyword);
//         // List<Jobs> jobs = jobRepo.searchJobs(keyword);

//         List<Jobs> jobs = jobRepo
//                 .findByTitleContainingIgnoreCaseOrSkillsRequiredContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(
//                         keyword, keyword, keyword);

//         model.addAttribute("jobsOnSearch", jobs);

//         List<Jobs> job = jobRepo.findAll();

//         model.addAttribute("job", job);
//         return "newindexYoutube";
//     }
// }
