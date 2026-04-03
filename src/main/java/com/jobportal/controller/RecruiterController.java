package com.jobportal.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.model.Application;
import com.jobportal.model.Jobs;
import com.jobportal.model.User12;
import com.jobportal.repository.ApplicationRepo;
import com.jobportal.repository.UserRepo12;
// import com.jobportal.security.user12Repo;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.RecruiterService;
import com.jobportal.service.User12Service;

@Controller
// @RestController
public class RecruiterController {

    @Autowired
    RecruiterService recruiterService;

    @Autowired
    User12Service user12service;

    @Autowired
    UserRepo12 user12Repo;
    // User12Repo user12Repo;

    @Autowired
    JobService jobService;

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    ApplicationService applicationService;

    // @Autowired
    // Application application;

    @GetMapping("/recruiter_home")
    public String recruiterHome(Principal principal, Model model) {

        String email = principal.getName();

        User12 recruiterdata = user12service.getByEmail(email);

        String recruiterId = recruiterdata.getIdUser();

        String name = recruiterdata.getName();
        String profilepic = recruiterdata.getProfilepic();

        model.addAttribute("name", name);
        model.addAttribute("profilepic", profilepic);

        System.out.println(profilepic);

        List<Jobs> jobByMe = jobService.findAllJobByRecruiter(recruiterId);

        // System.out.println("abbajhgjhfdhs");
        // System.out.println(jobByMe);

        // System.out.println(recruiterId);

        model.addAttribute("jobByMe", jobByMe);


        model.addAttribute("verifyStatus", recruiterdata.getVerify());
        return "recruiter_home";
    }

    @GetMapping("Recruiter/EditJob/{jobId}")
    public String edit(@PathVariable("jobId") String jobId, Model model) {

        Jobs updatejob = jobService.getByJobId(jobId);
        model.addAttribute("updatejob", updatejob);

        return "editJob";
    }

    @PostMapping("/Recruiter/updatedJob")
    public String updatesJobdetails(@ModelAttribute("updatejob") Jobs updatedone) {

        jobService.savejobupdate(updatedone);

        return "PostSuccess";

    }

    @GetMapping("/Recruiter/DeleteJob/{jobId}")
    public String delete(@PathVariable("jobId") String jobId, Model model, Principal principal) {

        jobService.DeleteJob(jobId);

        String email = principal.getName();

        User12 recruiterdata = user12service.getByEmail(email);

        String recruiterId = recruiterdata.getIdUser();

        List<Jobs> jobByMe = jobService.findAllJobByRecruiter(recruiterId);

        model.addAttribute("jobByMe", jobByMe);

        return "/recruiter_home";
    }

    @GetMapping("/Recruiter/updateProfile")
    public String update(Principal principal, Model model) {

        String email = principal.getName();

        Optional<User12> recruiterUpdate = user12Repo.findByEmail(email);

        User12 recruiter = recruiterUpdate.get();
        recruiter.setIdUser(recruiter.getIdUser());

        model.addAttribute("recruiter", recruiter);
        model.addAttribute("verifyStatus", recruiter.getVerify());

        return "UpdateFormRecruiter";
    }

    @PostMapping("/Recruiter/formData")
    public String recruiterProfileData(@ModelAttribute("recruiter") User12 formUser,
            @RequestParam("pic") MultipartFile file, Model model, Principal principal,
            @RequestParam("logo") MultipartFile file1) {

        try {

            String email = principal.getName();

            String uploadDir = "profilePic/";
            Path uploadPath = Paths.get(uploadDir);

            String uploadLogoDir = "companyLogo/";
            Path uploadLogoPath = Paths.get(uploadLogoDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if(!Files.exists(uploadLogoPath)){
                Files.createDirectories(uploadLogoPath);
            }


            User12 existingUser = user12service.getByEmail(email);

            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = uploadPath.resolve(fileName);
                Files.write(path, file.getBytes());
                existingUser.setProfilepic(fileName);
            }

            if (!file1.isEmpty()) {
                String fileNameLogo = System.currentTimeMillis() + "_" + file1.getOriginalFilename();
                Path pathLogo = uploadLogoPath.resolve(fileNameLogo);
                Files.write(pathLogo, file1.getBytes());
                existingUser.setCompanyLogo(fileNameLogo);
            }

            // Update profile fields
            existingUser.setName(formUser.getName());
            existingUser.setAge(formUser.getAge());
            existingUser.setSkills(formUser.getSkills());
            existingUser.setCity(formUser.getCity());
            existingUser.setState(formUser.getState());
            existingUser.setCountry(formUser.getCountry());
            existingUser.setContact(formUser.getContact());
            existingUser.setEducation(formUser.getEducation());
            existingUser.setExperience(formUser.getExperience());
            existingUser.setAbout(formUser.getAbout());
            existingUser.setCompanyName(formUser.getCompanyName());
            existingUser.setCompanyAddress(formUser.getCompanyAddress());
            existingUser.setPosition(formUser.getPosition());

            user12Repo.save(existingUser);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/recruiter_home";
    }

    @PostMapping("/Recruiter/uploadCompanyKyc")
    public String uploadCompanyKyc(@RequestParam("companyKyc") MultipartFile file , Principal principal){

        try {
            
            String email = principal.getName();
            String uploadDir = "companyKyc/";

            String fileName = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            Files.write(path , file.getBytes());

            User12 recruiter = user12service.getByEmail(email);
            
            recruiter.setCompanyKyc(fileName);

            user12Repo.save(recruiter);

            // return ""

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/recruiter_home";
    }
    //     @PostMapping("/Candidate/uploadResume")
    // public String uploadresume(@RequestParam("resume") MultipartFile file, Principal principal) {

    //     try {

    //         String email = principal.getName();

    //         String uploadDir = "uploads/";

    //         String fileName = file.getOriginalFilename();
    //         Path path = Paths.get(uploadDir + fileName);
    //         Files.write(path, file.getBytes());

    //         User12 user = user12service.getByEmail(email);
    //         user.setResume(fileName);

    //         user12repo.save(user);
    //     }

    //     catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     return "newpage";

    // }


    @GetMapping("/Recruiter/postRequest")
    public String postrequest(Principal principal, Model model) {

        String email = principal.getName();
        User12 recuiterdetails = user12service.getByEmail(email);

        String verify = recuiterdetails.getVerify();

        if (verify.equals("Verified")) {

            Jobs newPost = new Jobs();

            newPost.setCompanyName(recuiterdetails.getCompanyName());
            newPost.setCompanyLogo(recuiterdetails.getCompanyLogo());
            newPost.setRecruiterId(recuiterdetails.getIdUser());
            newPost.setContact(recuiterdetails.getContact());

            model.addAttribute("newPost", newPost);

            // model.addAttribute("companyName",recuiterdetails.getCompanyName());
            // model.addAttribute("recruiter_id",recuiterdetails.getIdUser());

            return "postJob";

        } else {
            // return "newpage";
            model.addAttribute("errorMessage",
                    "Your profile is not yet verified. You cannot post a job right now.");
            return "recruiter_home";
        }
    }

    @PostMapping("/Recruiter/jobDetails")
    public String jobpost(@ModelAttribute("newPost") Jobs newpost) {

        jobService.postAjob(newpost);

        return "PostSuccess";
    }

    @GetMapping("/Recruiter/viewApplication/{jobId}")
    public String viewcandidateapplication(@PathVariable("jobId") String jobId, Model model, Principal principal) {

        // String email = principal.getName();

        // User12 recruiter = user12service.getByEmail(email);

        List<Application> candidateList = applicationService.findByJobIdOrderByAppliedDateDesc(jobId);

        

        model.addAttribute("candidateList", candidateList);
        return "candidatesApplications";

    }

    @GetMapping("/Recruiter/Hire/{application_id}")
    public String hireacandidate(@PathVariable("application_id") String application_id) {

        Optional<Application> candidate = applicationRepo.findById(application_id);

        Application applicant = candidate.get();

        applicant.setStatus("Accept");

        applicationRepo.save(applicant);

        return "redirect:/recruiter_home";
    }

    @GetMapping("/Recruiter/Reject/{application_id}")
    public String rejectacandidate(@PathVariable("application_id") String application_id) {

        Optional<Application> candidate = applicationRepo.findById(application_id);

        Application applicant = candidate.get();
        applicant.setStatus("Reject");

        applicationRepo.save(applicant);

        // applicationRepo.deleteById(application_id);

        return "redirect:/recruiter_home";
    }

}
