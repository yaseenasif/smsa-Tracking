package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.FileMetaDataDto;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.FileMetaData;
import com.example.CargoTracking.model.InternationalRoute;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.repository.InternationalRouteRepository;
import com.example.CargoTracking.repository.InternationalShipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternationalRouteService {
    @Autowired
    InternationalRouteRepository internationalRouteRepository;
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<InternationalRouteDto> findInternationalRouteForAir(String origin, String destination) {
        List<InternationalRoute> byOriginAndDestination =
                internationalRouteRepository.findByOriginAndDestinationAndType(origin, destination,"Air");

        List<InternationalShipment> internationalShipment =
                internationalShipmentRepository.findByCreatedAtAndType(LocalDate.now(), "By Air");

        List<InternationalRoute> resultList = new ArrayList<>();

//        for (InternationalShipment shipment: internationalShipment) {
//            for (InternationalRoute internationalRoute: byOriginAndDestination) {
//                if(shipment.getOriginPort().equals(internationalRoute.getOrigin()) &&
//                   shipment.getDestinationPort().equals(internationalRoute.getDestination()) &&
//                   shipment.getRouteNumber().equals(internationalRoute.getRoute())
//                ){
//
//                }else{
//                    resultList.add(internationalRoute);
//
//                }
//            }
//        }
        for (InternationalShipment shipment : internationalShipment) {
            resultList.addAll(
                    byOriginAndDestination.stream()
                            .filter(internationalRoute ->
                                    !shipment.getOriginPort().equals(internationalRoute.getOrigin()) ||
                                            !shipment.getDestinationPort().equals(internationalRoute.getDestination()) ||
                                            !shipment.getRouteNumber().equals(internationalRoute.getRoute())
                            )
                            .collect(Collectors.toList())
            );
        }
        if(resultList.isEmpty()){
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(resultList);
    }

    public List<InternationalRouteDto> findInternationalRouteForRoad(String origin, String destination) {
        List<InternationalRoute> byOriginAndDestination =
                internationalRouteRepository.findByOriginAndDestinationAndType(origin, destination,"Road");
        return toDtoList(byOriginAndDestination);
    }

    public List<InternationalRouteDto> toDtoList(List<InternationalRoute> internationalRoutes){
        return internationalRoutes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public InternationalRouteDto toDto(InternationalRoute internationalRoute){
        return modelMapper.map(internationalRoute, InternationalRouteDto.class);
    }
}
