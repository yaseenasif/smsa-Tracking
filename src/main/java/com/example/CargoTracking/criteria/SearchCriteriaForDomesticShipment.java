package com.example.CargoTracking.criteria;

import com.example.CargoTracking.model.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SearchCriteriaForDomesticShipment {
    private String value;
    private User user;
}
