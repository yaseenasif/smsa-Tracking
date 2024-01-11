package com.example.CargoTracking.criteria;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SearchCriteriaForInternationalRoute {
    private String value;
    private String type;
    private boolean status;
}
