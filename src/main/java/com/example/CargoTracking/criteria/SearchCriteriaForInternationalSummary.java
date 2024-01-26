package com.example.CargoTracking.criteria;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SearchCriteriaForInternationalSummary {
    private String fromDate;
    private String toDate;
    private String status;
    private String origin;
    private Set<String> destinations;
    private String type;
    private String routeNumber;
}
