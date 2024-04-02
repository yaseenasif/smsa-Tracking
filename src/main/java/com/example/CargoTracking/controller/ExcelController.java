package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.service.ExcelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @PreAuthorize("hasAuthority('international-air-report-performance')")
    @GetMapping("/int-air-rep-per")
    public ResponseEntity<Resource> internationalAirReportPerformanceExcelDownload(@RequestParam(value = "value",required = false) String value) throws IOException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        Resource file = excelService.internationalAirReportPerformanceExcelDownload(searchCriteriaForInternationalSummary);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('international-road-report-performance')")
    @GetMapping("/int-road-rep-per")
    public ResponseEntity<Resource> internationalRoadReportPerformanceExcelDownload(@RequestParam(value = "value",required = false) String value) throws IOException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        Resource file = excelService.internationalRoadReportPerformanceExcelDownload(searchCriteriaForInternationalSummary);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('international-air-report-status')")
    @GetMapping("/int-air-rep-status")
    public ResponseEntity<Resource> internationalAirReportStatusExcelDownload(@RequestParam(value = "value",required = false) String value) throws IOException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        Resource file = excelService.internationalAirReportStatusExcelDownload(searchCriteriaForInternationalSummary);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('international-road-report-status')")
    @GetMapping("/int-road-rep-status")
    public ResponseEntity<Resource> internationalRoadReportStatusExcelDownload(@RequestParam(value = "value",required = false) String value) throws IOException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        Resource file = excelService.internationalRoadReportStatusExcelDownload(searchCriteriaForInternationalSummary);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('domestic-report-performance')")
    @GetMapping("/dom-per")
    public ResponseEntity<Resource> domesticPerformancePerformanceExcelDownload(@RequestParam(value = "value",required = false) String value) throws IOException {
        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);

        Resource file = excelService.domesticPerformanceExcelDownload(searchCriteriaForSummary);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
