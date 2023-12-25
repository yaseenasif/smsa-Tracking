package com.example.CargoTracking.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DomesticPerformance {
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
}
