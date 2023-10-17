package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.InternationalShipmentHistoryDto;
import com.example.CargoTracking.service.InternationalShipmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShipmentHistoryController {

    @Autowired
    InternationalShipmentHistoryService shipmentHistoryService;


    public List<InternationalShipmentHistoryDto> getAll(){
        return null;
    }

}
