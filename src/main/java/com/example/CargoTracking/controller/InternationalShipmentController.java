package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.dto.InternationalShipmentDto;
import com.example.CargoTracking.service.InternationalShipmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InternationalShipmentController {
    @Autowired
    InternationalShipmentService internationalShipmentService;

    @PostMapping("/add-international-shipment")
    public ResponseEntity<InternationalShipmentDto> saveInternationalShipment(@RequestBody InternationalShipmentDto internationalShipmentDto){
        return ResponseEntity.ok(internationalShipmentService.addShipment(internationalShipmentDto));
    }

    @GetMapping("/all-international-shipments")
    public ResponseEntity<List<InternationalShipmentDto>> getAll(){
        return ResponseEntity.ok(internationalShipmentService.getAll());
    }

    @GetMapping("/international-shipments-by-user-air")
    public ResponseEntity<List<InternationalShipmentDto>> getAllInternationalShipmentByUserForAir(){
        return ResponseEntity.ok(internationalShipmentService.getAllByUserAndForAir());
    }

    @GetMapping("/international-shipments-by-user-road")
    public ResponseEntity<List<InternationalShipmentDto>> getAllInternationalShipmentByUserForRoad(){
        return ResponseEntity.ok(internationalShipmentService.getAllByUserAndForRoad());
    }

    @GetMapping("/international-shipment/{id}")
    public ResponseEntity<InternationalShipmentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(internationalShipmentService.getById(id));
    }

    @GetMapping("/international-outbound-summery-air")
    public ResponseEntity<Page<InternationalShipmentDto>> getInternationalOutBoundSummeryForAir(@RequestParam(value = "value",required = false) String value,
                                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);

        return ResponseEntity.ok(internationalShipmentService.getInternationalOutBoundSummeryForAir(searchCriteriaForInternationalSummary,page,size));
    }

    @GetMapping("/international-outbound-summery-road")
    public ResponseEntity<Page<InternationalShipmentDto>> getInternationalOutBoundSummeryForRoad(@RequestParam(value = "value",required = false) String value,
                                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                                 @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(internationalShipmentService.getInternationalOutBoundSummeryForRoad(searchCriteriaForInternationalSummary,page,size));
    }

    @GetMapping("/international-inbound-summery-air")
    public ResponseEntity<Page<InternationalShipmentDto>> getInternationalInBoundSummeryForAir(@RequestParam(value = "value",required = false) String value,
                                                                                               @RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(internationalShipmentService.getInternationalInBoundSummeryForAir(searchCriteriaForInternationalSummary,page,size));
    }

    @GetMapping("/international-inbound-summery-road")
    public ResponseEntity<Page<InternationalShipmentDto>> getInternationalInBoundSummeryForRoad(@RequestParam(value = "value",required = false) String value,
                                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(internationalShipmentService.getInternationalInBoundSummeryForRoad(searchCriteriaForInternationalSummary,page,size));
    }

    @PatchMapping("/update-international-shipment/{id}")
    public ResponseEntity<InternationalShipmentDto> update(@PathVariable Long id, @RequestBody InternationalShipmentDto internationalShipmentDto){
        return ResponseEntity.ok(internationalShipmentService.updateInternationalShipment(id,internationalShipmentDto));
    }
}
