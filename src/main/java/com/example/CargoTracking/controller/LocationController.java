package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.LocationDto;
import com.example.CargoTracking.dto.SearchCriteriaForLocation;
import com.example.CargoTracking.dto.SearchCriteriaForUser;
import com.example.CargoTracking.dto.UserResponseDto;
import com.example.CargoTracking.model.Facility;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    LocationService locationService;

    @PreAuthorize("hasAuthority('add-location')")
    @PostMapping("/location")
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto){
        return ResponseEntity.ok(locationService.addLocation(locationDto));
    }

    @PreAuthorize("hasAuthority('getAll-location')")
    @GetMapping("/location")
    public ResponseEntity<List<LocationDto>> getAll(){
        return ResponseEntity.ok(locationService.getActiveLocations());
    }

    @GetMapping("/filter-location")
    public ResponseEntity<Page<LocationDto>> getFilterLocations(@RequestParam(defaultValue = "value",required = false) String value,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {

        SearchCriteriaForLocation searchCriteriaForLocation =  new ObjectMapper().readValue(value, SearchCriteriaForLocation.class);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(locationService.getFilterLocations(searchCriteriaForLocation,pageable));
    }
    @PreAuthorize("hasAuthority('getAll-domesticActive')")
    @GetMapping("/location-domestic")
    public ResponseEntity<List<LocationDto>> getAllForDomestic(){
        return ResponseEntity.ok(locationService.getActiveLocationsForDomestic());
    }

    @PreAuthorize("hasAuthority('getAll-internationalActive')")
    @GetMapping("/location-international")
    public ResponseEntity<List<LocationDto>> getAllForInternational(){
        return ResponseEntity.ok(locationService.getActiveLocationsForInternational());
    }

    @PreAuthorize("hasAuthority('getById-location')")
    @GetMapping("/location/{id}")
    public ResponseEntity<LocationDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(locationService.getById(id));
    }

    @PreAuthorize("hasAuthority('delete-location')")
    @DeleteMapping("/location/{id}")
    public ResponseEntity<ApiResponse> deleteLocationById(@PathVariable Long id){
        return ResponseEntity.ok(locationService.deleteLocationById(id));
    }

    @PreAuthorize("hasAuthority('update-location')")
    @PatchMapping("/location/{id}")
    public ResponseEntity<LocationDto> updateLocationById(@PathVariable Long id, @RequestBody LocationDto locationDto){
        return  ResponseEntity.ok(locationService.updateById(id, locationDto));
    }

    @PreAuthorize("hasAuthority('activate-location')")
    @PatchMapping("/location/active/{id}")
    public ResponseEntity<LocationDto> makeLocationActive(@PathVariable Long id){
        return ResponseEntity.ok(locationService.makeLocationActive(id));
    }

//    @GetMapping("get-all-by-facility/{facilityId}/{type}")
//    public ResponseEntity<List<LocationDto>> getLocationByFacility(@PathVariable Long facilityId, @PathVariable String type) {
//        return ResponseEntity.ok(locationService.getLocationByFacilityName(facilityId,type));
//    }

}
