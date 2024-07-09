package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.VehicleDto;
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

    @PreAuthorize("hasAuthority('add-vehicle')")
    @PostMapping("/vehicle-type")
    public ResponseEntity<VehicleDto> addType(@RequestBody VehicleDto vehicleDto){
        return ResponseEntity.ok(vehicleTypeService.addType(vehicleDto));
    }

    @PreAuthorize("hasAuthority('getAll-vehicle')")
    @GetMapping("/vehicle-type")
    public ResponseEntity<List<VehicleDto>> getAll(){
        return ResponseEntity.ok(vehicleTypeService.getActiveVehicles());
    }

    @PreAuthorize("hasAuthority('getById-vehicle')")
    @GetMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.getById(id));
    }

    @PreAuthorize("hasAuthority('delete-vehicle')")
    @DeleteMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleDto> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.deleteById(id));
    }

    @PreAuthorize("hasAuthority('update-vehicle')")
    @PatchMapping("/vehicle-type/{id}")
    public ResponseEntity<VehicleDto> updateById(@PathVariable Long id, @RequestBody VehicleDto vehicleDto){
        return  ResponseEntity.ok(vehicleTypeService.updateById(id, vehicleDto));
    }

    @PreAuthorize("hasAuthority('activate-vehicle')")
    @PatchMapping("/vehicle-type/active/{id}")
    public ResponseEntity<VehicleDto> makeVehicleTypeActive(@PathVariable Long id){
        return ResponseEntity.ok(vehicleTypeService.makeVehicleTypeActive(id));
    }


}
