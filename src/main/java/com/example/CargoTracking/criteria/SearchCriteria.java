package com.example.CargoTracking.criteria;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SearchCriteria {
    private String value;
    private boolean status;
}
