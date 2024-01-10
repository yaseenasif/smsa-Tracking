package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Country;
import com.example.CargoTracking.model.Location;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FacilityDto    {
    private Long id;
    private String name;
    private boolean status;
    @NotBlank(message = "Country is required")
    private Country country;
}
