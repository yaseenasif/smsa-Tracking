package com.example.CargoTracking.criteria;

import lombok.*;

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
    private String destination;
    private String type;
}
