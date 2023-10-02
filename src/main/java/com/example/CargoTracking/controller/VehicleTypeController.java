package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.VehicleTypeDto;
import com.example.CargoTracking.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleTypeController {

    @Autowired
    VehicleTypeService vehicleTypeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/vehicle-type")
    public ResponseEntity<VehicleTypeDto> addType(@RequestBody VehicleTypeDto vehicleTypeDto){
        return ResponseEntity.ok(vehicleTypeService.addType(vehicleTypeDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/vehicle-type")
    public ResponseEntity<List<VehicleTypeDto>> getAll(){
        return ResponseEntity.ok(vehicleTypeService.getActiveVehicles());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleTypeDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleTypeDto> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.deleteById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleTypeDto> updateById(@PathVariable Long id, @RequestBody VehicleTypeDto vehicleTypeDto){
        return  ResponseEntity.ok(vehicleTypeService.updateById(id, vehicleTypeDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/vehicle-type/active/{id}")
    public ResponseEntity<VehicleTypeDto> makeVehicleTypeActive(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.makeVehicleTypeActive(id));
    }


}
