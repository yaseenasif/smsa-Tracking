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
    private LocalDate etd;
    private LocalDate atd;
    private LocalDate eta;
    private LocalDate ata;
    private LocalDateTime created;
    private LocalDateTime offLoadedFromAircraft;
    private LocalDateTime flightDelayed;
    private LocalDateTime notArrivedAsPlanned;
    private LocalDateTime inProgress;
    private LocalDateTime heldInCustoms;
    private LocalDateTime awaitingPayments;
    private LocalDateTime cleared;
    private long leadTime;
    private String remarks;

}
