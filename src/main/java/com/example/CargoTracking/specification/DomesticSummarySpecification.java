package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.model.DomesticShipment;
import lombok.var;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DomesticSummarySpecification {
    public static Specification<DomesticShipment> getSearchSpecification(SearchCriteriaForSummary searchCriteriaForSummary) {

            return (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                // Add date range conditions
                if (searchCriteriaForSummary.getFromDate() != null && !searchCriteriaForSummary.getFromDate().isEmpty()) {
                    LocalDate fromDate = LocalDate.parse(searchCriteriaForSummary.getFromDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
                }
                if (searchCriteriaForSummary.getToDate() != null && !searchCriteriaForSummary.getToDate().isEmpty()) {
                    LocalDate toDate = LocalDate.parse(searchCriteriaForSummary.getToDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDate));
                }

                // Add other conditions
                if (searchCriteriaForSummary.getStatus() != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForSummary.getStatus().toLowerCase().trim() + "%"));
                }
                if (searchCriteriaForSummary.getOrigin() != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteriaForSummary.getOrigin().toLowerCase().trim() + "%"));
                }
                if (searchCriteriaForSummary.getRouteNumber() != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteriaForSummary.getRouteNumber().toLowerCase().trim() + "%"));
                }
                if (searchCriteriaForSummary.getPreAlertNumber() != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("preAlertNumber")), "%" + searchCriteriaForSummary.getPreAlertNumber().toLowerCase().trim() + "%"));
                }
                if (searchCriteriaForSummary.getMasterCONS() != null) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("referenceNumber")), "%" + searchCriteriaForSummary.getMasterCONS().toLowerCase().trim() + "%"));
                }
                if (searchCriteriaForSummary.getDestinations() != null && !searchCriteriaForSummary.getDestinations().isEmpty()) {
                    predicates.add(root.get("destinationLocation").in(searchCriteriaForSummary.getDestinations()));
                }

                // Add active status condition
                predicates.add(criteriaBuilder.equal(root.get("activeStatus"), true));

                // Combine all predicates
                return criteriaBuilder.and(predicates.toArray(new javax.persistence.criteria.Predicate[0]));
            };
        }


        //        LocalDate localFromDate;
//        LocalDate localToDate;
//
//        if (!searchCriteriaForSummary.getFromDate().isEmpty() || !searchCriteriaForSummary.getToDate().isEmpty()) {
//            String fromDate = searchCriteriaForSummary.getFromDate();
//            String toDate = searchCriteriaForSummary.getToDate();
//            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            localFromDate = LocalDate.parse(fromDate, targetFormatter);
//            localToDate = LocalDate.parse(toDate, targetFormatter);
//        } else {
//            return (root, query, criteriaBuilder) -> {
//                var predicate = criteriaBuilder.and(
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForSummary.getStatus().toLowerCase().trim() + "%"),
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteriaForSummary.getOrigin().toLowerCase().trim() + "%"),
//                        criteriaBuilder.equal(root.get("activeStatus"), true),
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteriaForSummary.getRouteNumber().toLowerCase().trim() + "%"),
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("preAlertNumber")), "%" + searchCriteriaForSummary.getPreAlertNumber().toLowerCase().trim() + "%"),
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("referenceNumber")), "%" + searchCriteriaForSummary.getMasterCONS().toLowerCase().trim() + "%")
//                );
//
//                if (searchCriteriaForSummary.getDestinations() != null && !searchCriteriaForSummary.getDestinations().isEmpty()) {
//                    predicate = criteriaBuilder.and(predicate, root.get("destinationLocation").in(searchCriteriaForSummary.getDestinations()));
//                }
//
//                return predicate;
//            };
//        }
//
//        return (root, query, criteriaBuilder) -> {
//            var predicate = criteriaBuilder.and(
//                    criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate),
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForSummary.getStatus().toLowerCase().trim() + "%"),
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("originLocation")), "%" + searchCriteriaForSummary.getOrigin().toLowerCase().trim() + "%"),
//                    criteriaBuilder.equal(root.get("activeStatus"), true),
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("preAlertNumber")), "%" + searchCriteriaForSummary.getPreAlertNumber().toLowerCase().trim() + "%"),
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("referenceNumber")), "%" + searchCriteriaForSummary.getMasterCONS().toLowerCase().trim() + "%"),
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteriaForSummary.getRouteNumber().toLowerCase().trim() + "%")
//            );
//
//            if (searchCriteriaForSummary.getDestinations() != null && !searchCriteriaForSummary.getDestinations().isEmpty()) {
//                predicate = criteriaBuilder.and(predicate, root.get("destinationLocation").in(searchCriteriaForSummary.getDestinations()));
//            }
//
//            return predicate;
//        };

}
