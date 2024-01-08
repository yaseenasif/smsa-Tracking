package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.CountryDto;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    CountryService countryService;

    @PostMapping("add-country")
    public ResponseEntity<CountryDto> addCountry (@RequestBody CountryDto countryDto){
        return ResponseEntity.ok(countryService.addCountry(countryDto));
    }

    @GetMapping("all-country")
    public ResponseEntity<List<CountryDto>> getAllCountry (){
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("country/{id}")
    public ResponseEntity<CountryDto> getCountryById (@PathVariable Long id){
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @PutMapping("update-country/{id}")
    public ResponseEntity<CountryDto> updateCountry (@PathVariable Long id,@RequestBody CountryDto countryDto){
        return ResponseEntity.ok(countryService.updateCountry(id,countryDto));
    }

    @DeleteMapping("delete-country/{id}")
    public ResponseEntity<ApiResponse> deleteCountry(@PathVariable Long id){
        return ResponseEntity.ok(countryService.deleteCountryById(id));
    }
}
