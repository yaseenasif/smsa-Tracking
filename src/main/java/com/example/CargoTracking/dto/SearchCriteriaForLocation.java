package com.example.CargoTracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaForLocation {
   String locationName;
   String locationType;
   String facility;
   String country;
   String status;
}
