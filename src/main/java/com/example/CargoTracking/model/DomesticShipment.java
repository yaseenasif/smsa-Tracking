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
@Data
@Builder
@Entity
public class DomesticShipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originFacility;
    private String originLocation;
    private Boolean refrigeratedTruck;
    private String destinationFacility;
    private String destinationLocation;
    private String routeNumber;
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
    private String vehicleNumber;
    private Long tagNumber;
    private Long sealNumber;
    private String status;
    private String remarks;
    private LocalDateTime ata;
    private Integer totalShipments;
    private String overages;
    private String overagesAwbs;
    private String received;
    private String shortages;
    private String shortagesAwbs;
    private String attachments;
    private Boolean redFlag;
    @Column(unique = true)
    private String preAlertNumber;
    private String preAlertType;
    private long transitTimeTaken;
    private LocalDateTime createdTime;
    private LocalDateTime arrivedTime;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Boolean activeStatus;
    private boolean escalationFlagOne;
    private boolean escalationFlagTwo;
    private boolean escalationFlagThree;


//    private List<String> urls;

    // attachments dalega
//    private String createdBy;
    private LocalDate createdAt;
//    private String updatedBy;
    private LocalDate updatedAt;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User UpdatedBy;

    @OneToMany(mappedBy = "domesticShipment",cascade = CascadeType.REMOVE)
    private List<DomesticShipmentHistory> domesticShipmentHistories ;

    @OneToMany(mappedBy = "domesticShipment", cascade = CascadeType.REMOVE)
    private List<FileMetaData> files;

    @Override
    public String toString() {
        return "DomesticShipment{" +
                "id=" + id +
                ", originFacility='" + originFacility + '\'' +
                ", originLocation='" + originLocation + '\'' +
                ", refrigeratedTruck=" + refrigeratedTruck +
                '}';
    }
}
