package com.example.CargoTracking.criteria;

import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.Location;
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
    private Location origin;
    private Location destination;
    private DomesticRoute route;
    private User user;
    private boolean activeStatus;
}
