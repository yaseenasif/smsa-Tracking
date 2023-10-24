package com.example.CargoTracking.specification;

import com.example.CargoTracking.model.Driver;
import org.springframework.data.jpa.domain.Specification;

public class DriverSpecification  {

    public static Specification<Driver> getSearchSpecification(String searchCriteria){
        searchCriteria = searchCriteria
                .replace("\\", "\\\\")
                .replace("%2B", "\\+")
                .replace("%", "\\%")
                .replace("_", "\\_")
                .trim();
        String finalSearchCriteria = searchCriteria;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + finalSearchCriteria + "%")
                );
    }

}
