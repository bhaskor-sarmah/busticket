package com.bohniman.covid.busticket.services;

import java.util.List;

import com.bohniman.covid.busticket.model.MasterDistrict;
import com.bohniman.covid.busticket.model.MasterState;
import com.bohniman.covid.busticket.model.MasterSubDistrict;
import com.bohniman.covid.busticket.model.Purpose;
import com.bohniman.covid.busticket.repository.MasterDistrictRepository;
import com.bohniman.covid.busticket.repository.MasterStateRepository;
import com.bohniman.covid.busticket.repository.MasterSubDistrictRepository;
import com.bohniman.covid.busticket.repository.PurposeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Autowired
    MasterStateRepository masterStateRepository;

    @Autowired
    MasterDistrictRepository masterDistrictRepository;

    @Autowired
    MasterSubDistrictRepository masterSubDistrictRepository;

    @Autowired
    PurposeRepository purposeRepository;

    public List<MasterState> getAllState() {
        return masterStateRepository.findAllByOrderByStateNameAsc();
    }

    public List<MasterDistrict> getDistrictByState(String stateCode) {
        return masterDistrictRepository.findAllByState_stateCodeOrderByDistrictNameAsc(stateCode);
    }

    public List<MasterSubDistrict> getSubDistrictByDistrict(String districtCode) {
        return masterSubDistrictRepository.findAllByDistrict_districtCodeOrderBySubDistrictNameAsc(districtCode);
    }

    public List<Purpose> getAllPurpose() {
        return purposeRepository.findAllByOrderByPurposeAsc();
    }

}