package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteria;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.Driver;
import org.springframework.data.jpa.domain.Specification;

public class DomesticRouteSpecification {
    public static Specification<DomesticRoute> getSearchSpecification(SearchCriteria searchCriteria){
//        searchCriteria = searchCriteria
//                .replace("\\", "\\\\")
//                .replace("%2B", "\\+")
//                .replace("%", "\\%")
//                .replace("_", "\\_")
//                .trim();
//        String finalSearchCriteria = searchCriteria;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("route")), "%" + searchCriteria.getValue() + "%"),
                        criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isStatus())
                );
    }
}
