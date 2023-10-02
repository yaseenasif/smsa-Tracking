package com.example.CargoTracking.dto;

import com.example.CargoTracking.model.Location;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String password;
    private String role;
    private String location;


}
