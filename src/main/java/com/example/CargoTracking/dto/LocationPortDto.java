package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Location;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LocationPortDto {

    private Long id;
    private String portName;
    private boolean status;
    private String location;


}
