package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.dto.DomesticShipmentHistoryDto;
import com.example.CargoTracking.dto.DriverDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.DomesticShipmentHistory;
import com.example.CargoTracking.model.Driver;
import com.example.CargoTracking.repository.DomesticShipmentHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomesticShipmentHistoryService {

    @Autowired
    DomesticShipmentHistoryRepository domesticShipmentHistoryRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<DomesticShipmentHistoryDto> getDomesticShipmentHistoryByDomesticShipmentId(Long id) {
        List<DomesticShipmentHistory> domesticShipmentHistory = domesticShipmentHistoryRepository.findByDomesticShipmentId(id);
        if(!domesticShipmentHistory.isEmpty()){
            List<DomesticShipmentHistoryDto> domesticShipmentHistoryDtoList = toDtoList(domesticShipmentHistory);
            return domesticShipmentHistoryDtoList;
        }
        throw new RecordNotFoundException(String.format("Domestic Shipment History not found by this id => %d",id));
    }

    public List<DomesticShipmentHistoryDto> toDtoList(List<DomesticShipmentHistory> domesticShipmentHistories){
        return domesticShipmentHistories.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DomesticShipmentHistoryDto toDto(DomesticShipmentHistory domesticShipmentHistory){
        return modelMapper.map(domesticShipmentHistory, DomesticShipmentHistoryDto.class);
    }
}
