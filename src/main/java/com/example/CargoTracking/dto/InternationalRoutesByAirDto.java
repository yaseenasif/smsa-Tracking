package com.example.CargoTracking.dto;
import lombok.*;
import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InternationalRoutesByAirDto {
    private Long id;
    private String origin;
    private String destination;
    private String route;
    private String driver;
    private LocalTime etd;
    private LocalTime eta;
}
