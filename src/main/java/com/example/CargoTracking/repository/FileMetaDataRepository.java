package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.FileMetaData;
import com.example.CargoTracking.model.InternationalShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData,Long> {
    FileMetaData findByFileName(String fileName);
    List<FileMetaData> findByDomesticShipment(DomesticShipment domesticShipment);
    List<FileMetaData> findByInternationalShipment(InternationalShipment domesticShipment);

}
