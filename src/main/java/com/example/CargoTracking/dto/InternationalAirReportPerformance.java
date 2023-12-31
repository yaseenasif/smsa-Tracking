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
public class InternationalAirReportPerformance {
    private Long id;
    private String preAlertNumber;
    private String referenceNumber;
    private String origin;
    private String destination;
    private String route;
    private String flight; // flight number
    private LocalDateTime actualTimeDeparture;
    private LocalDateTime actualTimeArrival;
    private LocalDateTime cleared;
    private Long totalTransitTime;
    private Long totalLeadTime;
}
