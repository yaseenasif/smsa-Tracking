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
@Data
@Builder
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean status;

    @OneToMany(mappedBy = "country",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Facility> facilities = new ArrayList<>();
}
