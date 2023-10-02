package com.example.CargoTracking.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DriverDto {

    private Long id;
    private String name;
    private String contactNumber;
    private String referenceNumber;
    private boolean status;



}
