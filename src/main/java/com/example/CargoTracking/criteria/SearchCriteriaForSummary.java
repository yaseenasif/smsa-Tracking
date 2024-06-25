package com.example.CargoTracking.criteria;

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
    private String origin;
    private String preAlertNumber;
    private String masterCONS;
    private Set<String> destinations;
    private String routeNumber;
}
