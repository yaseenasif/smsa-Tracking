package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.model.InternationalRoute;
import com.example.CargoTracking.service.InternationalRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class InternationalRouteController {
    @Autowired
    InternationalRouteService internationalRouteService;

    @GetMapping("/getRoute-air/{origin}/{destination}/{trip}")
    public List<InternationalRouteDto> getInternationalRouteForAir(@PathVariable String origin,
                                                                   @PathVariable String destination,
                                                                   @PathVariable int trip){
        return internationalRouteService.findInternationalRouteForAir(origin,destination,trip);
    }
    @GetMapping("/getRoute-road/{origin}/{destination}/{trip}")
    public List<InternationalRouteDto> getInternationalRouteForRoad(@PathVariable String origin,
                                                                    @PathVariable String destination,
                                                                    @PathVariable int trip){
        return internationalRouteService.findInternationalRouteForRoad(origin,destination,trip);
    }

    @GetMapping("/get-all-international-air")
    public ResponseEntity<List<InternationalRouteDto>> getInternationalRouteByAir(){
        return ResponseEntity.ok(internationalRouteService.findAllInternationalRouteForAir());
    }

    @GetMapping("/get-all-international-road")
    public List<InternationalRouteDto> getInternationalRouteByRoad(){
        return internationalRouteService.findAllInternationalRouteForRoad();
    }

    @PostMapping("add-internationalRoute")
    public InternationalRouteDto addInternationRoute(@RequestBody InternationalRouteDto internationalRouteDto){
        return internationalRouteService.saveInternationalRoute(internationalRouteDto);
    }
}
