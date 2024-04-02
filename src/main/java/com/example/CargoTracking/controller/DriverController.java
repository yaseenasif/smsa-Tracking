package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteria;
import com.example.CargoTracking.dto.DriverDto;
import com.example.CargoTracking.service.DriverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DriverController {

    @Autowired
    DriverService driverService;

    @PreAuthorize("hasAuthority('add_driver')")
    @PostMapping("/driver")
    public ResponseEntity<DriverDto> addDriver(@RequestBody DriverDto driverDto){
        return ResponseEntity.ok(driverService.addDriver(driverDto));
    }

    @PreAuthorize("hasAuthority('getAll_driver')")
    @GetMapping("/driver")
    public ResponseEntity<Page<DriverDto>> getAll(@RequestParam(value = "value",required = false) String value,
                                                    @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size
                                                  ) throws JsonProcessingException {
        return ResponseEntity.ok(driverService.getActiveDrivers(value,page,size));
    }

    @PreAuthorize("hasAuthority('getById_driver')")
    @GetMapping("/driver/{id}")
    public ResponseEntity<DriverDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(driverService.getById(id));
    }

    @PreAuthorize("hasAuthority('delete_driver')")
    @DeleteMapping("/driver/{id}")
    public ResponseEntity<DriverDto> deleteDriverById(@PathVariable Long id){
        return ResponseEntity.ok(driverService.deleteDriverById(id));
    }

    @PreAuthorize("hasAuthority('update_driver')")
    @PatchMapping("/driver/{id}")
    public ResponseEntity<DriverDto> updateDriverById(@PathVariable Long id, @RequestBody DriverDto driverDto){
        return  ResponseEntity.ok(driverService.updateById(id, driverDto));
    }

    @PreAuthorize("hasAuthority('activate_driver')")
    @PatchMapping("/driver/active/{id}")
    public ResponseEntity<DriverDto> makeDriverActive(@PathVariable Long id){
        return ResponseEntity.ok(driverService.makeDriverActive(id));
    }


}
