package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.DomesticShipmentHistoryDto;
import com.example.CargoTracking.dto.InternationalShipmentHistoryDto;
import com.example.CargoTracking.service.InternationalShipmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InternationalShipmentHistoryController {
    @Autowired
    InternationalShipmentHistoryService internationalShipmentHistoryService;


    @GetMapping("/all-international-shipments-history/{id}")
    public ResponseEntity<List<InternationalShipmentHistoryDto>> getById(@PathVariable Long id){
        return ResponseEntity.ok(internationalShipmentHistoryService.getDomesticShipmentHistoryByDomesticShipmentId(id));
    }
}
