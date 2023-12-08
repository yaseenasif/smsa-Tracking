package com.example.CargoTracking.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LocationDto {

    private Long id;
    @NotBlank(message = "Location name is required")
    private String locationName;
    private String type;
    private boolean status;
    private String originEmail;
    private String destinationEmail;
    private String originEscalation;
    private String destinationEscalation;

}
