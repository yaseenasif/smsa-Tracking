package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;
import java.util.List;

public interface FileMetaDataRepository extends JpaRepository<FileMetaData,Long> {
    FileMetaData findByFileName(String fileName);
    List<FileMetaData> findByDomesticShipment(DomesticShipment domesticShipment);
}
