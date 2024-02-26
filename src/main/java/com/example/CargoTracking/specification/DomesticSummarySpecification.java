package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.model.DomesticShipment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DomesticSummarySpecification {
  public static Specification<DomesticShipment> getSearchSpecification(SearchCriteriaForSummary searchCriteriaForSummary) {
    if (searchCriteriaForSummary == null) {
      return null;
    }

    return (root, query, criteriaBuilder) -> {
      // Initialize local date variables
      LocalDate localFromDate = null;
      LocalDate localToDate = null;

      // Parse date strings if they are not empty
      if (searchCriteriaForSummary.getFromDate() != null && searchCriteriaForSummary.getToDate() != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        localFromDate = LocalDate.parse(searchCriteriaForSummary.getFromDate(), formatter);
        localToDate = LocalDate.parse(searchCriteriaForSummary.getToDate(), formatter);
      }

      // Construct predicates based on search criteria
      return criteriaBuilder.and(
              // Check if createdAt is within the specified date range
              localFromDate != null && localToDate != null ?
                      criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate) : criteriaBuilder.conjunction(),
              // Check status (if provided)
              searchCriteriaForSummary.getStatus() != null ?
                      criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForSummary.getStatus() + "%") :
                      criteriaBuilder.conjunction(),
              // Check origin location (if provided)
              searchCriteriaForSummary.getOrigin() != null ?
                      criteriaBuilder.equal(criteriaBuilder.lower(root.get("originLocation")), searchCriteriaForSummary.getOrigin()) :
                      criteriaBuilder.conjunction(),
              // Check destinations (if provided)
              searchCriteriaForSummary.getDestinations() != null && !searchCriteriaForSummary.getDestinations().isEmpty() ?
                      root.get("destinationLocation").in(searchCriteriaForSummary.getDestinations()) :
                      criteriaBuilder.conjunction(),
              // Check route (if provided)
              searchCriteriaForSummary.getRoute() != null ?
                      criteriaBuilder.equal(criteriaBuilder.lower(root.get("route")), searchCriteriaForSummary.getRoute()) :
                      criteriaBuilder.conjunction()
      );
    };
  }
}
