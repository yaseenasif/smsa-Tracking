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
public class ShipmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private LocalDateTime processTime;
    private String locationCode;
    private Long user;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

}
