package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalRoute;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.repository.DomesticRouteRepository;
import com.example.CargoTracking.repository.DomesticShipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DomesticRouteService {
    @Autowired
    DomesticRouteRepository domesticRouteRepository;
    @Autowired
    DomesticShipmentRepository domesticShipmentRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<DomesticRouteDto> findDomesticRoute(String origin, String destination,int trip) {
        List<DomesticRoute> byOriginAndDestination =
                domesticRouteRepository.findByOriginAndDestination(origin, destination);
        if(byOriginAndDestination.isEmpty()){
            throw  new RecordNotFoundException(String.format("No routes available against given origin and destination"));
        }

        List<DomesticShipment> domesticShipment =
                domesticShipmentRepository.findByCreatedAt(LocalDate.now());

        List<DomesticRoute> resultList = new ArrayList<>();
        if(domesticShipment.isEmpty()){
            return toDtoList(byOriginAndDestination);
        }
        for (DomesticShipment shipment : domesticShipment) {
            resultList.addAll(
                    byOriginAndDestination.stream()
                            .filter(domesticRoute ->
                                    !shipment.getOriginLocation().equals(domesticRoute.getOrigin()) ||
                                            !shipment.getDestinationLocation().equals(domesticRoute.getDestination()) ||
                                            !shipment.getRouteNumber().equals(domesticRoute.getRoute()) ||
                                            shipment.getTrip() != trip
                            )
                            .collect(Collectors.toList())
            );
        }
        if(resultList.isEmpty()){
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(resultList);
    }

    public List<DomesticRouteDto> toDtoList(List<DomesticRoute> domesticRoutes){
        return domesticRoutes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DomesticRouteDto toDto(DomesticRoute domesticRoute){
        return modelMapper.map(domesticRoute, DomesticRouteDto.class);
    }

    public DomesticRoute findRouteByRouteNumber(String routeNumber) {
        return  domesticRouteRepository.findByRoute(routeNumber);
    }
}
