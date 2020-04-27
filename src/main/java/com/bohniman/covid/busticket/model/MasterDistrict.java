package com.bohniman.covid.busticket.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "master_district")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterDistrict {

    @Id
    private String districtCode;

    private String districtName;
    private String isActive;

}