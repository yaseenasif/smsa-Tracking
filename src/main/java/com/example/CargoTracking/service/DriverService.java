package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DriverDto;
import com.example.CargoTracking.model.Driver;
import com.example.CargoTracking.repository.DriverRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<DriverDto> getActiveDrivers() {
        return toDtoList(driverRepository.getActiveLocations());
    }

    public DriverDto getById(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        if (driver.isPresent()){
            return toDto(driver.get());
        }
        throw new RuntimeException(String.format("Driver Not Found On this Id => %d",id));
    }

    public DriverDto deleteDriverById(Long id){
        Optional<Driver> driver = driverRepository.findById(id);

        if(driver.isPresent()){
            driver.get().setStatus(Boolean.FALSE);
            return toDto(driverRepository.save(driver.get()));
        }

        throw new RuntimeException("Record doesn't exist");
    }

    public DriverDto updateById(Long id, DriverDto driverDto) {
        Optional<Driver> driver = driverRepository.findById(id);

        if(driver.isPresent()){
            driver.get().setName(driverDto.getName());
            driver.get().setContactNumber(driverDto.getContactNumber());
            driver.get().setReferenceNumber(driverDto.getReferenceNumber());
            return toDto(driverRepository.save(driver.get()));
        }

        throw new RuntimeException(String.format("Driver Not Found by this Id => %d" , id));
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
        throw new RuntimeException(String.format("Driver Not Found by this Id => %d" , id));
    }


    public List<DriverDto> toDtoList(List<Driver> driver){
        return driver.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DriverDto toDto(Driver driver){
        return modelMapper.map(driver, DriverDto.class);
    }
}
