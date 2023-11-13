package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.model.InternationalRoute;
import com.example.CargoTracking.service.InternationalRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InternationalRouteController {
    @Autowired
    InternationalRouteService internationalRouteService;

    @GetMapping("/getRoute-air/{origin}/{destination}")
    public List<InternationalRouteDto> getInternationalRouteForAir(@PathVariable String origin, @PathVariable String destination){
        return internationalRouteService.findInternationalRouteForAir(origin,destination);
    }
    @GetMapping("/getRoute-road/{origin}/{destination}")
    public List<InternationalRouteDto> getInternationalRouteForRoad(@PathVariable String origin, @PathVariable String destination){
        return internationalRouteService.findInternationalRouteForRoad(origin,destination);
    }

}
