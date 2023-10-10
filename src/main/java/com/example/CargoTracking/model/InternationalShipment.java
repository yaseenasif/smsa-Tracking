package com.example.CargoTracking.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
public class InternationalShipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originCountry;
    private String originPort;
    private Boolean refrigeratedTruck;//new road
    private String type;
    private String shipmentMode;
    private String preAlertNumber;//new
    private String destinationCountry;
    private String destinationPort;
    private String carrier;//air
    private Date departureDate;
    private Time departureTime;
    private Integer flightNumber;//air
    private Integer numberOfShipments;
    private Date arrivalDate;
    private Time arrivalTime;
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
    private String ata;// new both
    private Integer totalShipments;// new both
    private String overages;// new both
    private String overageAWBs;// new both
    private String received;// new both
    private String shortages;// new both
    private String shortageAWBs;// new both
    private String createdBy;
    private LocalDate createdAt;
    private String updatedBy;
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "internationalShipment")
    private List<InternationalShipmentHistory> shipmentHistory;
}
