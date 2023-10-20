package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.model.DomesticShipment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DomesticSummarySpecification {
    public static Specification<DomesticShipment> getSearchSpecification(SearchCriteriaForSummary searchCriteriaForSummary){
//        searchCriteria.setSearchText(searchCriteria.getSearchText()
//                .replace("\\", "\\\\")
//                .replace("%2B","\\+")
//                .replace("%", "\\%")
//                .replace("_", "\\_")
//                .trim());
//        String finalSearchCriteria = searchCriteria;
        String fromDate = searchCriteriaForSummary.getFromDate();
        String toDate = searchCriteriaForSummary.getToDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localFromDate = LocalDate.parse(fromDate, formatter);
        LocalDate localToDate = LocalDate.parse(toDate, formatter);


        return (root, query, criteriaBuilder) ->

                criteriaBuilder.and(
                        criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate ),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForSummary.getStatus() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteriaForSummary.getOrigin() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteriaForSummary.getDestination() + "%")
                        );
    }
}
