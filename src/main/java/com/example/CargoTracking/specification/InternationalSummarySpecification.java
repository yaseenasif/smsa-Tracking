package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InternationalSummarySpecification {

    public static Specification<InternationalShipment> getSearchSpecification(SearchCriteriaForInternationalSummary criteria){

        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

       return  (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteria.getFromDate() != null && !criteria.getFromDate().isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), LocalDate.parse(criteria.getFromDate(),targetFormatter)));
            }
            if (criteria.getToDate() != null && !criteria.getToDate().isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), LocalDate.parse(criteria.getToDate(),targetFormatter)));
            }
            if (criteria.getOrigin() != null && !criteria.getOrigin().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("originLocation"), criteria.getOrigin()));
            }
            if (criteria.getDestinations() != null && !criteria.getDestinations().isEmpty()) {
                predicates.add(root.get("destinationLocation").in(criteria.getDestinations()));
            }
            if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("type"), criteria.getType()));
            }
            if (criteria.getRouteNumber() != null && !criteria.getRouteNumber().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("routeNumber"), criteria.getRouteNumber()));
            }
            if(criteria.getStatus() != null && !criteria.getStatus().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + criteria.getStatus() + "%"));
            }

            predicates.add(criteriaBuilder.equal(root.get("activeStatus"),true));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
