package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteria;
import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.DomesticRouteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DomesticRouteController {
    @Autowired
    DomesticRouteService domesticRouteService;

    @GetMapping("/getRoute/{origin}/{destination}")
    public List<DomesticRouteDto> getDomesticRoute(@PathVariable String origin,
                                                              @PathVariable String destination ){
        return domesticRouteService.findDomesticRoute(origin,destination);
    }

    @GetMapping("domesticRoute/{routeNumber}")
    public DomesticRoute getRouteByRouteNumber(@PathVariable String routeNumber){
        return domesticRouteService.findRouteByRouteNumber(routeNumber);
    }

    @PostMapping("/add-domesticRoute")
    public DomesticRouteDto addDomesticRoute(@RequestBody DomesticRouteDto domesticRouteDto){
        return domesticRouteService.saveDomesticRoute(domesticRouteDto);
    }

    @GetMapping("/get-domestic-route/{id}")
    public ResponseEntity<DomesticRouteDto> getDomesticRouteById(@PathVariable Long id){
        return ResponseEntity.ok(domesticRouteService.getDomesticRouteById(id));
    }


    @GetMapping("/all-domesticRoutes")
    public Page<DomesticRouteDto> findAllDomesticRoutes(@RequestParam(value = "value",required = false) String value,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteria
                searchCriteria = new ObjectMapper().readValue(value, SearchCriteria.class);
        return domesticRouteService.findAllDomesticRoutes(searchCriteria,page,size);
    }

    @DeleteMapping("/delete-domestic-route/{id}")
    public ResponseEntity<ApiResponse> deleteDomesticRoute(@PathVariable Long id){
        return ResponseEntity.ok(domesticRouteService.deleteDomesticRoute(id));
    }

    @PatchMapping("/update-domestic-route/{id}")
    public ResponseEntity<DomesticRouteDto> update(@PathVariable Long id, @RequestBody DomesticRouteDto domesticRouteDto){
        return ResponseEntity.ok(domesticRouteService.updateDomesticRoute(id,domesticRouteDto));
    }
    @GetMapping("/all-domestic-route")
    public ResponseEntity<List<DomesticRouteDto>> getAll(){
        return ResponseEntity.ok(domesticRouteService.getAllDomesticRoute());
    }

}
