package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.LocationDto;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    ModelMapper modelMapper;

    public LocationDto addLocation(LocationDto locationDto) {

        Location location = Location.builder()
                .locationName(locationDto.getLocationName())
                .status(Boolean.TRUE)
                .build();

        return toDto(locationRepository.save(location));
    }

    public List<LocationDto> getActiveLocations() {
        return toDtoList(locationRepository.getActiveLocations());
    }

    public LocationDto getById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()){
            return toDto(location.get());
        }
        throw new RuntimeException(String.format("Location Not Found On this Id => %d",id));
    }

    public LocationDto deleteLocationById(Long id){
        Optional<Location> location = locationRepository.findById(id);

        if(location.isPresent()){
            location.get().setStatus(Boolean.FALSE);
            return toDto(locationRepository.save(location.get()));
        }

        throw new RuntimeException("Record doesn't exist");
    }

    public LocationDto updateById(Long id, LocationDto locationDto) {
        Optional<Location> location = locationRepository.findById(id);

        if(location.isPresent()){
            location.get().setLocationName(locationDto.getLocationName());
            return toDto(locationRepository.save(location.get()));
        }

        throw new RuntimeException(String.format("Location Not Found by this Id => %d" , id));
    }

    public LocationDto makeLocationActive(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if(location.isPresent()){
            if(location.get().isStatus()){
                throw new RuntimeException("Record is already Active");
            }
            location.get().setStatus(Boolean.TRUE);
            return toDto(locationRepository.save(location.get()));
        }
        throw new RuntimeException(String.format("Location Not Found by this Id => %d" , id));
    }

    public List<LocationDto> toDtoList(List<Location> location){
        return location.stream().map(this::toDto).collect(Collectors.toList());
    }

    public LocationDto toDto(Location location){
        return modelMapper.map(location, LocationDto.class);
    }

}
