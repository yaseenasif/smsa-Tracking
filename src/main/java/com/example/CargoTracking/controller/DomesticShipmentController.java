package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.service.DomesticShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<DomesticShipmentDto>> getAll(){
        return ResponseEntity.ok(domesticShipmentService.getAll());
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
    public ResponseEntity<List<DomesticShipmentDto>> getOutboundShipment(){
        return ResponseEntity.ok(domesticShipmentService.getOutboundShipment());
    }
    @GetMapping("/domestic-shipment/inbound")
    public ResponseEntity<List<DomesticShipmentDto>> getInboundShipment(){
        return ResponseEntity.ok(domesticShipmentService.getInboundShipment());
    }

    @PatchMapping("/update-domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> update(@PathVariable Long id,@RequestBody DomesticShipmentDto domesticShipmentDto){
        return ResponseEntity.ok(domesticShipmentService.updateDomesticShipment(id,domesticShipmentDto));
    }

}
