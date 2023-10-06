package com.example.CargoTracking.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class LocationPort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String portName;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
