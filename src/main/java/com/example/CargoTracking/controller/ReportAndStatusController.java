package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.*;
import com.example.CargoTracking.service.ReportAndStatusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportAndStatusController {
    @Autowired
    ReportAndStatusService reportAndStatusService;

    @PreAuthorize("hasAuthority('international-air-report-status')")
    @GetMapping("/int-air-report-status")
    public ResponseEntity<List<InternationalAirReportStatusDto>> findInternationalAirReportStatus(@RequestParam(value = "value",required = false) String value) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(reportAndStatusService.findInternationalAirReportStatus(searchCriteriaForInternationalSummary));
    }

    @PreAuthorize("hasAuthority('international-road-report-status')")
    @GetMapping("/int-road-report-status")
    public ResponseEntity<List<InternationalRoadReportStatusDto>> findInternationalRoadReportStatus(@RequestParam(value = "value",required = false) String value) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(reportAndStatusService.findInternationalRoadReportStatus(searchCriteriaForInternationalSummary));
    }

    @PreAuthorize("hasAuthority('international-air-report-performance')")
    @GetMapping("/int-air-report-performance")
    public ResponseEntity<List<InternationalAirReportPerformance>> findInternationalAirReportPerformance(@RequestParam(value = "value",required = false) String value) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(reportAndStatusService.findInternationalAirReportPerformance(searchCriteriaForInternationalSummary));
    }

    @PreAuthorize("hasAuthority('international-road-report-performance')")
    @GetMapping("/int-road-report-performance")
    public ResponseEntity<List<InternationalRoadReportPerformance>> findInternationalRoadReportPerformance(@RequestParam(value = "value",required = false) String value) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(reportAndStatusService.findInternationalRoadReportPerformance(searchCriteriaForInternationalSummary));
    }

//    @PreAuthorize("hasAuthority('domestic-report-performance')")
//    @GetMapping("/domestic-performance")
//    public ResponseEntity<List<DomesticPerformance>> findDomesticPerformance(@RequestParam(value = "value",required = false) String value
//                                                                           ) throws JsonProcessingException {
//        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);
//        return ResponseEntity.ok(reportAndStatusService.
//                findDomesticPerformance(searchCriteriaForSummary));
//    }

    @PreAuthorize("hasAuthority('domestic-report-performance')")
    @GetMapping("/domestic-performance")
    public ResponseEntity<Page<DomesticPerformance>> findDomesticPerformance(
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) throws JsonProcessingException {

        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);
        Page<DomesticPerformance> domesticPerformances = reportAndStatusService.findDomesticPerformance(searchCriteriaForSummary, page, size);
        return ResponseEntity.ok(domesticPerformances);
    }
}
