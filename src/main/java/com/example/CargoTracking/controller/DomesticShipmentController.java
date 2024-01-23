package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.DomesticShipmentService;
import com.example.CargoTracking.service.LocationService;
import com.example.CargoTracking.service.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class DomesticShipmentController {

    @Autowired
    DomesticShipmentService domesticShipmentService;
    @Autowired
    StorageService storageService;




    @PostMapping("/add-domestic-shipment")
    public ResponseEntity<DomesticShipmentDto> addShipment(@RequestBody DomesticShipmentDto domesticShipmentDto) throws IOException {
        return ResponseEntity.ok(domesticShipmentService.addShipment(domesticShipmentDto));
    }

    @PostMapping("/add-attachments/{id}/{attachmentType}")
    public ResponseEntity<ApiResponse> addAttachments(@PathVariable Long id,@PathVariable String attachmentType,@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(domesticShipmentService.addAttachment(id,attachmentType,file));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping("/all-domestic-shipments")
    public ResponseEntity<Page<DomesticShipmentDto>> getAll(@RequestParam(value = "value",required = false) String value,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForDomesticShipment
                searchCriteriaForDomesticShipment = new ObjectMapper().readValue(value, SearchCriteriaForDomesticShipment.class);
        return ResponseEntity.ok(domesticShipmentService.getAll(searchCriteriaForDomesticShipment,page,size));
    }


    @GetMapping("/domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(domesticShipmentService.getById(id));
    }

    @PutMapping("/edit-domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> updateDomesticShipment(@PathVariable Long id,
                                                                      @RequestBody DomesticShipmentDto domesticShipmentDto){
        return ResponseEntity.ok(domesticShipmentService.updateDomesticShipment(id,domesticShipmentDto));
    }

//    @GetMapping("/domestic-shipment-summery/all")
//    public ResponseEntity<Page<DomesticShipmentDto>> getAllForSummery(@RequestParam(value = "value",required = false) String value,
//                                                                         @RequestParam(defaultValue = "0") int page,
//                                                                         @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
//        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);
//        return ResponseEntity.ok(domesticShipmentService.getAllForSummery(searchCriteriaForSummary,page,size));
//    }
//
//    @GetMapping("/domestic-shipment/outbound")
//    public ResponseEntity<Page<DomesticShipmentDto>> getOutboundShipment(@RequestParam(value = "value",required = false) String value,
//                                                                         @RequestParam(defaultValue = "0") int page,
//                                                                         @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
//        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);
//        return ResponseEntity.ok(domesticShipmentService.getOutboundShipment(searchCriteriaForSummary,page,size));
//    }
//    @GetMapping("/domestic-shipment/inbound")
//    public ResponseEntity<Page<DomesticShipmentDto>> getInboundShipment(@RequestParam(value = "value",required = false) String value,
//                                                                        @RequestParam(defaultValue = "0") int page,
//                                                                        @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
//        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);
//        return ResponseEntity.ok(domesticShipmentService.getInboundShipment(searchCriteriaForSummary,page,size));
//    }
//
    @PatchMapping("/update-domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> update(@PathVariable Long id,@RequestBody DomesticShipmentDto domesticShipmentDto){
        return ResponseEntity.ok(domesticShipmentService.updateDomesticShipment(id,domesticShipmentDto));
    }

    @DeleteMapping("/delete-domestic-shipment/{id}")
    public ResponseEntity<ApiResponse> deleteDomesticShipment(@PathVariable Long id){
        return ResponseEntity.ok(domesticShipmentService.deleteDomesticShipment(id));
    }

}
