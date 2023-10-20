package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteria;
import com.example.CargoTracking.model.Driver;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + finalSearchCriteria + "%");
    }

}
