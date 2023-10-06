package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.LocationPortDto;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.LocationPort;
import com.example.CargoTracking.repository.LocationPortRepository;
import com.example.CargoTracking.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationPortService {

    @Autowired
    LocationPortRepository locationPortRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    LocationRepository locationRepository;
    public LocationPortDto addPort(LocationPortDto locationPortDto) {

        Location location = locationRepository.findByLocationName(locationPortDto.getLocation())
                .orElseThrow(()-> new RuntimeException("Location not Found"));

        LocationPort locationPort = LocationPort.builder()
                .portName(locationPortDto.getPortName())
                .location(location)
                .status(Boolean.TRUE)
                .build();

        return toDto(locationPortRepository.save(locationPort));
    }

    public List<LocationPortDto> getActivePorts() {
        return toDtoList(locationPortRepository.getActivePorts());
    }

    public LocationPortDto getById(Long id) {
        Optional<LocationPort> locationPort = locationPortRepository.findById(id);
        if (locationPort.isPresent()){
            return toDto(locationPort.get());
        }
        throw new RuntimeException(String.format("Port Not Found On this Id => %d",id));
    }

    public LocationPortDto deletePortById(Long id){
        Optional<LocationPort> locationPort = locationPortRepository.findById(id);

        if(locationPort.isPresent()){
            locationPort.get().setStatus(Boolean.FALSE);
            return toDto(locationPortRepository.save(locationPort.get()));
        }

        throw new RuntimeException("Record doesn't exist");
    }

    public LocationPortDto updateById(Long id, LocationPortDto locationPortDto) {
        Optional<LocationPort> locationPort = locationPortRepository.findById(id);

        Location location = locationRepository.findByLocationName(locationPortDto.getLocation())
                .orElseThrow(()-> new RuntimeException("Location not found"));

        if(locationPort.isPresent()){
            locationPort.get().setPortName(locationPortDto.getPortName());
            locationPort.get().setLocation(location);
            return toDto(locationPortRepository.save(locationPort.get()));
        }

        throw new RuntimeException(String.format("Port Not Found by this Id => %d" , id));
    }

    public LocationPortDto makePortActive(Long id) {
        Optional<LocationPort> locationPort = locationPortRepository.findById(id);
        if(locationPort.isPresent()){
            if(locationPort.get().isStatus()){
                throw new RuntimeException("Record is already Active");
            }
            locationPort.get().setStatus(Boolean.TRUE);
            return toDto(locationPortRepository.save(locationPort.get()));
        }
        throw new RuntimeException(String.format("Port Not Found by this Id => %d" , id));
    }


    public List<LocationPortDto> toDtoList(List<LocationPort> locationPort){
        return locationPort.stream().map(this::toDto).collect(Collectors.toList());
    }

    public LocationPortDto toDto(LocationPort locationPort){
        return modelMapper.map(locationPort, LocationPortDto.class);
    }

}
