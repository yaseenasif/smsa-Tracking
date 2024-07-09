package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.VehicleDto;
import com.example.CargoTracking.model.Vehicle;
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

    public VehicleDto addType(VehicleDto vehicleTypeDto) {

        Vehicle vehicle = Vehicle.builder()
                .name(vehicleTypeDto.getName())
                .occupancy(vehicleTypeDto.getOccupancy())
                .vehicleNumber(vehicleTypeDto.getVehicleNumber())
                .status(Boolean.TRUE)
                .build();

        return toDto(vehicleTypeRepository.save(vehicle));
    }

    public List<VehicleDto> getActiveVehicles() {
        return toDtoList(vehicleTypeRepository.findByStatusTrue());
    }

    public VehicleDto getById(Long id) {
        Optional<Vehicle> vehicleType = vehicleTypeRepository.findById(id);
        if (vehicleType.isPresent()){
            return toDto(vehicleType.get());
        }
        throw new RuntimeException(String.format("Vehicle Type Not Found On this Id => %d",id));
    }

    public VehicleDto deleteById(Long id){
        Optional<Vehicle> vehicleType = vehicleTypeRepository.findById(id);

        if(vehicleType.isPresent()){
            vehicleType.get().setStatus(Boolean.FALSE);
            return toDto(vehicleTypeRepository.save(vehicleType.get()));
        }

        throw new RuntimeException("Record doesn't exist");
    }

    public VehicleDto getVehicleTypeByVehicleTypeName(String name){
        return  toDto(vehicleTypeRepository.findByName(name));
    }

    public VehicleDto updateById(Long id, VehicleDto vehicleDto) {
        Optional<Vehicle> vehicleType = vehicleTypeRepository.findById(id);

        if(vehicleType.isPresent()){
            vehicleType.get().setName(vehicleDto.getName());
            vehicleType.get().setOccupancy(vehicleDto.getOccupancy());
            vehicleType.get().setVehicleNumber(vehicleDto.getVehicleNumber());
            return toDto(vehicleTypeRepository.save(vehicleType.get()));
        }
        throw new RuntimeException(String.format("Vehicle Type Not Found by this Id => %d" , id));
    }

    public VehicleDto makeVehicleTypeActive(Long id) {
        Optional<Vehicle> vehicleType = vehicleTypeRepository.findById(id);
        if(vehicleType.isPresent()){
            if(vehicleType.get().isStatus()){
                throw new RuntimeException("Record is already Active");
            }
            vehicleType.get().setStatus(Boolean.TRUE);
            return toDto(vehicleTypeRepository.save(vehicleType.get()));
        }
        throw new RuntimeException(String.format("Vehicle Type Not Found by this Id => %d" , id));
    }

    public List<VehicleDto> toDtoList(List<Vehicle> vehicles){
        return vehicles.stream().map(this::toDto).collect(Collectors.toList());
    }

    public VehicleDto toDto(Vehicle vehicle){
        return modelMapper.map(vehicle, VehicleDto.class);
    }

}
