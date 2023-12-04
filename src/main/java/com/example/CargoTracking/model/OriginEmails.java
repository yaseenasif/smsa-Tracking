package com.example.CargoTracking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class OriginEmails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originEmail;

    @ManyToMany(mappedBy = "originEmailsList")
    @JsonIgnore
    Set<Location> locations = new HashSet<>();

}
