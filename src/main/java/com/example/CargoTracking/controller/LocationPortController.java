package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.LocationPortDto;
import com.example.CargoTracking.service.LocationPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationPortController {

    @Autowired
    LocationPortService locationPortService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/location-port")
    public ResponseEntity<LocationPortDto> addPort(@RequestBody LocationPortDto locationPortDto){
        return ResponseEntity.ok(locationPortService.addPort(locationPortDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/location-port")
    public ResponseEntity<List<LocationPortDto>> getAll(){
        return ResponseEntity.ok(locationPortService.getActivePorts());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/location-port/{id}")
    public ResponseEntity<LocationPortDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(locationPortService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/location-port/{id}")
    public ResponseEntity<LocationPortDto> deletePortById(@PathVariable Long id){
        return ResponseEntity.ok(locationPortService.deletePortById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/location-port/{id}")
    public ResponseEntity<LocationPortDto> updatePortById(@PathVariable Long id, @RequestBody LocationPortDto locationPortDto){
        return  ResponseEntity.ok(locationPortService.updateById(id, locationPortDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/location-port/active/{id}")
    public ResponseEntity<LocationPortDto> makePortActive(@PathVariable Long id){
        return ResponseEntity.ok(locationPortService.makePortActive(id));
    }

}
