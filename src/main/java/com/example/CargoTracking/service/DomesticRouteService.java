package com.example.CargoTracking.service;
import com.example.CargoTracking.criteria.SearchCriteria;
import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.DomesticRouteRepository;
import com.example.CargoTracking.repository.DomesticShipmentRepository;
import com.example.CargoTracking.repository.UserRepository;
import com.example.CargoTracking.specification.DomesticRouteSpecification;
import com.example.CargoTracking.specification.DomesticShipmentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DomesticRouteService {
    @Autowired
    DomesticRouteRepository domesticRouteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DomesticShipmentRepository domesticShipmentRepository;

    public List<DomesticRouteDto> findDomesticRoute(String origin, String destination) {
        List<DomesticRoute> byOriginAndDestination =
                domesticRouteRepository.findByOriginAndDestinationAndActiveStatus(origin, destination,true);
        if (byOriginAndDestination.isEmpty()) {
            throw new RecordNotFoundException(String.format("No routes available against given origin and destination"));
        }

        List<DomesticShipment> domesticShipment =
                domesticShipmentRepository.findByCreatedAt(LocalDate.now());

        List<DomesticRoute> resultList = new ArrayList<>();
        if (domesticShipment.isEmpty()) {
            return toDtoList(byOriginAndDestination);
        }

        List<DomesticRoute> usedRoute = new ArrayList<>();

        for(DomesticRoute route:byOriginAndDestination){
            for(DomesticShipment shipment : domesticShipment){
                if (route.getRoute().equals(shipment.getRouteNumber())) {
                    usedRoute.add(route);
                    break;
                }
            }
        }

        byOriginAndDestination.removeAll(usedRoute);

        if (byOriginAndDestination.isEmpty()) {
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(byOriginAndDestination);
    }

    public List<DomesticRouteDto> toDtoList(List<DomesticRoute> domesticRoutes) {
        return domesticRoutes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DomesticRouteDto toDto(DomesticRoute domesticRoute) {
        return modelMapper.map(domesticRoute, DomesticRouteDto.class);
    }

    public DomesticRoute toEntity(DomesticRouteDto domesticRouteDto) {
        return modelMapper.map(domesticRouteDto, DomesticRoute.class);
    }

    public DomesticRoute findRouteByRouteNumber(String routeNumber) {
        return domesticRouteRepository.findByRoute(routeNumber);
    }

    public DomesticRouteDto saveDomesticRoute(DomesticRouteDto domesticRouteDto) {
        DomesticRoute domesticRoute = toEntity(domesticRouteDto);
        domesticRoute.setActiveStatus(Boolean.TRUE);
        domesticRoute = domesticRouteRepository.save(domesticRoute);
        return toDto(domesticRoute);
    }

    public Page<DomesticRouteDto> findAllDomesticRoutes(SearchCriteria searchCriteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<DomesticRoute> domesticRouteSpecification = DomesticRouteSpecification.getSearchSpecification(searchCriteria);
        Page<DomesticRoute> domesticRoutes = domesticRouteRepository.findAll(domesticRouteSpecification, pageable);
        Page<DomesticRouteDto> domesticRouteDtos = domesticRoutes.map(entity -> toDto(entity));

        return domesticRouteDtos;
//        return toDtoList(domesticRouteRepository.getActiveDomesticRoutes());
    }

    public ApiResponse deleteDomesticRoute(Long id) {
        Optional<DomesticRoute> domesticRoute = domesticRouteRepository.findById(id);
        if (domesticRoute.isPresent()) {
            DomesticRoute route = domesticRoute.get();
            route.setActiveStatus(Boolean.FALSE);
            domesticRouteRepository.save(route);
            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }
        throw new RecordNotFoundException(String.format("Domestic Route not found by this id => %d", id));
    }

    public DomesticRouteDto updateDomesticRoute(Long id, DomesticRouteDto domesticRouteDto) {
        Optional<DomesticRoute> domesticRoute = domesticRouteRepository.findById(id);
        if (domesticRoute.isPresent()) {

            domesticRoute.get().setOrigin(domesticRouteDto.getOrigin());
            domesticRoute.get().setOrigin(domesticRouteDto.getDestination());
            domesticRoute.get().setRoute(domesticRouteDto.getRoute());
            domesticRoute.get().setEtd(domesticRouteDto.getEtd());
            domesticRoute.get().setEta(domesticRouteDto.getEta());
            domesticRoute.get().setDriver(domesticRouteDto.getDriver());
            domesticRoute.get().setDurationLimit(domesticRouteDto.getDurationLimit());

            DomesticRoute save = domesticRouteRepository.save(domesticRoute.get());
            return toDto(save);

        } else {
            throw new RecordNotFoundException(String.format("Domestic Route Not Found By This Id %d", id));
        }
    }

    public DomesticRouteDto getDomesticRouteById(Long id) {
        Optional<DomesticRoute> domesticRoute = domesticRouteRepository.findById(id);
        if(domesticRoute.isPresent()){
            return toDto(domesticRoute.get());
        }else{
            throw new RecordNotFoundException(String.format("Domestic Route Not Found By This Id %d", id));
        }
    }
}
