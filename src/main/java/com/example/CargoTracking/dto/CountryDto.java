package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Facility;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CountryDto {
    private Long id;
    private String name;
    private Boolean status;
    private List<Facility> facilities;
}
