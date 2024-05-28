package com.example.CargoTracking.specification;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummaryOutbound;
import com.example.CargoTracking.model.InternationalShipment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InternationalSummarySpecificationForOutbound {
  public static Specification<InternationalShipment> getSearchSpecification(SearchCriteriaForInternationalSummaryOutbound searchCriteriaForInternationalSummary){

    LocalDate localFromDate;
    LocalDate localToDate;
    if(!searchCriteriaForInternationalSummary.getFromDate().isEmpty() || !searchCriteriaForInternationalSummary.getToDate().isEmpty() ){
      String fromDate = searchCriteriaForInternationalSummary.getFromDate();
      String toDate = searchCriteriaForInternationalSummary.getToDate();
      DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      localFromDate = LocalDate.parse(fromDate,targetFormatter);
      localToDate = LocalDate.parse(toDate,targetFormatter);
    }else{
      return (root, query, criteriaBuilder) ->

              criteriaBuilder.and(
                      criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForInternationalSummary.getStatus() + "%"),
                      criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteriaForInternationalSummary.getDestinations() + "%"),
                      root.get("originLocation").in(searchCriteriaForInternationalSummary.getOrigin()),
                      criteriaBuilder.equal(root.get("activeStatus"),true),
                      criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteriaForInternationalSummary.getRouteNumber() + "%"),
                      criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + searchCriteriaForInternationalSummary.getType() + "%")

              );
    }


    return (root, query, criteriaBuilder) ->

            criteriaBuilder.and(
                    criteriaBuilder.between(root.get("createdAt"), localFromDate, localToDate),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + searchCriteriaForInternationalSummary.getStatus() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("destinationLocation")), "%" + searchCriteriaForInternationalSummary.getDestinations() + "%"),
                    root.get("originLocation").in(searchCriteriaForInternationalSummary.getOrigin()),
                    criteriaBuilder.equal(root.get("activeStatus"),true),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("routeNumber")), "%" + searchCriteriaForInternationalSummary.getRouteNumber() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + searchCriteriaForInternationalSummary.getType() + "%")

            );
  }

}
