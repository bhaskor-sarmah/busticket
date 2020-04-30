package com.bohniman.covid.busticket.repository;

import java.util.List;

import com.bohniman.covid.busticket.model.MasterDistrict;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterDistrictRepository extends JpaRepository<MasterDistrict, String> {

    List<MasterDistrict> findAllByState_stateCodeOrderByDistrictNameAsc(String stateCode);
}