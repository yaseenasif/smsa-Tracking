package com.example.CargoTracking.controller;

import com.example.CargoTracking.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/int-air-rep-per")
    public ResponseEntity<Resource> internationalAirReportPerformanceExcelDownload() throws IOException {

        Resource file = excelService.internationalAirReportPerformanceExcelDownload();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/int-air-rep-per")
    public ResponseEntity<Resource> internationalRoadReportPerformanceExcelDownload() throws IOException {

        Resource file = excelService.internationalRoadReportPerformanceExcelDownload();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/int-air-rep-per")
    public ResponseEntity<Resource> internationalAirReportStatusExcelDownload() throws IOException {

        Resource file = excelService.internationalAirReportStatusExcelDownload();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/int-air-rep-per")
    public ResponseEntity<Resource> internationalRoadReportStatusExcelDownload() throws IOException {

        Resource file = excelService.internationalRoadReportStatusExcelDownload();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/dom-per")
    public ResponseEntity<Resource> domesticPerformancePerformanceExcelDownload() throws IOException {

        Resource file = excelService.domesticPerformanceExcelDownload();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
