package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.InternationalShipmentHistoryDto;
import com.example.CargoTracking.model.InternationalShipmentHistory;
import com.example.CargoTracking.repository.InternationalShipmentHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternationalShipmentHistoryService {

    @Autowired
    InternationalShipmentHistoryRepository internationalShipmentHistoryRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<InternationalShipmentHistoryDto> getAll(){
        return toDtoList(internationalShipmentHistoryRepository.findAll());
    }

    public List<InternationalShipmentHistoryDto> toDtoList(List<InternationalShipmentHistory> shipmentHistory){
        return shipmentHistory.stream().map(this::toDto).collect(Collectors.toList());
    }

    private InternationalShipmentHistoryDto toDto(InternationalShipmentHistory shipmentHistory) {
        return modelMapper.map(shipmentHistory , InternationalShipmentHistoryDto.class);
    }

    private InternationalShipmentHistory toEntity(InternationalShipmentHistoryDto shipmentHistoryDto){
        return modelMapper.map(shipmentHistoryDto , InternationalShipmentHistory.class);
    }

}
