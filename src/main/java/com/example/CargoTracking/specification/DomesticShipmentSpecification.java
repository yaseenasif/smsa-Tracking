package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.model.DomesticShipment;
import org.springframework.data.jpa.domain.Specification;

public class DomesticShipmentSpecification {
    public static Specification<DomesticShipment> getSearchSpecification(SearchCriteriaForDomesticShipment searchCriteria){
//        searchCriteria = searchCriteria
//                .replace("\\", "\\\\")
//                .replace("%2B", "\\+")
//                .replace("%", "\\%")
//                .replace("_", "\\_")
//                .trim();
//        String finalSearchCriteria = searchCriteria;
//        if(searchCriteria.getUser()==null){
//            return (root, query, criteriaBuilder) ->
//                    criteriaBuilder.and(
//                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getValue() + "%")
//                    );
//        }
        if(searchCriteria.getUser() == null){
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getValue() + "%")

                    );
        }else{
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getValue() + "%"),
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("createdBy")),searchCriteria.getUser())

                    );
            }
        }


}
