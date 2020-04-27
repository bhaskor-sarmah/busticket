package com.bohniman.covid.busticket.repository;

import com.bohniman.covid.busticket.model.Applicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}