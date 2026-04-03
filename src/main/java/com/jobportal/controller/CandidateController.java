package com.jobportal.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.model.Application;
import com.jobportal.model.Jobs;
import com.jobportal.model.User12;
import com.jobportal.repository.ApplicationRepo;
import com.jobportal.repository.JobRepo;
import com.jobportal.repository.UserRepo12;
import com.jobportal.service.CandidateService;
import com.jobportal.service.JobService;
import com.jobportal.service.User12Service;

@Controller
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @Autowired
    User12Service user12service;

    @Autowired
    UserRepo12 user12repo;

    @Autowired
    JobRepo jobRepo;

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    JobService jobService;

    // @GetMapping("/candidate_home")
    // public String candidateHome(Model model) {

    // model.addAttribute("candidateProfile", new Candidate());
    // Candidate candidate = CandidateService.getCandidate();
    // // String candidateId = candidate.
    // model.addAttribute("candidateId", candidate.getCandidateId());

    // return "candidate_home";
    // }

    @GetMapping("/candidate_home")
    public String candidateHome(Model model, Principal principal, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // List<Jobs> jobs = jobRepo.findAllByOrderByPostedDateDesc();

        // model.addAttribute("jobs", jobs);

         Page<Jobs> jobPage = jobService.getJobs(page, size);

        model.addAttribute("jobs", jobPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobPage.getTotalPages());

        String email = principal.getName();

        Optional<User12> user12 = user12repo.findByEmail(email);

        User12 newuser = user12.get();

        String name = newuser.getName();
        String profilePic = newuser.getProfilepic();

        model.addAttribute("name", name);
        model.addAttribute("profilePic", profilePic);

        String candidateId = newuser.getIdUser();

        // Get all applications of this candidate
        List<Application> applications = applicationRepo.findByCandidateId(candidateId);

        // Store only jobIds
        // for showing applied after once apply
        List<String> appliedJobIds = applications.stream()
                .map(Application::getJobId)
                .toList();

        model.addAttribute("appliedJobIds", appliedJobIds);
        
        model.addAttribute("searchPerformed", false);

        return "candidateHome";
    }

    @GetMapping("/Candidate/search")
    public String searchJobs(@RequestParam("keyword") String keyword, Model model,Principal principal) {

        System.out.println("Keyword: " + keyword);
        // List<Jobs> jobs = jobRepo.searchJobs(keyword);

        String email = principal.getName();

        Optional<User12> user12 = user12repo.findByEmail(email);

        User12 newuser = user12.get();

        String name = newuser.getName();
        String profilePic = newuser.getProfilepic();

        model.addAttribute("name", name);
        model.addAttribute("profilePic", profilePic);

        model.addAttribute("searchPerformed", true);

        List<Jobs> jobs = jobRepo
                .findByTitleContainingIgnoreCaseOrSkillsRequiredContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(
                        keyword, keyword, keyword);
        model.addAttribute("jobsearched", jobs);

        // System.out.println();

        return "candidateHome";
    }

    @GetMapping("/Candidate/UpdateProfile")
    public String updateProfile(Principal principal, Model model) {

        String email = principal.getName();

        Optional<User12> user12 = user12repo.findByEmail(email);

        if (user12 != null) {

            User12 newuser = user12.get();

            newuser.setIdUser(newuser.getIdUser());
            model.addAttribute("candidate", newuser);
            return "Update_Profile";
        }

        else {
            return "login";
        }
    }

    // @PostMapping("/Candidate/candidate_profile12")
    // public String profileCreation12(@ModelAttribute("candidate") User12
    // candidate, Model model) {

    // user12service.saveCandidate(candidate);

    // return "newpage";
    // }

    @PostMapping("/Candidate/candidate_profile12")
    public String profileCreation12(@ModelAttribute("candidate") User12 formUser,
            @RequestParam("pic") MultipartFile file,
            Principal principal) {

        try {

            String email = principal.getName();

            String uploadDir = "profilePic/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);

            }

            // String fileName = System.currentTimeMillis() + "_" +
            // file.getOriginalFilename();

            // Path path = uploadPath.resolve(fileName);
            // Files.write(path, file.getBytes());

            // User12 user = user12service.getByEmail(email);
            // user.setProfilepic(fileName);

            if (!file.isEmpty()) {

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                Path path = uploadPath.resolve(fileName);
                Files.write(path, file.getBytes());

                User12 user = user12service.getByEmail(email);
                user.setProfilepic(fileName);

                user12repo.save(user);
            }

            // existingUser.setName(formUser.getName());
            // existingUser.setAge(formUser.getAge());
            // existingUser.setSkills(formUser.getSkills());
            // existingUser.setCity(formUser.getCity());
            // existingUser.setState(formUser.getState());
            // existingUser.setCountry(formUser.getCountry());
            // existingUser.setContact(formUser.getContact());
            // existingUser.setEducation(formUser.getEducation());
            // existingUser.setExperience(formUser.getExperience());
            // existingUser.setAbout(formUser.getAbout());

            // user12repo.save(user);

            // User12 existingUser = user12service.getByEmail(email);

        } catch (Exception e) {

            e.printStackTrace();
        }

        String email = principal.getName();

        User12 existingUser = user12service.getByEmail(email);

        // // update only profile fields
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

        // ⚠ NEVER TOUCH PASSWORD HERE

        user12repo.save(existingUser);

        return "redirect:/candidate_home";
    }
    // @PostMapping("/Candidate/candidate_profile")
    // public String profileCreation(@ModelAttribute("candidateProfile") Candidate
    // candidate, Model model) {

    // candidateService.saveCandidate(candidate);

    // return "newpage";
    // }

    // @GetMapping("/Candidate/candidate_profile_update/{candidateId}")
    // public String candidateUpdate(@PathVariable("candidateId") String
    // candidateId,Model model){

    // Candidate candidate = candidateService.getByCandidateId(candidateId);
    // model.addAttribute("candidate", candidate);

    // return "profile_update";
    // }

    @PostMapping("/Candidate/uploadResume")
    public String uploadresume(@RequestParam("resume") MultipartFile file, Principal principal) {

        try {

            String email = principal.getName();

            String uploadDir = "uploads/";

            String fileName = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());

            User12 user = user12service.getByEmail(email);
            user.setResume(fileName);

            user12repo.save(user);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/candidate_home";

    }

    @GetMapping("/job/jobdetail/{jobId}")
    public String jobdetail(@PathVariable("jobId") String jobId, Model model) {

        Optional<Jobs> job = jobRepo.findById(jobId);

        if (job.isPresent()) {
            Jobs jobDesc = job.get();

            model.addAttribute("jobDesc", jobDesc);
            return "specificJob";
        }

        return "newpage";
    }

    @GetMapping("/job/jobdetailafterapply/{jobId}")
    public String jobdetailafterapplybycandidate(@PathVariable("jobId") String jobId, Model model,
            Principal principal) {

        String email = principal.getName();

        User12 user = user12service.getByEmail(email);

        String candidateId = user.getIdUser();

        List<Application> apps = applicationRepo.findByCandidateIdAndJobId(candidateId, jobId);

        Optional<Jobs> job = jobRepo.findById(jobId);

        if (job.isPresent() && !apps.isEmpty()) {
            Jobs jobDesc = job.get();
            Application application = apps.get(0); // get the most recent/first application

            Date appliedDate = application.getAppliedDate();
            model.addAttribute("appliedDate", appliedDate);

            String currentStatus = application.getStatus();
            model.addAttribute("currentStatus", currentStatus);
            // model.addAttribute("application",application);
            // System.out.println(application.getAppliedDate());

            System.out.println(jobDesc.getJobId());

            model.addAttribute("jobDesc", jobDesc);
            return "JobviewAfterApply";
        }

        return "newpage";
    }

    @GetMapping("/job/applynow/{jobId}")
    public String apply(@PathVariable("jobId") String jobId, Principal principal, Model model) {

        // Optional<Jobs> job = jobRepo.findById(jobId);

        Application application = new Application();

        String email = principal.getName();

        User12 candidate = user12service.getByEmail(email);

        String candidateId = candidate.getIdUser();

        String resume = candidate.getResume();

        String name = candidate.getName();

        Optional<Jobs> job = jobRepo.findById(jobId);

        Jobs jobforTitle = job.get();

        String title = jobforTitle.getTitle();

        // String jobTitle = jobRepo.getTitle(jobRepo.findById(jobId));

        System.out.println("Resume URL : " + resume);
        System.out.println(name);
        application.setCandidateId(candidateId);
        application.setJobId(jobId);
        application.setJobTitle(title);
        application.setResumeUrl(resume);

        application.setCandidateName(name);
        applicationRepo.save(application);

        // model.addAttribute("candidateId", candidateId);
        // model.addAttribute("resume", resume);
        // model.addAttribute("jobId", jobId);

        return "redirect:/candidate_home";
    }

    @GetMapping("/Candidate/ApplicationUpdate")
    public String applicationUpdate(Principal principal, Model model) {

        String email = principal.getName();

        User12 candidate = user12service.getByEmail(email);

        String candidateId = candidate.getIdUser();

        String candidateName = candidate.getName();

        List<Application> applications = applicationRepo.findByCandidateId(candidateId);

        model.addAttribute("applications", applications);
        model.addAttribute("candidateName", candidateName);

        // model.addAttribute("sejh" , applications.getJobId());

        List<String> jobTitles = new ArrayList<>();

        for (Application app : applications) {

            Jobs job = jobRepo.findById(app.getJobId()).orElse(null);

            if (job != null) {
                jobTitles.add(job.getTitle());
            }
        }

        model.addAttribute("jobTitle", jobTitles);

        return "ApplicationUpdate";
    }
}
