package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.InternationalShipmentDto;
import com.example.CargoTracking.service.InternationalShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/international-shipment/{id}")
    public ResponseEntity<InternationalShipmentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(internationalShipmentService.getById(id));
    }

    @GetMapping("/international-outbound-summery")
    public ResponseEntity<List<InternationalShipmentDto>> getInternationalOutBoundSummery(){
        return ResponseEntity.ok(internationalShipmentService.getInternationalOutBoundSummery());
    }

    @GetMapping("/international-inbound-summery")
    public ResponseEntity<List<InternationalShipmentDto>> getInternationalInBoundSummery(){
        return ResponseEntity.ok(internationalShipmentService.getInternationalInBoundSummery());
    }
}
