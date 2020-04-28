package com.bohniman.covid.busticket.repository;

import java.util.List;
import java.util.Optional;

import com.bohniman.covid.busticket.model.Applicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findByMobile(String mobile);

    List<Applicant> findAllByOrderByCreatedAtAsc();

}