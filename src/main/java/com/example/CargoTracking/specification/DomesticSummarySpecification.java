package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.model.DomesticShipment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DomesticSummarySpecification {
    public static Specification<DomesticShipment> getSearchSpecification(SearchCriteriaForSummary searchCriteriaForSummary){
        LocalDate localFromDate;
        LocalDate localToDate;
        if(!searchCriteriaForSummary.getFromDate().isEmpty() || !searchCriteriaForSummary.getToDate().isEmpty() ){
            String fromDate = searchCriteriaForSummary.getFromDate();
            String toDate = searchCriteriaForSummary.getToDate();
            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             localFromDate = LocalDate.parse(fromDate,targetFormatter);
             localToDate = LocalDate.parse(toDate,targetFormatter);
        }else{
            return (root, query, criteriaBuilder) ->

                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForSummary.getStatus() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteriaForSummary.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteriaForSummary.getDestination() + "%")
                    );
        }



        return (root, query, criteriaBuilder) ->

                criteriaBuilder.and(
                        criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate ),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForSummary.getStatus() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteriaForSummary.getOrigin() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteriaForSummary.getDestination() + "%")
                        );
    }
}
