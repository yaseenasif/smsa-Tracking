package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.ShipmentModeDto;
import com.example.CargoTracking.model.ShipmentMode;
import com.example.CargoTracking.repository.ShipmentModeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipmentModeService {

    @Autowired
    ShipmentModeRepository shipmentModeRepository;
    @Autowired
    ModelMapper modelMapper;
    public ShipmentModeDto addMode(ShipmentModeDto shipmentModeDto) {

        ShipmentMode shipmentMode = ShipmentMode.builder()
                .name(shipmentModeDto.getName())
                .status(Boolean.TRUE)
                .build();

        return toDto(shipmentModeRepository.save(shipmentMode));
    }

    public List<ShipmentModeDto> getActiveShipments() {
        return toDtoList(shipmentModeRepository.getActiveShipments());
    }

    public ShipmentModeDto getById(Long id) {
        Optional<ShipmentMode> shipmentMode = shipmentModeRepository.findById(id);
        if (shipmentMode.isPresent()){
            return toDto(shipmentMode.get());
        }
        throw new RuntimeException(String.format("Shipment Mode Not Found On this Id => %d",id));
    }

    public ShipmentModeDto deleteModeById(Long id){
        Optional<ShipmentMode> shipmentMode = shipmentModeRepository.findById(id);

        if(shipmentMode.isPresent()){
            shipmentMode.get().setStatus(Boolean.FALSE);
            return toDto(shipmentModeRepository.save(shipmentMode.get()));
        }

        throw new RuntimeException("Record doesn't exist");
    }

    public ShipmentModeDto updateById(Long id, ShipmentModeDto shipmentModeDto) {
        Optional<ShipmentMode> shipmentMode = shipmentModeRepository.findById(id);

        if(shipmentMode.isPresent()){
            shipmentMode.get().setName(shipmentModeDto.getName());
            return toDto(shipmentModeRepository.save(shipmentMode.get()));
        }

        throw new RuntimeException(String.format("Shipment Mode Not Found by this Id => %d" , id));
    }

    public ShipmentModeDto makeShipmentModeActive(Long id) {
        Optional<ShipmentMode> shipmentMode = shipmentModeRepository.findById(id);
        if(shipmentMode.isPresent()){
            if(shipmentMode.get().isStatus()){
                throw new RuntimeException("Record is already Active");
            }
            shipmentMode.get().setStatus(Boolean.TRUE);
            return toDto(shipmentModeRepository.save(shipmentMode.get()));
        }
        throw new RuntimeException(String.format("Shipment Mode Not Found by this Id => %d" , id));
    }


    public List<ShipmentModeDto> toDtoList(List<ShipmentMode> shipmentModes){
        return shipmentModes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ShipmentModeDto toDto(ShipmentMode shipmentMode){
        return modelMapper.map(shipmentMode, ShipmentModeDto.class);
    }

}
