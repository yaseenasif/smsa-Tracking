package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.ShipmentHistoryDto;
import com.example.CargoTracking.service.ShipmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShipmentHistoryController {

    @Autowired
    ShipmentHistoryService shipmentHistoryService;


    public List<ShipmentHistoryDto> getAll(){
        return null;
    }

}
