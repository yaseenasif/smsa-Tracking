package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Country;
import com.example.CargoTracking.model.Location;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

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
}
