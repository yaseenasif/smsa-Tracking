package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalRoute;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalShipment;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.model.InternationalRoute;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.InternationalRouteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class InternationalRouteController {
    @Autowired
    InternationalRouteService internationalRouteService;

    @PreAuthorize("hasAuthority('get-internationalAirRouteByOriginDestinationTrip')")
    @GetMapping("/getRoute-air/{origin}/{destination}/{trip}")
    public List<InternationalRouteDto> getInternationalRouteForAir(@PathVariable String origin,
                                                                   @PathVariable String destination,
                                                                   @PathVariable int trip){
        return internationalRouteService.findInternationalRouteForAir(origin,destination,trip);
    }

    @PreAuthorize("hasAuthority('get-internationalRoadRouteByOriginDestinationTrip')")
    @GetMapping("/getRoute-road/{origin}/{destination}/{trip}")
    public List<InternationalRouteDto> getInternationalRouteForRoad(@PathVariable String origin,
                                                                    @PathVariable String destination,
                                                                    @PathVariable int trip){
        return internationalRouteService.findInternationalRouteForRoad(origin,destination,trip);
    }

    @PreAuthorize("hasAuthority('get-internationalAirRoute')")
    @GetMapping("/get-all-international-air")
    public ResponseEntity<Page<InternationalRouteDto>> getInternationalRouteByAir(@RequestParam(value = "value",required = false) String value,
                                                                                  @RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalRoute
                searchCriteriaForInternationalRoute = new ObjectMapper().readValue(value, SearchCriteriaForInternationalRoute.class);
        return ResponseEntity.ok(internationalRouteService.findAllInternationalRouteForAir(searchCriteriaForInternationalRoute,page,size));
    }

    @PreAuthorize("hasAuthority('get-internationalRoadRoute')")
    @GetMapping("/get-all-international-road")
    public ResponseEntity<Page<InternationalRouteDto>> getInternationalRouteByRoad(@RequestParam(value = "value",required = false) String value,
                                                                                   @RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalRoute
                searchCriteriaForInternationalRoute = new ObjectMapper().readValue(value, SearchCriteriaForInternationalRoute.class);
        return ResponseEntity.ok(internationalRouteService.findAllInternationalRouteForRoad(searchCriteriaForInternationalRoute,page,size));
    }

    @PreAuthorize("hasAuthority('add-internationalRoute')")
    @PostMapping("/add-internationalRoute")
    public InternationalRouteDto addInternationRoute(@RequestBody InternationalRouteDto internationalRouteDto){
        return internationalRouteService.saveInternationalRoute(internationalRouteDto);
    }

    @PreAuthorize("hasAuthority('update-internationalRoute')")
    @PutMapping("/update-international-route/{id}")
    public ResponseEntity<InternationalRouteDto> updateInternationalRoute(@PathVariable Long id,@RequestBody InternationalRouteDto internationalRouteDto){
        return ResponseEntity.ok(internationalRouteService.updateInternationalRoute(id,internationalRouteDto));
    }

    @PreAuthorize("hasAuthority('delete-internationalRoute')")
    @DeleteMapping("/delete-international-route/{id}")
    public ResponseEntity<ApiResponse> deleteInternationalRoute(@PathVariable Long id){
        return ResponseEntity.ok(internationalRouteService.deleteInternationalRoute(id));
    }

    @PreAuthorize("hasAuthority('get-internationalRouteById')")
    @GetMapping("/get-international-route-by-id/{id}")
    public ResponseEntity<InternationalRouteDto> internationalRouteById(@PathVariable Long id){
        return ResponseEntity.ok(internationalRouteService.getInternationalRouteById(id));
    }

}
