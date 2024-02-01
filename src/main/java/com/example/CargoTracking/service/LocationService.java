package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.LocationDto;
import com.example.CargoTracking.exception.RecordAlreadyExist;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Country;
import com.example.CargoTracking.model.Facility;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.FacilityRepository;
import com.example.CargoTracking.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    FacilityRepository facilityRepository;
    @Autowired
    ModelMapper modelMapper;


    @Transactional
    public LocationDto addLocation(LocationDto locationDto) {
        String countryToInsert = locationDto.getCountry().getName();

        List<Location> locationsByName = locationRepository.findByLocationName(locationDto.getLocationName());

        if (locationsByName.isEmpty()) {
            Location location = Location.builder()
                    .locationName(locationDto.getLocationName().toUpperCase())
                    .type(locationDto.getType())
                    .status(Boolean.TRUE)
                    .originEmail(locationDto.getOriginEmail())
                    .destinationEmail(locationDto.getDestinationEmail())
                    .originEscalation(locationDto.getOriginEscalation())
                    .destinationEscalation(locationDto.getDestinationEscalation())
                    .country(locationDto.getCountry())
                    .facility(locationDto.getFacility())
                    .build();

            return toDto(locationRepository.save(location));
        } else {
            boolean isCountryPresent = locationsByName
                    .stream()
                    .map(Location::getCountry)
                    .map(Country::getName)
                    .allMatch(countryToInsert::equals);
            if (isCountryPresent) {
                Location location = Location.builder()
                        .locationName(locationDto.getLocationName().toUpperCase())
                        .type(locationDto.getType())
                        .status(Boolean.TRUE)
                        .originEmail(locationDto.getOriginEmail())
                        .destinationEmail(locationDto.getDestinationEmail())
                        .originEscalation(locationDto.getOriginEscalation())
                        .destinationEscalation(locationDto.getDestinationEscalation())
                        .country(locationDto.getCountry())
                        .facility(locationDto.getFacility())
                        .build();

                return toDto(locationRepository.save(location));
            } else {
               throw new RecordAlreadyExist( "Location with the same name already exists in the specified country");
            }
        }


//        boolean locationExists = locationRepository.existsByLocationNameAndCountryId(
//                locationDto.getLocationName().toUpperCase(),
//                locationDto.getCountry().getId()
//        );
//
//        if (locationExists) {
//        }

//        List<Location> locations = locationRepository.findByLocationName(locationDto.getLocationName().toUpperCase());
//        for (Location location: locations){
//            if(location.getCountry().equals(locationDto.getCountry())){
//                throw new RecordAlreadyExist( "Location with the same name already exists in the specified country");
//            }
//        }

    }

    public List<LocationDto> getActiveLocations() {
        return toDtoList(locationRepository.getActiveLocations());
    }

    public List<LocationDto> getActiveLocationsForDomestic() {
        return toDtoList(locationRepository.getActiveLocationsByType("Domestic"));
    }

    public List<LocationDto> getActiveLocationsForInternational() {
        return toDtoList(locationRepository.getActiveLocationsByType("International"));
    }

    public LocationDto getById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()){
            return toDto(location.get());
        }
        throw new RuntimeException(String.format("Location Not Found On this Id => %d",id));
    }

    public ApiResponse deleteLocationById(Long id){
        Optional<Location> location = locationRepository.findById(id);

        if(location.isPresent()){
            location.get().setStatus(Boolean.FALSE);
            locationRepository.save(location.get());
            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }

        throw new RuntimeException("Record doesn't exist");
    }

    public LocationDto updateById(Long id, LocationDto locationDto) {
        Optional<Location> location = locationRepository.findById(id);

        if(location.isPresent()){
            location.get().setLocationName(locationDto.getLocationName());
            location.get().setType(locationDto.getType());
            location.get().setOriginEmail(locationDto.getOriginEmail());
            location.get().setDestinationEmail(locationDto.getDestinationEmail());
            location.get().setOriginEscalation(locationDto.getOriginEscalation());
            location.get().setDestinationEscalation(locationDto.getDestinationEscalation());
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

//    public List<LocationDto> getLocationByFacilityName(Long facility, String type) {
//        Optional<Facility> facilityByIdAndStatus = facilityRepository.findByIdAndStatus(facility, Boolean.TRUE);
//        if(!facilityByIdAndStatus.isPresent()){
//            throw  new RecordNotFoundException("Facility do not exist");
//        }
//        List<Location> locations = locationRepository.getAllByFacilityAndType(facilityByIdAndStatus.get(),type);
//        if(!locations.isEmpty()){
//            return toDtoList(locations);
//        }else{
//            throw new RecordNotFoundException(String.format("Location Not Found"));
//        }
//    }

    public Location getLocationByName(String locationName,String type){
        return locationRepository.findByLocationNameAndType(locationName,type);
    }

    public List<LocationDto> toDtoList(List<Location> location){
        return location.stream().map(this::toDto).collect(Collectors.toList());
    }

    public LocationDto toDto(Location location){
        return modelMapper.map(location, LocationDto.class);
    }


}
