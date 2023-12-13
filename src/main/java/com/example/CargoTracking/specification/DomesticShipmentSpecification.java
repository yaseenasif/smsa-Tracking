package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.model.DomesticShipment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteria.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteria.getDestination() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%"),
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("createdBy")),searchCriteria.getUser()) );

        }else if(searchCriteria.getFromDate().isEmpty() && searchCriteria.getToDate().isEmpty() && searchCriteria.getUser() != null){
            return (root, query, criteriaBuilder) ->

                    criteriaBuilder.and(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteria.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteria.getDestination() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%"),
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
                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteria.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteria.getDestination() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%")
                    );
        }
        else{

                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
                                criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteria.getOrigin() + "%"),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteria.getDestination() + "%"),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%")

                        );
            }
        }






}
