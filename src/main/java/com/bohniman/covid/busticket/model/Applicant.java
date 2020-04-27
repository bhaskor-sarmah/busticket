package com.bohniman.covid.busticket.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @NotEmpty(message = "Enter a valid Name")
    private String name;

    @Pattern(regexp = "[\\d]{1}|10", message = "Enter valid number between 1 to 10")
    private String noOFMember;

    @Pattern(regexp = "[\\d]{3}", message = "Select a district")
    private String formDistrict;

    @Pattern(regexp = "[\\d]{3}", message = "Select a district")
    private String toDistrict;

    @Pattern(regexp = "[\\d]+", message = "Select a purpose")
    private String purpose;

    private String purposeDetails;

}