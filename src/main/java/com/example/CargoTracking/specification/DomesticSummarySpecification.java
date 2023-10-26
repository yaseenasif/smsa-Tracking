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
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
////            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//            // First formatter for parsing the original date string
//            DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//
//// Parse the original date string into a LocalDateTime
//            LocalDateTime localDateTimeFrom = LocalDateTime.parse(fromDate, originalFormatter);
//            LocalDateTime localDateTimeTo = LocalDateTime.parse(toDate, originalFormatter);
//
//// Second formatter for formatting into "yyyy-MM-dd" format
            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//// Format the LocalDateTime into a string with the "yyyy-MM-dd" format
//            String formattedDateFrom = localDateTimeFrom.format(targetFormatter);
//            String formattedDateTo = localDateTimeTo.format(targetFormatter);
//
//
//// Parse the formatted string into a LocalDate
//             localFromDate = LocalDate.parse(formattedDateFrom, targetFormatter);
//            localToDate = LocalDate.parse(formattedDateTo, targetFormatter);

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
