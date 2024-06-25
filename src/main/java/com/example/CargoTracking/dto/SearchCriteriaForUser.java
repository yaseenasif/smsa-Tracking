package com.example.CargoTracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaForUser {
    String location;
    String email;
    String name;
    String role;
    String status;
}
