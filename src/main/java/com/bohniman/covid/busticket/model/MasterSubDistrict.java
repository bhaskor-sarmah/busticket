package com.bohniman.covid.busticket.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterSubDistrict {

    @Id
    private String subDistrictCode;

    private String subDistrictName;
    private String isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_district")
    private MasterDistrict district;

}