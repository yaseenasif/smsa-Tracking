package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.Roles;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {

    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;
    private String employeeId;
    @NotBlank(message = "Role is required")
    private Set<Roles> roles;
    private boolean status;
    private Set<Location> locations;
    private Set<Location> domesticOriginLocations;
    private Set<Location> domesticDestinationLocations;
    private Set<Location> internationalAirOriginLocation;
    private Set<Location> internationalAirDestinationLocation;
    private Set<Location> internationalRoadOriginLocation;
    private Set<Location> internationalRoadDestinationLocation;

}
