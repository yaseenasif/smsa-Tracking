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
    private String fromDate;
    private String toDate;
    private String status;
    private String origin;
    private String destination;
    private String routeNumber;
    private User user;
    private boolean activeStatus;
}
