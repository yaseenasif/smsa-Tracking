package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.InternationalAirReportStatusDto;
import com.example.CargoTracking.dto.InternationalRoadReportStatusDto;
import com.example.CargoTracking.dto.ShipmentType;
import com.example.CargoTracking.service.ExcelService;
import com.example.CargoTracking.service.ReportAndStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @Autowired
    ReportAndStatusService reportAndStatusService;

    @PreAuthorize("hasAuthority('international-air-report-performance')")
    @GetMapping("/int-air-rep-per")
    public ResponseEntity<Resource> internationalAirReportPerformanceExcelDownload() throws IOException {
//        SearchCriteriaForInternationalSummary
//                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        Resource file = excelService.internationalAirReportPerformanceExcelDownload();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('international-road-report-performance')")
    @GetMapping("/int-road-rep-per")
    public ResponseEntity<Resource> internationalRoadReportPerformanceExcelDownload() throws IOException {
//        SearchCriteriaForInternationalSummary
//                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        Resource file = excelService.internationalRoadReportPerformanceExcelDownload();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('international-air-report-status')")
    @PostMapping("/int-air-rep-status")
    public ResponseEntity<Resource> internationalAirReportStatusExcelDownload(
            @RequestBody SearchCriteriaForInternationalSummary criteria
    ) throws IOException {

        List<InternationalAirReportStatusDto> list = reportAndStatusService
                                                        .findInternationalAirReportStatus(criteria);

        Resource file = excelService.internationalAirReportStatusExcelDownload(list);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('international-road-report-status')")
    @GetMapping("/int-road-rep-status")
    public ResponseEntity<Resource> internationalRoadReportStatusExcelDownload() throws IOException {
//        SearchCriteriaForInternationalSummary
//                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        Resource file = excelService.internationalRoadReportStatusExcelDownload();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('domestic-report-performance')")
    @PostMapping("/dom-per")
    public ResponseEntity<Resource> domesticPerformancePerformanceExcelDownload(
           @RequestBody SearchCriteriaForSummary criteria
    ) throws IOException {
        if(criteria.getToDate() == null || criteria.getToDate().isEmpty()
                && criteria.getFromDate() == null || criteria.getFromDate().isEmpty()) {
            criteria.setFromDate(LocalDate.now().minusMonths(3).toString());
            criteria.setToDate(LocalDate.now().toString());
        }

        Resource file = excelService.domesticPerformanceExcelDownload(criteria);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
