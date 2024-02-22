package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.model.DomesticShipment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DomesticShipmentSpecification {
//    public static Specification<DomesticShipment> getSearchSpecification(SearchCriteriaForDomesticShipment searchCriteria){
//        LocalDate localFromDate;
//        LocalDate localToDate;
//        if(!searchCriteria.getFromDate().isEmpty() && !searchCriteria.getToDate().isEmpty() && searchCriteria.getUser() != null ) {
//            String fromDate = searchCriteria.getFromDate();
//            String toDate = searchCriteria.getToDate();
//            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            localFromDate = LocalDate.parse(fromDate, targetFormatter);
//            localToDate = LocalDate.parse(toDate, targetFormatter);
//            return (root, query, criteriaBuilder) ->
//
//                    criteriaBuilder.and(
//                            criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate ),
//                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
//                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("originLocation")),searchCriteria.getOrigin()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("destinationLocation")),searchCriteria.getDestination()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("route")),searchCriteria.getRoute()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("createdBy")),searchCriteria.getUser()) );
//
//        }else if(searchCriteria.getFromDate().isEmpty() && searchCriteria.getToDate().isEmpty() && searchCriteria.getUser() != null){
//            return (root, query, criteriaBuilder) ->
//
//                    criteriaBuilder.and(
//                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
//                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("originLocation")),searchCriteria.getOrigin()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("destinationLocation")),searchCriteria.getDestination()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("route")),searchCriteria.getRoute()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("createdBy")),searchCriteria.getUser()) );
//
//        }
//        else if(!searchCriteria.getFromDate().isEmpty() && !searchCriteria.getToDate().isEmpty() && searchCriteria.getUser() == null){
//            String fromDate = searchCriteria.getFromDate();
//            String toDate = searchCriteria.getToDate();
//            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            localFromDate = LocalDate.parse(fromDate, targetFormatter);
//            localToDate = LocalDate.parse(toDate, targetFormatter);
//            return (root, query, criteriaBuilder) ->
//
//                    criteriaBuilder.and(
//                            criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate ),
//                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
//                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("originLocation")),searchCriteria.getOrigin()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("destinationLocation")),searchCriteria.getDestination()),
//                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("route")),searchCriteria.getRoute()));
//        }
//        else{
//
//                return (root, query, criteriaBuilder) ->
//                        criteriaBuilder.and(
//                                criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%"),
//                                criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
//                                criteriaBuilder.equal(criteriaBuilder.lower(root.get("originLocation")),searchCriteria.getOrigin()),
//                                criteriaBuilder.equal(criteriaBuilder.lower(root.get("destinationLocation")),searchCriteria.getDestination()),
//                                criteriaBuilder.equal(criteriaBuilder.lower(root.get("route")),searchCriteria.getRoute()));
//
//
//            }
//    }

    public static Specification<DomesticShipment> getSearchSpecification(SearchCriteriaForDomesticShipment searchCriteria) {
        if (searchCriteria == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            // Initialize local date variables
            LocalDate localFromDate = null;
            LocalDate localToDate = null;

            // Parse date strings if they are not empty
            if (searchCriteria.getFromDate()!=null && searchCriteria.getToDate()!=null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                localFromDate = LocalDate.parse(searchCriteria.getFromDate(), formatter);
                localToDate = LocalDate.parse(searchCriteria.getToDate(), formatter);
            }

            // Construct predicates based on search criteria
            return criteriaBuilder.and(
                    // Check if createdAt is within the specified date range
                    localFromDate != null && localToDate != null ?
                            criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate) : criteriaBuilder.conjunction(),
                    // Check active status
                    criteriaBuilder.equal(root.get("activeStatus"), searchCriteria.isActiveStatus()),
                    // Check status (if provided)
                    searchCriteria.getStatus() != null ?
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteria.getStatus() + "%") :
                            criteriaBuilder.conjunction(),
                    // Check origin location (if provided)
                    searchCriteria.getOrigin() != null ?
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("originLocation")), searchCriteria.getOrigin()) :
                            criteriaBuilder.conjunction(),
                    // Check destination location (if provided)
                    searchCriteria.getDestination() != null ?
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("destinationLocation")), searchCriteria.getDestination()) :
                            criteriaBuilder.conjunction(),
                    // Check route (if provided)
                    searchCriteria.getRoute() != null ?
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("route")), searchCriteria.getRoute()) :
                            criteriaBuilder.conjunction(),
                    // Check createdBy user (if provided)
                    searchCriteria.getUser() != null ?
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("createdBy")), searchCriteria.getUser()) :
                            criteriaBuilder.conjunction()
            );
        };
    }






}
