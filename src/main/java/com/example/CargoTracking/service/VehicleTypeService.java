package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.VehicleTypeDto;
import com.example.CargoTracking.model.VehicleType;
import com.example.CargoTracking.repository.VehicleTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleTypeService {

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    ModelMapper modelMapper;

    public VehicleTypeDto addType(VehicleTypeDto vehicleTypeDto) {

        VehicleType vehicleType= VehicleType.builder()
                .name(vehicleTypeDto.getName())
                .status(Boolean.TRUE)
                .build();

        return toDto(vehicleTypeRepository.save(vehicleType));
    }

    public List<VehicleTypeDto> getActiveVehicles() {
        return toDtoList(vehicleTypeRepository.getActiveVehicles());
    }

    public VehicleTypeDto getById(Long id) {
        Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(id);
        if (vehicleType.isPresent()){
            return toDto(vehicleType.get());
        }
        throw new RuntimeException(String.format("Vehicle Type Not Found On this Id => %d",id));
    }

    public VehicleTypeDto deleteById(Long id){
        Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(id);

        if(vehicleType.isPresent()){
            vehicleType.get().setStatus(Boolean.FALSE);
            return toDto(vehicleTypeRepository.save(vehicleType.get()));
        }

        throw new RuntimeException("Record doesn't exist");
    }

    public VehicleTypeDto updateById(Long id, VehicleTypeDto vehicleTypeDto) {
        Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(id);

        if(vehicleType.isPresent()){
            vehicleType.get().setName(vehicleTypeDto.getName());
            return toDto(vehicleTypeRepository.save(vehicleType.get()));
        }
        throw new RuntimeException(String.format("Vehicle Type Not Found by this Id => %d" , id));
    }

    public VehicleTypeDto makeVehicleTypeActive(Long id) {
        Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(id);
        if(vehicleType.isPresent()){
            if(vehicleType.get().isStatus()){
                throw new RuntimeException("Record is already Active");
            }
            vehicleType.get().setStatus(Boolean.TRUE);
            return toDto(vehicleTypeRepository.save(vehicleType.get()));
        }
        throw new RuntimeException(String.format("Vehicle Type Not Found by this Id => %d" , id));
    }

    public List<VehicleTypeDto> toDtoList(List<VehicleType> vehicleTypes){
        return vehicleTypes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public VehicleTypeDto toDto(VehicleType vehicleType){
        return modelMapper.map(vehicleType, VehicleTypeDto.class);
    }

}
