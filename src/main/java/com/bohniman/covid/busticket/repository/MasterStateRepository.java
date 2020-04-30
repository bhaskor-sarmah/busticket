package com.bohniman.covid.busticket.repository;

import java.util.List;

import com.bohniman.covid.busticket.model.MasterState;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterStateRepository extends JpaRepository<MasterState, String> {

    List<MasterState> findAllByOrderByStateNameAsc();
}