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
public class InternationalAirReportStatusDto {
    private Long id;
    private String preAlertNumber;
    private String referenceNumber;
    private String origin;
    private String destination;
    private String route;
    private String flightNumber;
    private Integer shipments;//no of shipments
    private Integer bags; // no of bags
    private LocalDateTime etd;
    private LocalDateTime atd;
    private LocalDateTime eta;
    private LocalDateTime ata;
    private LocalDateTime created;
    private LocalDateTime offLoadedFromAircraft;
    private LocalDateTime flightDelayed;
    private LocalDateTime notArrivedAsPlanned;
    private LocalDateTime inProgress;
    private LocalDateTime heldInCustoms;
    private LocalDateTime awaitingPayments;
    private LocalDateTime cleared;
    private Long etdVsEta;
    private Long etaVSAta;
    private Long leadTime;
    private String remarks;

}
