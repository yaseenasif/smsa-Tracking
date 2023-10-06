package com.example.CargoTracking.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LocationPortDto {

    private Long id;
    @NotBlank(message = "Port name is required")
    private String portName;
    private boolean status;
    @NotBlank(message = "Location name is required")
    private String location;


}
