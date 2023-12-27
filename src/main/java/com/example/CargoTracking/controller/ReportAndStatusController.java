package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.*;
import com.example.CargoTracking.service.ReportAndStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportAndStatusController {
    @Autowired
    ReportAndStatusService reportAndStatusService;

    @GetMapping("/int-air-report-status")
    public ResponseEntity<List<InternationalAirReportStatusDto>> findInternationalAirReportStatus(){
        return ResponseEntity.ok(reportAndStatusService.findInternationalAirReportStatus());
    }

    @GetMapping("/int-road-report-status")
    public ResponseEntity<List<InternationalRoadReportStatusDto>> findInternationalRoadReportStatus(){
        return ResponseEntity.ok(reportAndStatusService.findInternationalRoadReportStatus());
    }

    @GetMapping("/int-air-report-performance")
    public ResponseEntity<List<InternationalAirReportPerformance>> findInternationalAirReportPerformance(){
        return ResponseEntity.ok(reportAndStatusService.findInternationalAirReportPerformance());
    }

    @GetMapping("/int-road-report-performance")
    public ResponseEntity<List<InternationalRoadReportPerformance>> findInternationalRoadReportPerformance(){
        return ResponseEntity.ok(reportAndStatusService.findInternationalRoadReportPerformance());
    }

    @GetMapping("/domestic-performance")
    public ResponseEntity<List<DomesticPerformance>> findDomesticPerformance(){
        return ResponseEntity.ok(reportAndStatusService.findDomesticPerformance());
    }
}
