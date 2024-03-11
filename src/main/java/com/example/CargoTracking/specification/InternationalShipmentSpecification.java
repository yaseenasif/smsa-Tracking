package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalShipment;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

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
                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
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
                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
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
                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
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
                            criteriaBuilder.equal(root.get("activeStatus"),searchCriteria.isActiveStatus()),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("originCountry")), "%" + searchCriteria.getOrigin() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationCountry")), "%" + searchCriteria.getDestination() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteria.getRouteNumber() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),
                                    "%" + searchCriteria.getType() + "%")

                    );
        }
    }


    public static Specification<InternationalShipment> withCreatedYearAndUser(Integer year, User user) {
        return (Root<InternationalShipment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
            LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59);
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("createdTime"), startOfYear, endOfYear));

            if (user != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("createdBy"), user));
            }

            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(root.get("activeStatus")));

            return predicate;
        };
    }


    public static Specification<InternationalShipment> withDestinationLocationsAndActive(Integer year, Set<String> destinations) {
        return (Root<InternationalShipment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            // If destinations are not provided, return a predicate that always evaluates to false
            if (destinations == null || destinations.isEmpty()) {
                return criteriaBuilder.and((Predicate) criteriaBuilder.literal(false));
            }

            Predicate predicate = criteriaBuilder.conjunction();

            // Filter by created year
            if (year != null) {
                LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
                LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("createdTime"), startOfYear, endOfYear));
            }

            // Filter by destination locations
            if (!destinations.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("destinationLocation").in(destinations));
            }

            // Filter by active status
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(root.get("activeStatus")));

            return predicate;
        };
    }

}



