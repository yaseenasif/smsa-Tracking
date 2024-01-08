package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.CountryDto;
import com.example.CargoTracking.dto.FacilityDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Country;
import com.example.CargoTracking.model.Facility;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.FacilityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacilityService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FacilityRepository facilityRepository;
    public FacilityDto addFacility(FacilityDto facilityDto) {
        Facility facility = toEntity(facilityDto);
        facility.setStatus(Boolean.TRUE);
        return toDto(facilityRepository.save(facility));
    }

    public List<FacilityDto> getAllFacilities() {
        List<Facility> facilities = facilityRepository.getAllByStatus(Boolean.TRUE);
        if(!facilities.isEmpty()){
            return toDtoList(facilities);
        }
        throw new RecordNotFoundException(String.format("There is no facilities present"));
    }

    public FacilityDto getFacilityById(Long id) {
        Optional<Facility> facility = facilityRepository.findByIdAndStatus(id,Boolean.TRUE);
        if(facility.isPresent()){
            return toDto(facility.get());
        }else{
            throw new RecordNotFoundException(String.format("facility not found by this Id %d", id));
        }
    }

    public FacilityDto updateFacility(Long id,FacilityDto facilityDto) {
        Optional<Facility> optionalFacility = facilityRepository.findByIdAndStatus(id, Boolean.TRUE);
        if(optionalFacility.isPresent()){
            Facility facility = optionalFacility.get();
            facility.setName(facilityDto.getName());
            facility.setCountry(facilityDto.getCountry());
            facility.setStatus(Boolean.TRUE);
            return toDto(facilityRepository.save(facility));
        } else {
            throw new RecordNotFoundException(String.format("Facility not found by this Id %d", id));
        }
    }

    public ApiResponse deleteFacility(Long id) {
        Optional<Facility> facility = facilityRepository.findByIdAndStatus(id, Boolean.TRUE);
        if(facility.isPresent()){
            facility.get().setStatus(Boolean.FALSE);
            facilityRepository.save(facility.get());

            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }else{
            throw new RecordNotFoundException(String.format("Facility not found by this Id %d", id));
        }
    }

    public List<FacilityDto> getFacilitiesByCountryName(String country) {
        List<Facility> facilities = facilityRepository.getAllByStatusAndCountryName(Boolean.TRUE,country);
        if(!facilities.isEmpty()){
            return toDtoList(facilities);
        }
        throw new RecordNotFoundException(String.format("There is no facilities present with this country"));
    }

    public List<FacilityDto> toDtoList(List<Facility> facilities) {
        return facilities.stream().map(this::toDto).collect(Collectors.toList());
    }

    private FacilityDto toDto(Facility facility) {
        return modelMapper.map(facility, FacilityDto.class);
    }


    private Facility toEntity(FacilityDto facilityDto) {
        return modelMapper.map(facilityDto, Facility.class);
    }



}
