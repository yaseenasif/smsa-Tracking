package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.service.DomesticRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DomesticRouteController {
    @Autowired
    DomesticRouteService domesticRouteService;

    @GetMapping("/getRoute/{origin}/{destination}")
    public List<DomesticRouteDto> getInternationalRouteForAir(@PathVariable String origin,
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


    @GetMapping("/all-domesticRoutes")
    public List<DomesticRouteDto> findAllDomesticRoutes(){
        return domesticRouteService.findAllDomesticRoutes();
    }
}
