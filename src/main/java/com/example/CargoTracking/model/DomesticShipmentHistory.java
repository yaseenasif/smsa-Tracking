package com.example.CargoTracking.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class DomesticShipmentHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private LocalDateTime processTime;
    private String locationCode;
    private Long user;
    private String remarks;
    private Boolean activeStatus;

    @ManyToOne
    @JoinColumn(name = "domestic_shipment_id")
    private DomesticShipment domesticShipment;


}
