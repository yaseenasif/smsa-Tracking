package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.FacilityDto;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @PostMapping("add-facility")
    public ResponseEntity<FacilityDto> addFacility(@RequestBody FacilityDto facilityDto){
        return ResponseEntity.ok(facilityService.addFacility(facilityDto));
    }

    @GetMapping("get-all")
    public ResponseEntity<List<FacilityDto>> getAllFacilities(){
        return ResponseEntity.ok(facilityService.getAllFacilities());
    }

    @GetMapping("get-facility/{id}")
    public ResponseEntity<FacilityDto> getFacilityById(@PathVariable Long id){
        return ResponseEntity.ok(facilityService.getFacilityById(id));
    }

    @PutMapping("update-Facility/{id}")
    public ResponseEntity<FacilityDto> updateFacility(@PathVariable Long id,@RequestBody FacilityDto facilityDto){
        return ResponseEntity.ok(facilityService.updateFacility(id,facilityDto));
    }

    @DeleteMapping("delete-Facility/{id}")
    public ResponseEntity<ApiResponse> deleteFacility(@PathVariable Long id){
        return ResponseEntity.ok(facilityService.deleteFacility(id));
    }

    @GetMapping("get-by-country/{country}")
    public ResponseEntity<List<FacilityDto>> getFacilitiesByCountry(@PathVariable String country){
        return ResponseEntity.ok(facilityService.getFacilitiesByCountryName(country));
    }
}
