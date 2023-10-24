package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DriverDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Driver;
import com.example.CargoTracking.repository.DriverRepository;
import com.example.CargoTracking.specification.DriverSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;
    @Autowired
    ModelMapper modelMapper;

    public DriverDto addDriver(DriverDto driverDto) {

        Driver driver = Driver.builder()
                .name(driverDto.getName())
                .contactNumber(driverDto.getContactNumber())
                .referenceNumber(driverDto.getReferenceNumber())
                .status(Boolean.TRUE)
                .build();

        return toDto(driverRepository.save(driver));
    }

    public Page<DriverDto> getActiveDrivers(String searchCriteria, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> drivers;
        if(searchCriteria == null){
            drivers = driverRepository.findAll(pageable);
        }else {

            Specification<Driver> driverSpecification = DriverSpecification.getSearchSpecification(searchCriteria);
            drivers = driverRepository.findAll(driverSpecification,pageable);

        }
        Page<DriverDto> driverDto = drivers.map(entity->toDto(entity));
        return driverDto;
    }

    public DriverDto getById(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        if (driver.isPresent()){
            return toDto(driver.get());
        }
        throw new RecordNotFoundException(String.format("Driver Not Found On this Id => %d",id));
    }

    public DriverDto deleteDriverById(Long id){
        Optional<Driver> driver = driverRepository.findById(id);

        if(driver.isPresent()){
            driver.get().setStatus(Boolean.FALSE);
            return toDto(driverRepository.save(driver.get()));
        }

        throw new RecordNotFoundException(String.format("Driver Not Found On this Id => %d",id));
    }

    public DriverDto updateById(Long id, DriverDto driverDto) {
        Optional<Driver> driver = driverRepository.findById(id);

        if(driver.isPresent()){
            driver.get().setName(driverDto.getName());
            driver.get().setContactNumber(driverDto.getContactNumber());
            driver.get().setReferenceNumber(driverDto.getReferenceNumber());
            return toDto(driverRepository.save(driver.get()));
        }

        throw new RecordNotFoundException(String.format("Driver Not Found On this Id => %d",id));
    }

    public DriverDto makeDriverActive(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        if(driver.isPresent()){
            if(driver.get().isStatus()){
                throw new RuntimeException("Record is already Active");
            }
            driver.get().setStatus(Boolean.TRUE);
            return toDto(driverRepository.save(driver.get()));
        }
        throw new RecordNotFoundException(String.format("Driver Not Found On this Id => %d",id));
    }


    public List<DriverDto> toDtoList(List<Driver> driver){
        return driver.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DriverDto toDto(Driver driver){
        return modelMapper.map(driver, DriverDto.class);
    }
}
