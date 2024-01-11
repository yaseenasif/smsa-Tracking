package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalRoute;
import com.example.CargoTracking.model.InternationalRoute;
import org.springframework.data.jpa.domain.Specification;

public class InternationalRouteSpecification {
    public static Specification<InternationalRoute> getSearchSpecification(SearchCriteriaForInternationalRoute searchCriteriaForInternationalRoute){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("route")), "%" + searchCriteriaForInternationalRoute.getValue() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + searchCriteriaForInternationalRoute.getType() + "%"),
                        criteriaBuilder.equal(root.get("status"),searchCriteriaForInternationalRoute.isStatus())
                );
    }
}
