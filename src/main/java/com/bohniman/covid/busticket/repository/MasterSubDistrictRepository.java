package com.bohniman.covid.busticket.repository;

import java.util.List;

import com.bohniman.covid.busticket.model.MasterSubDistrict;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterSubDistrictRepository extends JpaRepository<MasterSubDistrict, String> {

    List<MasterSubDistrict> findAllByDistrict_districtCodeOrderBySubDistrictNameAsc(String districtCode);

}