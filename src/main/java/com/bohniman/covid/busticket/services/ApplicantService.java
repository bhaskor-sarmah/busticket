package com.bohniman.covid.busticket.services;

import javax.validation.Valid;

import com.bohniman.covid.busticket.model.Applicant;
import com.bohniman.covid.busticket.repository.ApplicantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantService {

    @Autowired
    ApplicantRepository applicantRepository;

    public boolean saveApplicant(@Valid Applicant applicant) {
        try {
            applicantRepository.save(applicant);
            return true;
        } catch (Exception e) {
            System.out.println("Error - " + e.getMessage());
        }
        return false;
    }

}