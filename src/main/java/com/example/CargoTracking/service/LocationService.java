package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.LocationDto;
import com.example.CargoTracking.dto.SearchCriteriaForLocation;
import com.example.CargoTracking.dto.UserResponseDto;
import com.example.CargoTracking.exception.RecordAlreadyExist;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Country;
import com.example.CargoTracking.model.Facility;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.FacilityRepository;
import com.example.CargoTracking.repository.LocationRepository;
import com.example.CargoTracking.specification.LocationSpecification;
import com.example.CargoTracking.specification.UserSpecification;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    FacilityRepository facilityRepository;
    @Autowired
    ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);


    @Transactional
    public LocationDto addLocation(LocationDto locationDto) {

        Location byLocationNameAndCountryAndFacility = locationRepository
                .findByLocationNameAndCountryAndFacilityAndStatus(locationDto.getLocationName(), locationDto.getCountry(), locationDto.getFacility(),true);
        if(byLocationNameAndCountryAndFacility!=null){
            throw new RecordAlreadyExist( "Location is already existed.");
        }
        String countryToInsert = locationDto.getCountry().getName();

        List<Location> locationsByName = locationRepository.findByLocationName(locationDto.getLocationName());

        if (locationsByName.isEmpty()) {
            Location location = Location.builder()
                    .locationName(locationDto.getLocationName().toUpperCase())
                    .type(locationDto.getType())
                    .status(Boolean.TRUE)
                    .originEmail(locationDto.getOriginEmail())
                    .destinationEmail(locationDto.getDestinationEmail())
                    .originEscalationLevel1(locationDto.getOriginEscalationLevel1())
                    .originEscalationLevel2(locationDto.getOriginEscalationLevel2())
                    .originEscalationLevel3(locationDto.getOriginEscalationLevel3())
                    .destinationEscalationLevel1(locationDto.getDestinationEscalationLevel1())
                    .destinationEscalationLevel2(locationDto.getDestinationEscalationLevel2())
                    .destinationEscalationLevel3(locationDto.getDestinationEscalationLevel3())
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
                        .originEscalationLevel1(locationDto.getOriginEscalationLevel1())
                        .originEscalationLevel2(locationDto.getOriginEscalationLevel2())
                        .originEscalationLevel3(locationDto.getOriginEscalationLevel3())
                        .destinationEscalationLevel1(locationDto.getDestinationEscalationLevel1())
                        .destinationEscalationLevel2(locationDto.getDestinationEscalationLevel2())
                        .destinationEscalationLevel3(locationDto.getDestinationEscalationLevel3())
                        .country(locationDto.getCountry())
                        .facility(locationDto.getFacility())
                        .build();

                return toDto(locationRepository.save(location));
            } else {
               throw new RecordAlreadyExist( "Location with the same name already exists in the specified country");
            }
        }
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
        logger.info("--------------------------------------------------------------------------------------------------------");
        logger.info("in update method");
        Optional<Location> location = locationRepository.findById(id);
        logger.info("finding location by name country and facility");
        Location byLocationNameAndCountryAndFacility = locationRepository
                .findByLocationNameAndCountryAndFacilityAndStatus(locationDto.getLocationName(), locationDto.getCountry(), locationDto.getFacility(),true);
        logger.info("Successfully find");
        if(byLocationNameAndCountryAndFacility!=null){
            logger.info("enter first if");

            if(!Objects.equals(byLocationNameAndCountryAndFacility.getId(), id)){
                logger.info("enter first if nested if and print exception");

                throw new RecordAlreadyExist( "Location is already existed.");
            }
        }
        if (location.isPresent()) {
            logger.info("then enter second if");
            logger.info("if location is present");

            String countryToInsert = locationDto.getCountry().getName();

            List<Location> locationsByName = locationRepository.findByLocationName(locationDto.getLocationName());
            logger.info("find location list by location name");

            if (locationsByName.isEmpty()) {
                logger.info("if locationByNames list is empty then simply add location");

                location.get().setLocationName(locationDto.getLocationName());
                location.get().setType(locationDto.getType());
                location.get().setOriginEmail(locationDto.getOriginEmail());
                location.get().setDestinationEmail(locationDto.getDestinationEmail());
                location.get().setOriginEscalationLevel1(locationDto.getOriginEscalationLevel1());
                location.get().setOriginEscalationLevel2(locationDto.getOriginEscalationLevel2());
                location.get().setOriginEscalationLevel3(locationDto.getOriginEscalationLevel3());
                location.get().setDestinationEscalationLevel1(locationDto.getDestinationEscalationLevel1());
                location.get().setDestinationEscalationLevel2(locationDto.getDestinationEscalationLevel2());
                location.get().setDestinationEscalationLevel3(locationDto.getDestinationEscalationLevel3());
                location.get().setStatus(Boolean.TRUE);
                location.get().setFacility(locationDto.getFacility());
                location.get().setCountry(locationDto.getCountry());
                return toDto(locationRepository.save(location.get()));

            } else {
                logger.info("in else part");

                boolean isCountryPresent = locationsByName
                        .stream()
                        .map(Location::getCountry)
                        .map(Country::getName)
                        .allMatch(countryToInsert::equals);
                logger.info("checking jis country ki location hai usi me add ker rahe heen");

                if (isCountryPresent) {
                    logger.info("if country is present then add location");

                    location.get().setLocationName(locationDto.getLocationName());
                    location.get().setType(locationDto.getType());
                    location.get().setOriginEmail(locationDto.getOriginEmail());
                    location.get().setDestinationEmail(locationDto.getDestinationEmail());
                    location.get().setOriginEscalationLevel1(locationDto.getOriginEscalationLevel1());
                    location.get().setOriginEscalationLevel2(locationDto.getOriginEscalationLevel2());
                    location.get().setOriginEscalationLevel3(locationDto.getOriginEscalationLevel3());
                    location.get().setDestinationEscalationLevel1(locationDto.getDestinationEscalationLevel1());
                    location.get().setDestinationEscalationLevel2(locationDto.getDestinationEscalationLevel2());
                    location.get().setDestinationEscalationLevel3(locationDto.getDestinationEscalationLevel3());
                    location.get().setStatus(Boolean.TRUE);
                    location.get().setFacility(locationDto.getFacility());
                    location.get().setCountry(locationDto.getCountry());
                    return toDto(locationRepository.save(location.get()));
                } else {
                    logger.info("throw exception");

                    throw new RecordAlreadyExist("Location with the same name already exists in the specified country");
                }
            }
        }
        throw new RuntimeException(String.format("Location Not Found by this Id => %d", id));
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


    public Page<LocationDto> getFilterLocations(SearchCriteriaForLocation searchCriteriaForLocation, Pageable pageable) {
        Specification<Location> locationSpecification= LocationSpecification.findLocation(searchCriteriaForLocation);
        Page<Location> locationsPage = locationRepository.findAll(locationSpecification, pageable);
        List<LocationDto> locationDtos= locationsPage.stream().map(this::mapToDto).collect(Collectors.toList());
        return new PageImpl<>(locationDtos, pageable, locationsPage.getTotalElements());
    }
    private LocationDto mapToDto(Location location){
      return   modelMapper.map(location,LocationDto.class);
    }
}
