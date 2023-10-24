package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.service.DomesticShipmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DomesticShipmentController {

    @Autowired
    DomesticShipmentService domesticShipmentService;



    @PostMapping("/add-domestic-shipment")
    public ResponseEntity<DomesticShipmentDto> addShipment(@RequestBody DomesticShipmentDto domesticShipmentDto){
                return ResponseEntity.ok(domesticShipmentService.addShipment(domesticShipmentDto));
    }

    @GetMapping("/all-domestic-shipments")
    public ResponseEntity<Page<DomesticShipmentDto>> getAll(@RequestParam(value = "value",required = false) String value,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForDomesticShipment
                searchCriteriaForDomesticShipment = new ObjectMapper().readValue(value, SearchCriteriaForDomesticShipment.class);
        return ResponseEntity.ok(domesticShipmentService.getAll(searchCriteriaForDomesticShipment,page,size));
    }


    @GetMapping("/domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(domesticShipmentService.getById(id));
    }

    @PutMapping("/edit-domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> updateDomesticShipment(@PathVariable Long id,
                                                                      @RequestBody DomesticShipmentDto domesticShipmentDto){
        return ResponseEntity.ok(domesticShipmentService.updateDomesticShipment(id,domesticShipmentDto));
    }

    @GetMapping("/domestic-shipment/outbound")
    public ResponseEntity<Page<DomesticShipmentDto>> getOutboundShipment(@RequestParam(value = "value",required = false) String value,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);
        return ResponseEntity.ok(domesticShipmentService.getOutboundShipment(searchCriteriaForSummary,page,size));
    }
    @GetMapping("/domestic-shipment/inbound")
    public ResponseEntity<Page<DomesticShipmentDto>> getInboundShipment(@RequestParam(value = "value",required = false) String value,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);
        return ResponseEntity.ok(domesticShipmentService.getInboundShipment(searchCriteriaForSummary,page,size));
    }

    @PatchMapping("/update-domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> update(@PathVariable Long id,@RequestBody DomesticShipmentDto domesticShipmentDto){
        return ResponseEntity.ok(domesticShipmentService.updateDomesticShipment(id,domesticShipmentDto));
    }

}
