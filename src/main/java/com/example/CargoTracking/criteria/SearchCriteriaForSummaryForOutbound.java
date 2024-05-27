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
public class SearchCriteriaForSummaryForOutbound {
  private String fromDate;
  private String toDate;
  private String status;
  private Set<String> origin;
  private String destinations;
  private String routeNumber;
}
