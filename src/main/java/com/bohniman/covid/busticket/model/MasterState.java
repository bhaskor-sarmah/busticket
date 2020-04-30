package com.bohniman.covid.busticket.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "master_state")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterState {

    @Id
    private String stateCode;
    private String stateName;
    private String isActive;

    @OneToMany(mappedBy = "state")
    private List<MasterDistrict> districts = new ArrayList<>();

}