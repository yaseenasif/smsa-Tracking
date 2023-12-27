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
public class InternationalRoadReportStatusDto {
    private Long id;
    private String preAlertNumber;
    private String referenceNumber;
    private String origin;
    private String destination;
    private String route;
    private String vehicle;
    private Integer shipments; // no of shipments
    private Integer pallets; // no of pallets
    private String occupancy;
    private Integer bags; // no of bags
    private LocalDateTime etd;
    private LocalDateTime atd;
    private LocalDateTime eta;
    private LocalDateTime ata;
    private LocalDateTime created;
    private LocalDateTime departed;
    private LocalDateTime notArrived;
    private LocalDateTime heldInCustoms;
    private LocalDateTime awaitingPayments;
    private LocalDateTime accident;
    private LocalDateTime borderDelay;
    private LocalDateTime offloadedAtDestination;
    private LocalDateTime Cleared;
    private long etdVsEta;
    private long etaVSAta;
    private long leadTime;
    private String remarks;

}
