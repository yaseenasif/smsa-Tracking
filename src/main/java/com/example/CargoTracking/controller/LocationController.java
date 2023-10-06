package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.LocationDto;
import com.example.CargoTracking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    LocationService locationService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/location")
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto){
        return ResponseEntity.ok(locationService.addLocation(locationDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/location")
    public ResponseEntity<List<LocationDto>> getAll(){
        return ResponseEntity.ok(locationService.getActiveLocations());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/location/{id}")
    public ResponseEntity<LocationDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(locationService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/location/{id}")
    public ResponseEntity<LocationDto> deleteLocationById(@PathVariable Long id){
        return ResponseEntity.ok(locationService.deleteLocationById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/location/{id}")
    public ResponseEntity<LocationDto> updateLocationById(@PathVariable Long id, @RequestBody LocationDto locationDto){
        return  ResponseEntity.ok(locationService.updateById(id, locationDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/location/active/{id}")
    public ResponseEntity<LocationDto> makeLocationActive(@PathVariable Long id){
        return ResponseEntity.ok(locationService.makeLocationActive(id));
    }

}
