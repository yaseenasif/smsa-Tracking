package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DriverDto;
import com.example.CargoTracking.dto.FileMetaDataDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.Driver;
import com.example.CargoTracking.model.FileMetaData;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.repository.DomesticShipmentRepository;
import com.example.CargoTracking.repository.FileMetaDataRepository;
import com.example.CargoTracking.repository.InternationalShipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileMetaDataService {
    @Autowired
    FileMetaDataRepository fileMetaDataRepository;
    @Autowired
    DomesticShipmentRepository domesticShipmentRepository;
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<FileMetaDataDto> getFileMataDataByDomesticShipment(Long id) {
        Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);
        List<FileMetaData> fileMetaDataByDomesticShipment = fileMetaDataRepository.findByDomesticShipment(domesticShipment.get());
        if(!fileMetaDataByDomesticShipment.isEmpty()){
            return toDtoList(fileMetaDataByDomesticShipment);
        }else{
            throw new RecordNotFoundException(String.format("No data found against this domestic shipment id => %d",id));
        }

    }
    public List<FileMetaDataDto> getFileMataDataByInternationalShipment(Long id) {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        List<FileMetaData> fileMetaDataByDomesticShipment = fileMetaDataRepository.findByInternationalShipment(internationalShipment.get());
        if(!fileMetaDataByDomesticShipment.isEmpty()){
            return toDtoList(fileMetaDataByDomesticShipment);
        }else{
            throw new RecordNotFoundException(String.format("No data found against this international shipment id => %d",id));
        }

    }

    public List<FileMetaDataDto> toDtoList(List<FileMetaData> fileMetaData){
        return fileMetaData.stream().map(this::toDto).collect(Collectors.toList());
    }

    public FileMetaDataDto toDto(FileMetaData fileMetaData){
        return modelMapper.map(fileMetaData, FileMetaDataDto.class);
    }
}
