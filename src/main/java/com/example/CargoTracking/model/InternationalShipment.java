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
    private String originPort;
    private Boolean refrigeratedTruck;//new road
    private String type;
    private String shipmentMode;
    private long preAlertNumber;//new
    private String destinationCountry;
    private String destinationPort;
    private String carrier;//air
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate etd;
    private LocalDate eta;
    private LocalDate atd;
    private Integer flightNumber;//air
    private Integer numberOfShipments;
    private LocalDate arrivalDate;
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
    private LocalDateTime createdTime;
    private LocalDateTime arrivedTime;

    private int trip;

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
                ", originPort='" + originPort + '\'' +
                ", refrigeratedTruck=" + refrigeratedTruck +
                ", type='" + type + '\'' +
                ", shipmentMode='" + shipmentMode + '\'' +
                '}';
    }
}
