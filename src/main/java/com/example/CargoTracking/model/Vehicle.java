package com.example.CargoTracking.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String occupancy;
    private String vehicleNumber;
    private boolean status;

    @OneToMany(mappedBy = "vehicle")
    private Set<DomesticRoute> domesticRoutes;
}
