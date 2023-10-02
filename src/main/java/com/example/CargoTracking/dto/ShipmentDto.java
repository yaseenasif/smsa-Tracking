package com.example.CargoTracking.dto;

import lombok.*;

import java.sql.Time;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ShipmentDto {

    private Long id;
    private String originCountry;
    private String originPort;
    private String destinationCountry;
    private String destinationPort;
    private String shipmentMode;
    private String mode;
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

}
