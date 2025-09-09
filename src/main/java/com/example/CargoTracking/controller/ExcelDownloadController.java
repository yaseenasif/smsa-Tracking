package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.dto.ShipmentType;
import com.example.CargoTracking.service.ExcelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class ExcelDownloadController {

    private final ExcelService excelService;

    public ExcelDownloadController(ExcelService excelService) {
        this.excelService = excelService;
    }


    @PreAuthorize("hasAuthority('international-air-report-performance')")
    @PostMapping("/international-shipment-air-report")
    public ResponseEntity<Resource> internationalAirReportPerformanceExcelDownload(
        @RequestBody SearchCriteriaForInternationalSummary criteria) throws IOException {

        if(criteria.getToDate() == null || criteria.getToDate().isEmpty()
                && criteria.getFromDate() == null || criteria.getFromDate().isEmpty()) {
            criteria.setFromDate(LocalDate.now().minusMonths(3).toString());
            criteria.setToDate(LocalDate.now().toString());
        }

        if(criteria.getType() !=null && criteria.getType().isEmpty()) {
            criteria.setType(ShipmentType.BY_AIR.getType());
        }

        Resource file = excelService.internationalAirReportExcelDownload(criteria);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
