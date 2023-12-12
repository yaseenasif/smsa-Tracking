package com.example.CargoTracking.service;

import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Status;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    StatusRepository statusRepository;


    public Status addStatus(Status status) {
        status.setStatus(true);
        return statusRepository.save(status);
    }

    public List<Status> getAll() {
        return statusRepository.findByStatus(true);
    }

    public Status getById(Long id) {
        Optional<Status> status = statusRepository.findById(id);
        if (status.isPresent()) {
            return status.get();
        }
        throw new RecordNotFoundException(String.format("Status Not Found On this Id => %d", id));
    }

    public ApiResponse deleteById(Long id) {
        Optional<Status> vehicleType = statusRepository.findById(id);

        if (vehicleType.isPresent()) {
            vehicleType.get().setStatus(false);
           statusRepository.save(vehicleType.get());
            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }
        throw new RecordNotFoundException(String.format("Status not found by this id => %d",id));
    }


    public Status updateStatus(Long id, Status status) {
        Optional<Status> savedStatus = statusRepository.findById(id);
        if (savedStatus.isPresent()) {
            Status unSaveStatus = savedStatus.get();
            unSaveStatus.setName(status.getName());
            return statusRepository.save(unSaveStatus);
        }
        throw new RecordNotFoundException(String.format("Status not found by this id => %d",id));
    }
}
