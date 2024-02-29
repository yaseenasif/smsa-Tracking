package com.example.CargoTracking.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
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
@Entity
public class InternationalShipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originCountry;
    private String originFacility;
    private String originLocation;
    private Long originLocationId;
    private Boolean refrigeratedTruck;//vehicle
    private String type;
    private String shipmentMode;
    @Column(unique = true)
    private String preAlertNumber;//new
    private String destinationCountry;
    private String destinationFacility;
    private String destinationLocation;
    private Long destinationLocationId;
    private String carrier;//air
    private LocalDateTime etd;
    private LocalDateTime eta;
    private LocalDateTime atd;
    private String flightNumber;//air
    private Integer numberOfShipments;
    private Integer numberOfBoxes;
    private Double actualWeight;
    private String driverName;
    private String driverContact;
    private String referenceNumber;//Master CONS
    private String vehicleType;
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
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime arrivedTime;
    private LocalDateTime clearedTime;
    private boolean escalationFlagOne;
    private boolean escalationFlagTwo;
    private boolean escalationFlagThree;
    private boolean activeStatus;
    private int trip;
    private Integer damage;
    private String damageAwbs;

    @ManyToOne
    private User createdBy;
    private LocalDate createdAt;
    @ManyToOne
    private User updatedBy;
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "internationalShipment")
    private List<InternationalShipmentHistory> shipmentHistory;

    @OneToMany(mappedBy = "internationalShipment", cascade = CascadeType.REMOVE)
    private List<FileMetaData> files;

    @Override
    public String toString() {
        return "InternationalShipment{" +
                "id=" + id +
                ", originCountry='" + originCountry + '\'' +
                ", originFacility='" + originFacility + '\'' +
                ", originLocation='" + originLocation + '\'' +
                ", refrigeratedTruck=" + refrigeratedTruck +
                ", type='" + type + '\'' +
                ", shipmentMode='" + shipmentMode + '\'' +
                '}';
    }
}
