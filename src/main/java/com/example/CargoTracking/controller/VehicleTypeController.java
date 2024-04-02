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

    @PreAuthorize("hasAuthority('add-vehicleType')")
    @PostMapping("/vehicle-type")
    public ResponseEntity<VehicleTypeDto> addType(@RequestBody VehicleTypeDto vehicleTypeDto){
        return ResponseEntity.ok(vehicleTypeService.addType(vehicleTypeDto));
    }

    @PreAuthorize("hasAuthority('getAll-vehicleType')")
    @GetMapping("/vehicle-type")
    public ResponseEntity<List<VehicleTypeDto>> getAll(){
        return ResponseEntity.ok(vehicleTypeService.getActiveVehicles());
    }

    @PreAuthorize("hasAuthority('getById-vehicleType')")
    @GetMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleTypeDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.getById(id));
    }

    @PreAuthorize("hasAuthority('delete-vehicleType')")
    @DeleteMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleTypeDto> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.deleteById(id));
    }

    @PreAuthorize("hasAuthority('update-vehicleType')")
    @PatchMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleTypeDto> updateById(@PathVariable Long id, @RequestBody VehicleTypeDto vehicleTypeDto){
        return  ResponseEntity.ok(vehicleTypeService.updateById(id, vehicleTypeDto));
    }

    @PreAuthorize("hasAuthority('activate-vehicleType')")
    @PatchMapping("/vehicle-type/active/{id}")
    public ResponseEntity<VehicleTypeDto> makeVehicleTypeActive(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.makeVehicleTypeActive(id));
    }


}
