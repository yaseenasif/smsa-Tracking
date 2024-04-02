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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DomesticShipmentController {

    @Autowired
    DomesticShipmentService domesticShipmentService;
    @Autowired
    StorageService storageService;



    @PreAuthorize("hasAuthority('add-domesticShipment')")
    @PostMapping("/add-domestic-shipment/org-id/{oId}/des-id/{dId}")
    public ResponseEntity<DomesticShipmentDto> addShipment(@RequestBody DomesticShipmentDto domesticShipmentDto,@PathVariable Long oId,@PathVariable Long dId) throws IOException {
        return ResponseEntity.ok(domesticShipmentService.addShipment(domesticShipmentDto,oId,dId));
    }

    @PreAuthorize("hasAuthority('add-domesticAttachment')")
    @PostMapping("/add-attachments/{id}/{attachmentType}")
    public ResponseEntity<ApiResponse> addAttachments(@PathVariable Long id,@PathVariable String attachmentType,@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(domesticShipmentService.addAttachment(id,attachmentType,file));
    }

    @PreAuthorize("hasAuthority('delete-attachment')")
    @DeleteMapping("/delete-attachment/{id}")
    public ResponseEntity<ApiResponse> deleteAttachment(@PathVariable Long id) {
        return ResponseEntity.ok(domesticShipmentService.deleteAttachment(id));
    }

    @PreAuthorize("hasAuthority('download-domesticAttachment')")
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

    @PreAuthorize("hasAuthority('getAll-domesticShipment')")
    @GetMapping("/all-domestic-shipments")
    public ResponseEntity<Page<DomesticShipmentDto>> getAll(@RequestParam(value = "value",required = false) String value,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForDomesticShipment
                searchCriteriaForDomesticShipment = new ObjectMapper().readValue(value, SearchCriteriaForDomesticShipment.class);
        return ResponseEntity.ok(domesticShipmentService.getAll(searchCriteriaForDomesticShipment,page,size));
    }

    @PreAuthorize("hasAuthority('getById-domesticShipment')")
    @GetMapping("/domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(domesticShipmentService.getById(id));
    }

    @PreAuthorize("hasAuthority('update-domesticShipment')")
    @PutMapping("/edit-domestic-shipment/{id}/org-id/{oId}/des-id/{dId}")
    public ResponseEntity<DomesticShipmentDto> updateDomesticShipment(@PathVariable Long id,
                                                                      @RequestBody DomesticShipmentDto domesticShipmentDto,@PathVariable Long oId,@PathVariable Long dId){
        return ResponseEntity.ok(domesticShipmentService.updateDomesticShipment(id,domesticShipmentDto,oId,dId));
    }

    @PreAuthorize("hasAuthority('inbound-domesticShipment')")
    @GetMapping("/domestic-shipment/inbound")
    public ResponseEntity<Page<DomesticShipmentDto>> getInboundShipment(@RequestParam(value = "value",required = false) String value,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForSummary searchCriteriaForSummary = new ObjectMapper().readValue(value, SearchCriteriaForSummary.class);
        return ResponseEntity.ok(domesticShipmentService.getInboundShipment(searchCriteriaForSummary,page,size));
    }

    @PreAuthorize("hasAuthority('update-domesticShipment')")
    @PatchMapping("/update-domestic-shipment/{id}")
    public ResponseEntity<DomesticShipmentDto> update(@PathVariable Long id,@RequestBody DomesticShipmentDto domesticShipmentDto,@PathVariable Long oId,@PathVariable Long dId){
        return ResponseEntity.ok(domesticShipmentService.updateDomesticShipment(id,domesticShipmentDto,oId,dId));
    }

    @PreAuthorize("hasAuthority('delete-domesticShipment')")
    @DeleteMapping("/delete-domestic-shipment/{id}")
    public ResponseEntity<ApiResponse> deleteDomesticShipment(@PathVariable Long id){
        return ResponseEntity.ok(domesticShipmentService.deleteDomesticShipment(id));
    }

    @PreAuthorize("hasAuthority('dashboardCount-domesticShipment')")
    @GetMapping("/dashboard-domestic")
    public ResponseEntity<Map<String,Integer>> getDashboardData(@RequestParam Integer year){
        return ResponseEntity.ok(domesticShipmentService.getAllDashboardData(year));
    }

    @GetMapping("/low-and-high-volume-by-location-inbound-domestic")
    public ResponseEntity<Map<String,Integer>> lowAndHighVolumeWithLocationForInboundForDomestic(@RequestParam Integer year){
        return ResponseEntity.ok(domesticShipmentService.lowAndHighVolumeWithLocationForInboundForDomestic(year));
    }

    @GetMapping("/low-and-high-volume-by-location-outbound-domestic")
    public ResponseEntity<Map<String,Integer>> lowAndHighVolumeWithLocationForOutboundForDomestic(@RequestParam Integer year){
        return ResponseEntity.ok(domesticShipmentService.lowAndHighVolumeWithLocationForOutboundForDomestic(year));
    }

    @PreAuthorize("hasAuthority('lowToHighVolumeOutbound-domesticShipment')")
    @GetMapping("low-to-high-domestic-outbound-test")
    public ResponseEntity<Map<String,Map<String,Integer>>> getOutBoundForDomesticDashboardTest(@RequestParam Integer year){
        return ResponseEntity.ok(domesticShipmentService.getOutBoundForDashboardTest(year));
    }

}
