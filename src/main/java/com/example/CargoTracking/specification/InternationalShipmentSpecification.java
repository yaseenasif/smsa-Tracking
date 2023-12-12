package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalShipment;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InternationalShipmentSpecification {
    public static Specification<InternationalShipment> getSearchSpecification(SearchCriteriaForInternationalShipment searchCriteria) {
//        searchCriteria = searchCriteria
//                .replace("\\", "\\\\")
//                .replace("%2B", "\\+")
//                .replace("%", "\\%")
//                .replace("_", "\\_")
//                .trim();
//        String finalSearchCriteria = searchCriteria;

        LocalDate localFromDate;
        LocalDate localToDate;
        if(!searchCriteria.getFromDate().isEmpty() && !searchCriteria.getToDate().isEmpty() && searchCriteria.getUser() != null ) {
            String fromDate = searchCriteria.getFromDate();
            String toDate = searchCriteria.getToDate();
            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localFromDate = LocalDate.parse(fromDate, targetFormatter);
            localToDate = LocalDate.parse(toDate, targetFormatter);
            return (root, query, criteriaBuilder) ->

                    criteriaBuilder.and(
                            criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate ),
                            criteriaBuilder.equal(root.get("activeStatus"),true),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originCountry")), "%" + searchCriteria.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationCountry")), "%" + searchCriteria.getDestination() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),
                                    "%" + searchCriteria.getType() + "%"),
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("createdBy")),searchCriteria.getUser()) );

        }else if(searchCriteria.getFromDate().isEmpty() && searchCriteria.getToDate().isEmpty() && searchCriteria.getUser() != null){
            return (root, query, criteriaBuilder) ->

                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
                            criteriaBuilder.equal(root.get("activeStatus"),true),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originCountry")), "%" + searchCriteria.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationCountry")), "%" + searchCriteria.getDestination() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),
                                    "%" + searchCriteria.getType() + "%"),
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("createdBy")),searchCriteria.getUser()) );

        }
        else if(!searchCriteria.getFromDate().isEmpty() && !searchCriteria.getToDate().isEmpty() && searchCriteria.getUser() == null){
            String fromDate = searchCriteria.getFromDate();
            String toDate = searchCriteria.getToDate();
            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localFromDate = LocalDate.parse(fromDate, targetFormatter);
            localToDate = LocalDate.parse(toDate, targetFormatter);
            return (root, query, criteriaBuilder) ->

                    criteriaBuilder.and(
                            criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate ),
                            criteriaBuilder.equal(root.get("activeStatus"),true),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originCountry")), "%" + searchCriteria.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationCountry")), "%" + searchCriteria.getDestination() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),
                                    "%" + searchCriteria.getType() + "%")
                    );
        }
        else{

            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
                            criteriaBuilder.equal(root.get("activeStatus"),true),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originCountry")), "%" + searchCriteria.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationCountry")), "%" + searchCriteria.getDestination() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),
                                    "%" + searchCriteria.getType() + "%")

                    );
        }
    }

}



