package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.ShipmentDto;
import com.example.CargoTracking.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShipmentController {

    @Autowired
    ShipmentService shipmentService;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/shipment")
    public ResponseEntity<ShipmentDto> addShipment(@RequestBody ShipmentDto shipmentDto){
                return ResponseEntity.ok(shipmentService.addShipment(shipmentDto));
    }

    @GetMapping("/shipment")
    public ResponseEntity<List<ShipmentDto>> getAll(){
        return ResponseEntity.ok(shipmentService.getAll());
    }

    @GetMapping("/shipment/{id}")
    public ResponseEntity<ShipmentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(shipmentService.getById(id));
    }

    @GetMapping("/shipment/outbound")
    public ResponseEntity<List<ShipmentDto>> getOutboundShipment(){
        return ResponseEntity.ok(shipmentService.getOutboundShipment());
    }
    @GetMapping("/shipment/inbound")
    public ResponseEntity<List<ShipmentDto>> getInboundShipment(){
        return ResponseEntity.ok(shipmentService.getInboundShipment());
    }

}
