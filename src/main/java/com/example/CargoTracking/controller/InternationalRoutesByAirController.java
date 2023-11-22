//package com.example.CargoTracking.controller;
//
//import com.example.CargoTracking.dto.InternationalRoutesByAirDto;
//import com.example.CargoTracking.model.InternationalRoutesByAir;
//import com.example.CargoTracking.service.InternationalRoutesByAirService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class InternationalRoutesByAirController {
//    @Autowired
//    InternationalRoutesByAirService internationalRoutesByAirService;
//
//    @GetMapping("/getRoute/{origin}/{destination}")
//    public List<InternationalRoutesByAirDto> getInternationalRouteForAir(@PathVariable String origin,
//                                                                         @PathVariable String destination ){
//        return internationalRoutesByAirService.findInternationalRoute(origin,destination);
//    }
//
//    @GetMapping("internationalRouteByAir/{routeNumber}")
//    public InternationalRoutesByAir getRouteByRouteNumber(@PathVariable String routeNumber){
//        return internationalRoutesByAirService.findRouteByRouteNumber(routeNumber);
//    }
//
//    @PostMapping("/add-internationalRouteByAir")
//    public InternationalRoutesByAirDto addInternationalRoute(@RequestBody InternationalRoutesByAirDto internationalRoutesByAirDto){
//        return internationalRoutesByAirService.saveInternationalRoute(internationalRoutesByAirDto);
//    }
//
//    @GetMapping("/all-internationalRoutesByAir")
//    public List<InternationalRoutesByAirDto> findAllInternationalRoutesByAir(){
//        return internationalRoutesByAirService.findAllInternationalRoutes();
//    }
//}
//
