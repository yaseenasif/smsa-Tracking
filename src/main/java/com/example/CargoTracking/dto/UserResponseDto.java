package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.Roles;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserResponseDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Role is required")
    private Set<Roles> roles;


    private Set<Location> locations;

    private Set<Location> domesticOriginLocations;
    private Set<Location> domesticDestinationLocations;
    private Set<Location> internationalAirOriginLocation;
    private Set<Location> internationalAirDestinationLocation;
    private Set<Location> internationalRoadOriginLocation;
    private Set<Location> internationalRoadDestinationLocation;
}
