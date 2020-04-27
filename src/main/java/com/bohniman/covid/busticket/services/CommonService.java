package com.bohniman.covid.busticket.services;

import java.util.List;

import com.bohniman.covid.busticket.model.MasterDistrict;
import com.bohniman.covid.busticket.model.Purpose;
import com.bohniman.covid.busticket.repository.MasterDistrictRepository;
import com.bohniman.covid.busticket.repository.PurposeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Autowired
    MasterDistrictRepository masterDistrictRepository;

    @Autowired
    PurposeRepository purposeRepository;

    public List<MasterDistrict> getAllDistrict() {
        return masterDistrictRepository.findAllByOrderByDistrictNameAsc();
    }

    public List<Purpose> getAllPurpose() {
        return purposeRepository.findAllByOrderByPurposeAsc();
    }

}