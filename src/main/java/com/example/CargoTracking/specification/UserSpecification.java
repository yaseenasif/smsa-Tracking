package com.example.CargoTracking.specification;

import com.example.CargoTracking.dto.LocationDto;
import com.example.CargoTracking.dto.SearchCriteriaForUser;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.Roles;
import com.example.CargoTracking.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> findUser(SearchCriteriaForUser searchCriteriaForUser) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Location> locationJoin = root.join("locations");
            Join<User, Roles> rolesJoin=root.join("roles");
            query.distinct(true);
            return criteriaBuilder.and(
                    criteriaBuilder.like(root.get("name"), "%" + searchCriteriaForUser.getName().toLowerCase().trim() + "%"),
                    criteriaBuilder.like(root.get("email"), "%" + searchCriteriaForUser.getEmail().toLowerCase().trim() + "%"),
                    criteriaBuilder.equal(root.get("status"), Boolean.parseBoolean(searchCriteriaForUser.getStatus())),
                    criteriaBuilder.like(locationJoin.get("locationName"),"%" + searchCriteriaForUser.getLocation().toLowerCase().trim() + "%"),
                    criteriaBuilder.like(rolesJoin.get("name"),"%" + searchCriteriaForUser.getRole().toLowerCase().trim() + "%")
            );
        };
    }
}
