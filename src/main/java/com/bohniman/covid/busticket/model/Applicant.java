package com.bohniman.covid.busticket.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @NotEmpty(message = "Enter a valid Name")
    private String name;

    @Pattern(regexp = "[\\d]{1}|10", message = "Enter valid number between 1 to 10")
    private String noOFMember;

    @NotNull(message = "Please select state")
    @Pattern(regexp = "?!-1", message = "Please select state")
    private String formState;

    @NotNull(message = "Please select District")
    @Pattern(regexp = "?!-1", message = "Please select District")
    private String formDistrict;

    @NotNull(message = "Please select Sub-District")
    @Pattern(regexp = "?!-1", message = "Please select Sub-District")
    private String formSubDistrict;

    @NotEmpty(message = "Pincode is required")
    private String fromPincode;

    @NotNull(message = "Please select state")
    @Pattern(regexp = "?!-1", message = "Please select state")
    private String toState;

    @NotNull(message = "Please select District")
    @Pattern(regexp = "?!-1", message = "Please select District")
    private String toDistrict;

    @NotNull(message = "Please select Sub-District")
    @Pattern(regexp = "?!-1", message = "Please select Sub-District")
    private String toSubDistrict;

    @NotEmpty(message = "Pincode is required")
    private String toPincode;

    @Pattern(regexp = "[\\d]+", message = "Select a purpose")
    private String purpose;

    @Pattern(regexp = "[\\d]{10}", message = "Invalid Mobile No")
    @Column(unique = true, length = 10)
    private String mobile;

    private String type;

    private String purposeDetails;

    public Applicant(String mobile, String type) {
        this.mobile = mobile;
        this.type = type;
    }

}