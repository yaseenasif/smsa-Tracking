package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.FileMetaDataDto;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.FileMetaData;
import com.example.CargoTracking.model.InternationalRoute;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.InternationalRouteRepository;
import com.example.CargoTracking.repository.InternationalShipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InternationalRouteService {
    @Autowired
    InternationalRouteRepository internationalRouteRepository;
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<InternationalRouteDto> findInternationalRouteForAir(String origin, String destination, int trip) {
        List<InternationalRoute> byOriginAndDestination =
                internationalRouteRepository.findByOriginAndDestinationAndType(origin, destination,"Air");
        if(byOriginAndDestination.isEmpty()){
            throw  new RecordNotFoundException(String.format("No routes available against given origin and destination"));
        }

        List<InternationalShipment> internationalShipment =
                internationalShipmentRepository.findByCreatedAtAndType(LocalDate.now(), "By Air");

        List<InternationalRoute> resultList = new ArrayList<>();
        if(internationalShipment.isEmpty()){
            return toDtoList(byOriginAndDestination);
        }
        List<InternationalRoute> usedRoute = new ArrayList<>();
        for (InternationalRoute route: byOriginAndDestination) {
            for(InternationalShipment shipment: internationalShipment){
                if(route.getRoute().equals(shipment.getRouteNumber())){
                    if(shipment.getTrip() == trip){
                        usedRoute.add(route);
                        break;
                    }

                }
            }

        }
        byOriginAndDestination.removeAll(usedRoute);

        if(byOriginAndDestination.isEmpty()){
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(byOriginAndDestination);
    }

    public List<InternationalRouteDto> findInternationalRouteForRoad(String origin, String destination, int trip) {
        List<InternationalRoute> byOriginAndDestination =
                internationalRouteRepository.findByOriginAndDestinationAndType(origin, destination,"Road");
        if(byOriginAndDestination.isEmpty()){
            throw  new RecordNotFoundException(String.format("No routes available against given origin and destination"));
        }
        List<InternationalShipment> internationalShipment =
                internationalShipmentRepository.findByCreatedAtAndType(LocalDate.now(), "By Road");

        List<InternationalRoute> resultList = new ArrayList<>();

        if(internationalShipment.isEmpty()){
            return toDtoList(byOriginAndDestination);
        }

        List<InternationalRoute> usedRoute = new ArrayList<>();
        for (InternationalRoute route:byOriginAndDestination){
            for(InternationalShipment shipment : internationalShipment){
                if(route.getRoute().equals(shipment.getRouteNumber())){
                    if(shipment.getTrip() == trip){
                        usedRoute.add(route);
                        break;
                    }
                }

            }
        }
        byOriginAndDestination.removeAll(usedRoute);

        if(byOriginAndDestination.isEmpty()){
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(byOriginAndDestination);
    }

    public List<InternationalRouteDto> toDtoList(List<InternationalRoute> internationalRoutes){
        return internationalRoutes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public InternationalRouteDto toDto(InternationalRoute internationalRoute){
        return modelMapper.map(internationalRoute, InternationalRouteDto.class);
    }

    public List<InternationalRouteDto> findAllInternationalRouteForAir() {
        List<InternationalRoute> internationalRouteForAir = internationalRouteRepository.findAllByType("Air");
        return toDtoList(internationalRouteForAir);
    }

    public List<InternationalRouteDto> findAllInternationalRouteForRoad() {
        List<InternationalRoute> internationalRouteForRoad = internationalRouteRepository.findAllByType("Road");
        return toDtoList(internationalRouteForRoad);
    }
    public InternationalRouteDto saveInternationalRoute(InternationalRouteDto internationalRouteDto) {
        InternationalRoute internationalRoute = toEntity(internationalRouteDto);
        internationalRoute.setStatus(Boolean.TRUE);
        internationalRoute = internationalRouteRepository.save(internationalRoute);
        return toDto(internationalRoute);
    }
    public InternationalRoute toEntity(InternationalRouteDto internationalRouteDto){
        return modelMapper.map(internationalRouteDto,InternationalRoute.class);
    }

    public InternationalRouteDto updateInternationalRoute(Long id, InternationalRouteDto internationalRouteDto) {
        Optional<InternationalRoute> internationalRoute = internationalRouteRepository.findById(id);
        if(internationalRoute.isPresent()){
            internationalRoute.get().setOrigin(internationalRouteDto.getOrigin());
            internationalRoute.get().setDestination(internationalRouteDto.getDestination());
            internationalRoute.get().setRoute(internationalRouteDto.getRoute());
            internationalRoute.get().setDriverId(internationalRouteDto.getDriverId());
            internationalRoute.get().setFlight(internationalRouteDto.getFlight());
            internationalRoute.get().setEta(internationalRouteDto.getEta());
            internationalRoute.get().setEtd(internationalRouteDto.getEtd());

            InternationalRoute save = internationalRouteRepository.save(internationalRoute.get());
            return toDto(save);

        }else{
            throw new RecordNotFoundException(String.format("Shipment Route is not available for id: %d",id));
        }
    }

    public ApiResponse deleteInternationalRoute(Long id) {
        Optional<InternationalRoute> internationalRoute = internationalRouteRepository.findById(id);
        if(internationalRoute.isPresent()){
            InternationalRoute route = internationalRoute.get();
            route.setStatus(Boolean.FALSE);
            internationalRouteRepository.save(route);
            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }else{
            throw new RecordNotFoundException(String.format("Shipment Route is not available for id: %d",id));
        }
    }

    public InternationalRouteDto getInternationalRouteById(Long id) {
        Optional<InternationalRoute> internationalRoute = internationalRouteRepository.findById(id);
        if(internationalRoute.isPresent()){
            return toDto(internationalRoute.get());
        }else{
            throw new RecordNotFoundException(String.format("Shipment Route is not available for id: %d",id));
        }
    }
}
