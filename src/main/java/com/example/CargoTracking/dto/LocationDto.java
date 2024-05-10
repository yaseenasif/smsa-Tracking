package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Country;
import com.example.CargoTracking.model.Facility;
import com.example.CargoTracking.model.Location;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LocationDto {

    private Long id;
    @NotBlank(message = "Location name is required")
    private String locationName;
    private String type;
    private boolean status;
    private String originEmail;
    private String destinationEmail;
//    private String originEscalation;
    private String originEscalationLevel1;
    private String originEscalationLevel2;
    private String originEscalationLevel3;
//    private String destinationEscalation;
    private String destinationEscalationLevel1;
    private String destinationEscalationLevel2;
    private String destinationEscalationLevel3;
    private Country country;
    private Facility facility;

}
