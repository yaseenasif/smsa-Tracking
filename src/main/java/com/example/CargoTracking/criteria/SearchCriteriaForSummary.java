package com.example.CargoTracking.criteria;

import lombok.*;

import java.time.LocalDate;

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
    private String destination;
}
