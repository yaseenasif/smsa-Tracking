package com.example.CargoTracking.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VehicleDto {

    private Long id;
    @NotBlank(message = "Vehicle type is required")
    private String name;
    @NotBlank(message = "occupancy is required")
    private String occupancy;
    private String vehicleNumber;
    private boolean status;

}
