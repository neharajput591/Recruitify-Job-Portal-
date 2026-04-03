package com.jobportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.User12;
import com.jobportal.repository.UserRepo12;

@Service
public class User12Service {

    @Autowired
    UserRepo12 user12Repo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Map<String, String> otpCache = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> otpExpiryCache = new ConcurrentHashMap<>();

    public User12 getByEmail(String email) {
        return user12Repo.findByEmail(email).orElse(null);
    }

    public void saveCandidate(User12 candidateProfile) {
        user12Repo.save(candidateProfile);
    }

    public List<User12> findByRoleAndVerify(String role, String verify) {
        return user12Repo.findByRoleAndVerify(role, verify);
    }

    public List<User12> findAllCandidate(String role1) {
        return user12Repo.findByRole(role1);
    }

    public Optional<User12> findRecruiter(String idUser) {
        return user12Repo.findById(idUser);
    }

    public List<User12> findAllCandidateByOrderByCreatedDateDesc(String role1) {
        return user12Repo.findByRoleOrderByCreatedDateDesc(role1);
    }

    public List<User12> findByRoleAndVerify12(String role, String verify) {
        return user12Repo.findByRoleAndVerifyOrderByCreatedDateDesc(role, verify);
    }

    // Generate OTP
    public String generateOTP() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    // Register User + Send OTP
    public void registerUser(User12 user) {

        // user.setPassword(passwordEncoder.encode(user.getPassword())); // 🔥 FIX

        user.setEnabled(false);

        // generate OTP
        String otp = generateOTP();

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        // user12Repo.save(user);

        sendOtpEmail(user.getEmail(), otp);

    }

    public void sendOtpForRegistration(String email) {
        String otp = generateOTP();
        otpCache.put(email, otp);
        otpExpiryCache.put(email, LocalDateTime.now().plusMinutes(5));
        sendOtpEmail(email, otp);
    }

    public boolean verifyOtpForRegistration(String email, String otp) {
        String cachedOtp = otpCache.get(email);
        LocalDateTime expiry = otpExpiryCache.get(email);

        if (cachedOtp != null && cachedOtp.equals(otp) && expiry != null && expiry.isAfter(LocalDateTime.now())) {
            // OTP is valid, now we can save the user to the database
            User12 newUser = new User12();
            newUser.setEmail(email);
            newUser.setEnabled(true);
            user12Repo.save(newUser);

            // Clear cache
            otpCache.remove(email);
            otpExpiryCache.remove(email);
            return true;
        }
        return false;
    }

    public void completeRegistration(String email, String name, String password, String role) {
        Optional<User12> userOpt = user12Repo.findByEmail(email);
        if (userOpt.isPresent()) {
            User12 user = userOpt.get();
            user.setName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            user12Repo.save(user);
        } else {
            throw new RuntimeException("User not found during registration completion");
        }
    }

    // Send Email
    public void sendOtpEmail(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP Verification");
        message.setText("Your OTP is: " + otp + " (valid for 5 minutes)");

        mailSender.send(message);
    }

    // verify otp

    public boolean verifyOtp(String email, String otp) {

        Optional<User12> user12 = user12Repo.findByEmail(email);

        if (user12.isPresent()) {
            User12 user = user12.get();

            if (user == null)
                return false;

            if (!user.getOtp().equals(otp))
                return false;

            if (user.getOtpExpiry().isBefore(LocalDateTime.now()))
                return false;

            user.setEnabled(true);
            user.setOtp(null);

            user12Repo.save(user);

            return true;

        } else {
            throw new RuntimeException("User not found with this email");

        }

    }

    // Resend OTP
    public void resendOtp(String email) {

        Optional<User12> user12 = user12Repo.findByEmail(email);

        if (user12.isPresent()) {
            User12 user = user12.get();

            String otp = generateOTP();

            user.setOtp(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

            user12Repo.save(user);

            sendOtpEmail(email, otp);

        } else {
            throw new RuntimeException("User not found with this email");
        }

    }

}
