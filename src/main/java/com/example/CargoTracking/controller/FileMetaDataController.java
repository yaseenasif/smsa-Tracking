package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.FileMetaDataDto;
import com.example.CargoTracking.model.FileMetaData;
import com.example.CargoTracking.service.FileMetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FileMetaDataController {

    @Autowired
    FileMetaDataService metaDataService;

    @GetMapping("/file-meta-data-by-domestic-shipment/{id}")
    public ResponseEntity<List<FileMetaDataDto>> getFileMataDataByDomesticShipment(@PathVariable Long id){
        return ResponseEntity.ok(metaDataService.getFileMataDataByDomesticShipment(id));

    }

    @GetMapping("/file-meta-data-by-international-shipment/{id}")
    public ResponseEntity<List<FileMetaDataDto>> getFileMataDataByInternationalShipment(@PathVariable Long id){
        return ResponseEntity.ok(metaDataService.getFileMataDataByInternationalShipment(id));

    }
}
