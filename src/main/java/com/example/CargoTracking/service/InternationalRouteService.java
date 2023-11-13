package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.FileMetaDataDto;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.model.FileMetaData;
import com.example.CargoTracking.model.InternationalRoute;
import com.example.CargoTracking.repository.InternationalRouteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternationalRouteService {
    @Autowired
    InternationalRouteRepository internationalRouteRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<InternationalRouteDto> findInternationalRouteForAir(String origin, String destination) {
        List<InternationalRoute> byOriginAndDestination =
                internationalRouteRepository.findByOriginAndDestinationAndType(origin, destination,"Air");
        return toDtoList(byOriginAndDestination);
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
