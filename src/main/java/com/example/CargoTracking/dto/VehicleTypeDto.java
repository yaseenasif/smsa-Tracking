package com.example.CargoTracking.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VehicleTypeDto {

    private Long id;
    @NotBlank(message = "Vehicle type is required")
    private String name;

}
