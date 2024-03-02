package com.example.CargoTracking.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InternationalRouteDto {
    private Long id;
    private String origin;
    private String destination;
    private String route;
    private String flight;
    private String driverId;
    private LocalTime etd;
    private LocalTime eta;
    private String remarks;
    private String type;
}
