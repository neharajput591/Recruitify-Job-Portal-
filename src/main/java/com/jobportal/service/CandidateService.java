package com.jobportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.model.UnusedModel.Candidate;
import com.jobportal.repository.UnusedRepo.CandidateRepo;

@Service
public class CandidateService {

    @Autowired
    CandidateRepo candidateRepo;

    public void saveCandidate(Candidate candidateProfile) {
        candidateRepo.save(candidateProfile);
    }

      // Get candidate by ID
    public Candidate getByCandidateId(String candidateId) {

        Optional<Candidate> cand = candidateRepo.findByCandidateId(candidateId);

        if (cand.isPresent()) {
            return cand.get();
        } else {
            return null;
        }
    }

    // Get all candidates 
    public List<Candidate> getAllCandidates() {
        return candidateRepo.findAll();
    }

    public Candidate getByEmail(String email) {
    return candidateRepo.findByEmail(email).orElse(null);
}

}
