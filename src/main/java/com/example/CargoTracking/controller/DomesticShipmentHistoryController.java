package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.dto.DomesticShipmentHistoryDto;
import com.example.CargoTracking.service.DomesticShipmentHistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DomesticShipmentHistoryController {

    @Autowired
    DomesticShipmentHistoryService domesticShipmentHistoryService;
    @GetMapping("/all-domestic-shipments-history/{id}")
    public ResponseEntity<List<DomesticShipmentHistoryDto>> getById(@PathVariable Long id){
        return ResponseEntity.ok(domesticShipmentHistoryService.getDomesticShipmentHistoryByDomesticShipmentId(id));
    }
}
