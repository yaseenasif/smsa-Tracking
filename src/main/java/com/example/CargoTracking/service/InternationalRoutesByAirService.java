package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.dto.InternationalRoutesByAirDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.repository.DomesticRouteRepository;
import com.example.CargoTracking.repository.DomesticShipmentRepository;
import com.example.CargoTracking.repository.InternationalRoutesByAirRepository;
import com.example.CargoTracking.repository.InternationalShipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternationalRoutesByAirService {
    @Autowired
    InternationalRoutesByAirRepository internationalRoutesByAirRepository;
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<InternationalRoutesByAirDto> findInternationalRoute(String origin, String destination) {
        List<InternationalRoutesByAir> byOriginAndDestination =
                internationalRoutesByAirRepository.findByOriginAndDestination(origin, destination);
        if(byOriginAndDestination.isEmpty()){
            throw  new RecordNotFoundException(String.format("No routes available against given origin and destination"));
        }

        List<InternationalShipment> internationalShipment =
                internationalShipmentRepository.findByCreatedAt(LocalDate.now());

        List<InternationalRoutesByAir> resultList = new ArrayList<>();
        if(internationalShipment.isEmpty()){
            return toDtoList(byOriginAndDestination);
        }
        for (InternationalShipment shipment : internationalShipment) {
            resultList.addAll(
                    byOriginAndDestination.stream()
                            .filter(domesticRoute ->
                                    !shipment.getOriginPort().equals(domesticRoute.getOrigin()) ||
                                            !shipment.getDestinationPort().equals(domesticRoute.getDestination()) ||
                                            !shipment.getRouteNumber().equals(domesticRoute.getRoute())
                            )
                            .collect(Collectors.toList())
            );
        }
        if(resultList.isEmpty()){
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(resultList);
    }

    public List<InternationalRoutesByAirDto> toDtoList(List<InternationalRoutesByAir> internationalRoutesByAir){
        return internationalRoutesByAir.stream().map(this::toDto).collect(Collectors.toList());
    }

    public InternationalRoutesByAirDto toDto(InternationalRoutesByAir internationalRoutesByAir){
        return modelMapper.map(internationalRoutesByAir, InternationalRoutesByAirDto.class);
    }

    public InternationalRoutesByAir toEntity(InternationalRoutesByAirDto internationalRoutesByAirDto){
        return modelMapper.map(internationalRoutesByAirDto,InternationalRoutesByAir.class);
    }

    public InternationalRoutesByAir findRouteByRouteNumber(String routeNumber) {
        return  internationalRoutesByAirRepository.findByRoute(routeNumber);
    }

    public InternationalRoutesByAirDto saveInternationalRoute(InternationalRoutesByAirDto internationalRoutesByAirDto) {
        InternationalRoutesByAir internationalRoutesByAir = internationalRoutesByAirRepository.save(toEntity(internationalRoutesByAirDto));
        return toDto(internationalRoutesByAir);
    }

    public List<InternationalRoutesByAirDto> findAllInternationalRoutes() {
        return toDtoList(internationalRoutesByAirRepository.findAll());
    }
}
