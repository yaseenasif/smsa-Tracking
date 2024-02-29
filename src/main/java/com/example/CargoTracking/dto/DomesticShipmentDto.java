package com.example.CargoTracking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DomesticShipmentDto {

    private Long id;
    private String originCountry;
    private String destinationCountry;
    private String originFacility;
    private String originLocation;
    private Boolean refrigeratedTruck;
    private String destinationFacility;
    private String destinationLocation;
    private String routeNumber;
    private Long routeNumberId;
    private Integer numberOfShipments;
    private Double weight;
    private LocalDateTime etd;
    private LocalDateTime eta;
    private LocalDateTime atd;
    private String driverName;
    private String driverContact;
    private String referenceNumber;
    private String vehicleType;
    private Integer numberOfPallets;
    private Integer numberOfBags;
    private Integer numberOfPalletsReceived;
    private Integer numberOfBagsReceived;
    private String vehicleNumber;
    private String tagNumber;
    private Long sealNumber;
    private String status;
    private String remarks;
    private LocalDateTime ata;
    private Integer totalShipments;
    private Integer numberOfBoxes;
    private Integer overages;
    private String overagesAwbs;
    private Integer received;
    private Integer shortages;
    private String shortagesAwbs;
    private String attachments;
    private Boolean redFlag;
    private String preAlertNumber;
    private String preAlertType;
    private long transitTimeTaken;
    private Boolean activeStatus;
    private Integer damage;
    private String damageAwbs;
}
