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
    private String originPort;
    private Boolean refrigeratedTruck;//new road
    private String type;
    private String shipmentMode;
    private long preAlertNumber;//new
    private String destinationCountry;
    private String destinationPort;
    private String carrier;//air
    private LocalDate departureDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;
    private LocalDate etd;
    private LocalDate eta;
    private LocalDate atd;
    private Integer flightNumber;//air
    private Integer numberOfShipments;
    private LocalDate arrivalDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;
    private Double actualWeight;
    private String driverName;
    private String driverContact;
    private String referenceNumber;
    private String vehicleType;
    private Integer numberOfPallets;
    private Integer numberOfBags;
    private String vehicleNumber;
    private Long tagNumber;
    private Long sealNumber;
    private String attachments;//new both
    private String status;
    private String remarks;
    private String routeNumber;
    private LocalDate ata;// new both
    private Integer totalShipments;// new both
    private String overages;// new both
    private String overageAWBs;// new both
    private String received;// new both
    private String shortages;// new both
    private String shortageAWBs;// new both
    private Boolean redFlag;
    private String preAlertType;
    private long transitTimeTaken;

}
