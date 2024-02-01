package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.CountryDto;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Country;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.Facility;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.CountryRepository;
import com.example.CargoTracking.repository.FacilityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
@Transactional
@Service
public class CountryService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    FacilityRepository facilityRepository;

    public CountryDto addCountry(CountryDto countryDto) {
        if (countryRepository.existsByName(countryDto.getName())) {
            throw new RecordNotFoundException("A country with the same name already exists.");
        }
        Country country = toEntity(countryDto);
        country.setStatus(Boolean.TRUE);

//        Set<Facility> facilities = new HashSet<>();
//        if(country.getFacilities()!=null){
//            for(Facility facility : countryDto.getFacilities()){
//                Optional<Facility> actualFacility = facilityRepository.findById(facility.getId());
//                facilities.add(actualFacility.get());
//            }
//        }
//
//        country.setFacilities(facilities);
        Country save = countryRepository.save(country);
        return toDto(save);
    }

    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAllByStatus(Boolean.TRUE);
        if(!countries.isEmpty()){
            return toDtoList(countries);
        }
        throw new RecordNotFoundException(String.format("There is no country present"));
    }

    public CountryDto getCountryById(Long id) {
        Optional<Country> country = countryRepository.findByIdAndStatus(id,Boolean.TRUE);
        if(country.isPresent()){
            return toDto(country.get());
        }
        throw new RecordNotFoundException(String.format("Country not found by this Id %d", id));
    }

    public CountryDto updateCountry(Long id, CountryDto countryDto) {
        Optional<Country> existingCountryOptional = countryRepository.findById(id);
        if (existingCountryOptional.isPresent()) {
            Country existingCountry = existingCountryOptional.get();

            // Check if a country with the same name already exists (excluding the current country being updated)
            if (countryRepository.existsByNameAndIdNot(countryDto.getName(), id)) {
                throw new RecordNotFoundException("A country with the same name already exists.");
            }

            existingCountry.setName(countryDto.getName());
            existingCountry.setStatus(Boolean.TRUE);
            return toDto(countryRepository.save(existingCountry));
        } else {
            throw new RecordNotFoundException(String.format("Country not found by this Id %d", id));
        }
    }

    public ApiResponse deleteCountryById(Long id){
        Optional<Country> country = countryRepository.findById(id);
        if(country.isPresent()){
            country.get().setStatus(Boolean.FALSE);
            countryRepository.save(country.get());

            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }
        throw new RecordNotFoundException(String.format("Country not found by this Id %d", id));
    }

    public List<CountryDto> toDtoList(List<Country> countries) {
        return countries.stream().map(this::toDto).collect(Collectors.toList());
    }

    private CountryDto toDto(Country country) {
        return modelMapper.map(country, CountryDto.class);
    }


    private Country toEntity(CountryDto countryDto) {
        return modelMapper.map(countryDto, Country.class);
    }



}
