package com.example.CargoTracking.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LoginCredentials {

    private String name;
    private String password;

}
