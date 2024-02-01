package com.example.CargoTracking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationName;
    private String type;
    private boolean status;
    private String originEmail;
    private String destinationEmail;
    private String originEscalation;
    private String destinationEscalation;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", locationName='" + locationName + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", originEmail='" + originEmail + '\'' +
                ", destinationEmail='" + destinationEmail + '\'' +
                '}';
    }
}
