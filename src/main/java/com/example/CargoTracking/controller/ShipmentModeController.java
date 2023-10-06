package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.ShipmentModeDto;
import com.example.CargoTracking.service.ShipmentModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShipmentModeController {
    @Autowired
    ShipmentModeService shipmentModeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/shipment-mode")
    public ResponseEntity<ShipmentModeDto> addMode(@RequestBody ShipmentModeDto shipmentModeDto){
        return ResponseEntity.ok(shipmentModeService.addMode(shipmentModeDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/shipment-mode")
    public ResponseEntity<List<ShipmentModeDto>> getAll(){
        return ResponseEntity.ok(shipmentModeService.getActiveShipments());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/shipment-mode/{id}")
    public ResponseEntity<ShipmentModeDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(shipmentModeService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/shipment-mode/{id}")
    public ResponseEntity<ShipmentModeDto> deleteModeById(@PathVariable Long id){
        return ResponseEntity.ok(shipmentModeService.deleteModeById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/shipment-mode/{id}")
    public ResponseEntity<ShipmentModeDto> updateShipmentModeById(@PathVariable Long id, @RequestBody ShipmentModeDto shipmentModeDto){
        return  ResponseEntity.ok(shipmentModeService.updateById(id, shipmentModeDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/shipment-mode/active/{id}")
    public ResponseEntity<ShipmentModeDto> makeShipmentModeActive(@PathVariable Long id){
        return ResponseEntity.ok(shipmentModeService.makeShipmentModeActive(id));
    }

}
