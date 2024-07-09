package com.example.CargoTracking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
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
public class InternationalShipmentDto {
    private Long id;
    private String originCountry;
    private String originFacility;
    private String originLocation;
    private Boolean refrigeratedTruck;//new road
    private String type;
    private String shipmentMode;
    private String preAlertNumber;
    private String destinationCountry;
    private String destinationFacility;
    private String destinationLocation;
    private String carrier;//air
    private LocalDateTime etd;
    private LocalDateTime eta;
    private LocalDateTime atd;
    private String flightNumber;//air
    private Integer numberOfShipments;
    private Double actualWeight;
    private String driverName;
    private String driverContact;
    private String referenceNumber;
    private String vehicle;
    private Integer numberOfPallets;
    private Integer numberOfBags;
    private Integer numberOfPalletsReceived;
    private Integer numberOfBagsReceived;
    private String vehicleNumber;
    private String tagNumber;
    private Long sealNumber;
    private String attachments;//new both
    private String status;
    private String remarks;
    private String routeNumber;
    private Integer numberOfBoxes;
    private LocalDateTime ata;// new both
    private Integer totalShipments;// new both
    private Integer overages;// new both
    private String overageAWBs;// new both
    private Integer received;// new both
    private Integer shortages;// new both
    private String shortageAWBs;// new both
    private Boolean redFlag;
    private String preAlertType;
    private long transitTimeTaken;
    private int trip;
    private Integer damage;
    private String damageAwbs;

}
