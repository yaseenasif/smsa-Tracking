package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.*;
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
    private Country originCountry;
    private Country destinationCountry;
    private Facility originFacility;
    private Facility destinationFacility;
    private Location originLocation;
    private Location destinationLocation;
    private Boolean refrigeratedTruck;
    private DomesticRoute route;
    //remove
    private int duration;
    private Integer numberOfShipments;
    private Double weight;
    private LocalDateTime etd;
    private LocalDateTime eta;
    private LocalDateTime atd;
    private Driver driver;
    //remove
    private String driverContact;
    private String referenceNumber;
    private VehicleType vehicleType;
    private Integer numberOfPallets;
    private Integer numberOfBags;
    private String vehicleNumber;
    private String tagNumber;
    private Long sealNumber;
    private String status;
    private String remarks;
    private LocalDateTime ata;
    private Integer totalShipments;
    private Integer numberOfBoxes;
    private String overages;
    private String overagesAwbs;
    private String received;
    private String shortages;
    private String shortagesAwbs;
    private String attachments;
    private Boolean redFlag;
    private String preAlertNumber;
    private String preAlertType;
    private long transitTimeTaken;
    private Boolean activeStatus;
}
