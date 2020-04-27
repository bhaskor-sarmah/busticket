package com.bohniman.covid.busticket.repository;

import java.util.List;

import com.bohniman.covid.busticket.model.Purpose;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurposeRepository extends JpaRepository<Purpose, String> {

    List<Purpose> findAllByOrderByPurposeAsc();
}