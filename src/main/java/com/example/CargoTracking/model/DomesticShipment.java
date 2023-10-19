package com.example.CargoTracking.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class DomesticShipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originFacility;
    private String originLocation;
    private String refrigeratedTruck;
    private String destinationFacility;
    private String destinationLocation;
    private String routeNumber;
    private Integer numberOfShipments;
    private Double weight;
    private String etd;
    private String eta;
    private String atd;
    private String driverName;
    private String driverContact;
    private String referenceNumber;
    private String vehicleType;
    private Integer numberOfPallets;
    private Integer numberOfBags;
    private String vehicleNumber;
    private Long tagNumber;
    private Long sealNumber;
    private String status;
    private String remarks;
    private String ata;
    private Integer totalShipments;
    private String overages;
    private String overagesAwbs;
    private String received;
    private String shortages;
    private String shortagesAwbs;
    private String attachments;

    // attachments dalega
//    private String createdBy;
    private LocalDate createdAt;
//    private String updatedBy;
    private LocalDate updatedAt;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User UpdatedBy;

    @OneToMany(mappedBy = "domesticShipment")
    private List<DomesticShipmentHistory> domesticShipmentHistories ;

}
