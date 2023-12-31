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
    private LocalDateTime actualTimeDeparture;
    private LocalDateTime actualTimeArrival;
    private LocalDateTime offloaded;
    private Long totalTransitTime;
    private Long totalLeadTime;

}
