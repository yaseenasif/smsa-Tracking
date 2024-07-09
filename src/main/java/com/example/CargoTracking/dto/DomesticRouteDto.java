package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Driver;
import com.example.CargoTracking.model.Vehicle;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DomesticRouteDto {
    private Long id;
    private String origin;
    private String destination;
    private String route;
    private String driver;
    private LocalTime etd;
    private LocalTime eta;
    private Integer durationLimit;
    private String remarks;
    private Set<Driver> drivers;
    private Set<Vehicle> vehicles;

}
