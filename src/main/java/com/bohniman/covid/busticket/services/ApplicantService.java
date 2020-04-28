package com.bohniman.covid.busticket.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bohniman.covid.busticket.model.Applicant;
import com.bohniman.covid.busticket.repository.ApplicantRepository;
import com.bohniman.covid.busticket.repository.MasterDistrictRepository;
import com.bohniman.covid.busticket.repository.PurposeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantService {

    @Autowired
    ApplicantRepository applicantRepository;

    @Autowired
    MasterDistrictRepository masterDistrictRepository;

    @Autowired
    PurposeRepository purposeRepository;

    public boolean saveApplicant(@Valid Applicant applicant) {
        try {
            applicantRepository.save(applicant);
            return true;
        } catch (Exception e) {
            System.out.println("Error - " + e.getMessage());
        }
        return false;
    }

    public boolean checkIfMobileExist(String mobile) {
        Optional<Applicant> applicant = applicantRepository.findByMobile(mobile);
        if (applicant.isPresent()) {
            return true;
        }
        return false;
    }

    public List<Applicant> getAllApplicant() {
        List<Applicant> appList = applicantRepository.findAllByOrderByCreatedAtAsc();
        for (Applicant a : appList) {
            a.setFormDistrict(masterDistrictRepository.findById(a.getFormDistrict()).get().getDistrictName());
            a.setToDistrict(masterDistrictRepository.findById(a.getToDistrict()).get().getDistrictName());
            a.setPurpose(purposeRepository.findById(a.getPurpose()).get().getPurpose());
        }
        return appList;
    }

    public Applicant getApplicantById(Long applicantId) {
        return applicantRepository.findById(applicantId).get();
    }

}