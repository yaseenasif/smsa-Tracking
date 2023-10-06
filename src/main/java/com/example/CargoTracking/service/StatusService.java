package com.example.CargoTracking.service;

import com.example.CargoTracking.model.Status;
import com.example.CargoTracking.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    StatusRepository statusRepository;


    public Status addStatus(Status status) {
        return statusRepository.save(status);
    }

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public Status getById(Long id) {
        Optional<Status> status = statusRepository.findById(id);
        if (status.isPresent()) {
            return status.get();
        }
        throw new RuntimeException(String.format("Status Not Found On this Id => %d", id));
    }

    public void deleteById(Long id) {
        Optional<Status> vehicleType = statusRepository.findById(id);

        if (vehicleType.isPresent()) {
           statusRepository.deleteById(id);
        }
        throw new RuntimeException("Record doesn't exist");
    }


}
