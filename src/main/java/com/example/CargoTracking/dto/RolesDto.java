package com.example.CargoTracking.dto;


import com.example.CargoTracking.model.Permission;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolesDto {

    private Long id;
    private String name;
    private Set<Permission> permissions = new HashSet<>();

}
