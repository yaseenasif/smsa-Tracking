package com.example.CargoTracking.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LocationDto {

    private Long id;
    private String locationName;
    private boolean status;

}
