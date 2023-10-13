package com.example.CargoTracking.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DomesticShipmentDto {

    private Long id;
    private String origin;
    private String originFacility;
    private String destination;
    private String destinationFacility;
    private Date departureDate;
    private Date arrivalDate;
    private Time departureTime;
    private Time arrivalTime;
    private Integer numberOfShipments;
    private Double actualWeight;
    private String driverName;
    private String driverNumber;
    private String referenceNumber;
    private String vehicleType;
    private String vehicleNumber;
    private Integer numberOfPallets;
    private Long tagNumber;
    private Long sealNumber;
    private Integer numberOfBags;
//     attachments dalega
    private String status;
    private String remarks;
    private boolean refrigeratedTruck;
    private String ata;
    private String overage;
    private String shortage;
    private String overageAwb;
    private String Overage;
    private Integer totalShipment;
    private String received;
    private String createdBy;
    private LocalDate createdAt;
    private String updatedBy;
    private LocalDate updatedAt;

}
