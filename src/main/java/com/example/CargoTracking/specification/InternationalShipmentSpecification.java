package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalShipment;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import org.springframework.data.jpa.domain.Specification;

public class InternationalShipmentSpecification {
    public static Specification<InternationalShipment> getSearchSpecification(SearchCriteriaForInternationalShipment searchCriteria) {
//        searchCriteria = searchCriteria
//                .replace("\\", "\\\\")
//                .replace("%2B", "\\+")
//                .replace("%", "\\%")
//                .replace("_", "\\_")
//                .trim();
//        String finalSearchCriteria = searchCriteria;

       if(searchCriteria.getUser() == null){
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")),
                                    "%" + searchCriteria.getValue() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),
                                    "%" + searchCriteria.getType() + "%")

                    );
        }else{
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")),
                                    "%" + searchCriteria.getValue() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),
                                    "%" + searchCriteria.getType() + "%"),
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("createdBy")), searchCriteria.getUser())

                    );
        }

    }


}
