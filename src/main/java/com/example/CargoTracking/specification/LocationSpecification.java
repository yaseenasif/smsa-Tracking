package com.example.CargoTracking.specification;

import com.example.CargoTracking.dto.SearchCriteriaForLocation;
import com.example.CargoTracking.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class LocationSpecification {
    public static Specification<Location> findLocation(SearchCriteriaForLocation searchCriteriaForLocation) {
        return (root, query, criteriaBuilder) -> {
            Join<Location, Facility> facilityJoin = root.join("facility");
            Join<Location, Country> countryJoin=root.join("country");
            query.distinct(true);

            return criteriaBuilder.and(
                    criteriaBuilder.like(root.get("locationName"), "%" + searchCriteriaForLocation.getLocationName().toLowerCase().trim() + "%"),
                    criteriaBuilder.like(root.get("type"), "%" + searchCriteriaForLocation.getLocationType().toLowerCase().trim() + "%"),
                    criteriaBuilder.like(facilityJoin.get("name"),"%" + searchCriteriaForLocation.getFacility().toLowerCase().trim() + "%"),
                    criteriaBuilder.like(countryJoin.get("name"),"%" + searchCriteriaForLocation.getCountry().toLowerCase().trim() + "%"),
                    criteriaBuilder.equal(root.get("status"), Boolean.parseBoolean(searchCriteriaForLocation.getStatus()))
            );
        };
    }
}
