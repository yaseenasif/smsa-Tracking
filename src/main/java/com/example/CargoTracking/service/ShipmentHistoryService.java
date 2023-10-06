package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.ShipmentHistoryDto;
import com.example.CargoTracking.model.ShipmentHistory;
import com.example.CargoTracking.repository.ShipmentHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentHistoryService {

    @Autowired
    ShipmentHistoryRepository shipmentHistoryRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ShipmentHistoryDto> getAll(){
        return toDtoList(shipmentHistoryRepository.findAll());
    }

    public List<ShipmentHistoryDto> toDtoList(List<ShipmentHistory> shipmentHistory){
        return shipmentHistory.stream().map(this::toDto).collect(Collectors.toList());
    }

    private ShipmentHistoryDto toDto(ShipmentHistory shipmentHistory) {
        return modelMapper.map(shipmentHistory , ShipmentHistoryDto.class);
    }

    private ShipmentHistory toEntity(ShipmentHistoryDto shipmentHistoryDto){
        return modelMapper.map(shipmentHistoryDto , ShipmentHistory.class);
    }

}
