package com.example.CargoTracking.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InternationalRoadReportPerformance {
    private Long id;
    private String preAlertNumber;
    private String referenceNumber;
    private String origin;
    private String destination;
    private String route;
    private String vehicleType;
    private LocalDate actualTimeDeparture;
    private LocalDate actualTimeArrival;
    private LocalDateTime offloaded;
    private String totalTransitTime;
    private String totalLeadTime;

}
