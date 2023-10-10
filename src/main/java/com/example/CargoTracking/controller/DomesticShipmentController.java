package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.service.DomesticShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DomesticShipmentController {

    @Autowired
    DomesticShipmentService domesticShipmentService;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @GetMapping("/shipment/outbound")
    public ResponseEntity<List<DomesticShipmentDto>> getOutboundShipment(){
        return ResponseEntity.ok(domesticShipmentService.getOutboundShipment());
    }
    @GetMapping("/shipment/inbound")
    public ResponseEntity<List<DomesticShipmentDto>> getInboundShipment(){
        return ResponseEntity.ok(domesticShipmentService.getInboundShipment());
    }

}
