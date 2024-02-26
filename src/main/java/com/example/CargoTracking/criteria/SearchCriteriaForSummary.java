package com.example.CargoTracking.criteria;

import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.Location;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SearchCriteriaForSummary {
    private String fromDate;
    private String toDate;
    private String status;
    private Location origin;
    private Set<Location> destinations;
    private DomesticRoute route;
}
