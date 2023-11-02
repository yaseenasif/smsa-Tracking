package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetaDataRepository extends JpaRepository<FileMetaData,Long> {
}
