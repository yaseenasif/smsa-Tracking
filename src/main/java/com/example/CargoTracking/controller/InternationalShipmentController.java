package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.*;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.dto.InternationalShipmentDto;
import com.example.CargoTracking.dto.SendEmailAddressForOutlookManual;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.InternationalShipmentService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class  InternationalShipmentController {
    @Autowired
    InternationalShipmentService internationalShipmentService;
    @Autowired
    StorageService storageService;

    @PreAuthorize("hasAuthority('add-internationalShipment')")
    @PostMapping("/add-international-shipment/org-id/{oId}/des-id/{dId}")
    public ResponseEntity<InternationalShipmentDto> saveInternationalShipment(@RequestBody InternationalShipmentDto internationalShipmentDto,@PathVariable Long oId,@PathVariable Long dId){
        return ResponseEntity.ok(internationalShipmentService.addShipment(internationalShipmentDto,oId,dId));
    }

    @PreAuthorize("hasAuthority('add-internationalAttachment')")
    @PostMapping("/add-international-attachments/{id}/{attachmentType}")
    public ResponseEntity<ApiResponse> addAttachments(@PathVariable Long id,@PathVariable String attachmentType, @RequestParam("file") List<MultipartFile> file) throws IOException {
        return ResponseEntity.ok(internationalShipmentService.addAttachment(id,attachmentType,file));
    }

    @PreAuthorize("hasAuthority('download-internationalAttachment')")
    @GetMapping("/international-download/{fileName}")
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

    @PreAuthorize("hasAuthority('getAll-internationalShipment')")
    @GetMapping("/all-international-shipments")
    public ResponseEntity<List<InternationalShipmentDto>> getAll(){
        return ResponseEntity.ok(internationalShipmentService.getAll());
    }

    @PreAuthorize("hasAuthority('getAll-internationalShipmentAir')")
    @GetMapping("/international-shipments-by-user-air")
    public ResponseEntity<Page<InternationalShipmentDto>> getAllInternationalShipmentByUserForAir(@RequestParam(value = "value",required = false) String value,
                                                                                                  @RequestParam(defaultValue = "0") int page,
                                                                                                  @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalShipment
                searchCriteriaForInternationalShipment = new ObjectMapper().readValue(value, SearchCriteriaForInternationalShipment.class);
        return ResponseEntity.ok(internationalShipmentService.getAllByUserAndForAir(searchCriteriaForInternationalShipment,page,size));
    }

    @PreAuthorize("hasAuthority('getAll-internationalShipmentRoad')")
    @GetMapping("/international-shipments-by-user-road")
    public ResponseEntity<Page<InternationalShipmentDto>> getAllInternationalShipmentByUserForRoad(@RequestParam(value = "value",required = false) String value,
                                                                                                   @RequestParam(defaultValue = "0") int page,
                                                                                                   @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalShipment
                searchCriteriaForInternationalShipment = new ObjectMapper().readValue(value, SearchCriteriaForInternationalShipment.class);
        return ResponseEntity.ok(internationalShipmentService.getAllByUserAndForRoad(searchCriteriaForInternationalShipment,page,size));
    }

    @PreAuthorize("hasAuthority('getById-internationalShipment')")
    @GetMapping("/international-shipment/{id}")
    public ResponseEntity<InternationalShipmentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(internationalShipmentService.getById(id));
    }

    @GetMapping("/international-shipment-email-address/{id}")
    public ResponseEntity<SendEmailAddressForOutlookManual> getInternationalShipmentEmailAddressById(@PathVariable Long id){
        return ResponseEntity.ok(internationalShipmentService.getInternationalShipmentEmailAddressById(id));
    }

    @PreAuthorize("hasAuthority('get-internationalInboundSummaryAir')")
    @GetMapping("/international-inbound-summery-air")
    public ResponseEntity<Page<InternationalShipmentDto>> getInternationalInBoundSummeryForAir(@RequestParam(value = "value",required = false) String value,
                                                                                               @RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(internationalShipmentService.getInternationalInBoundSummeryForAir(searchCriteriaForInternationalSummary,page,size));
    }

    @PreAuthorize("hasAuthority('get-internationalInboundSummaryRoad')")
    @GetMapping("/international-inbound-summery-road")
    public ResponseEntity<Page<InternationalShipmentDto>> getInternationalInBoundSummeryForRoad(@RequestParam(value = "value",required = false) String value,
                                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalSummary
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummary.class);
        return ResponseEntity.ok(internationalShipmentService.getInternationalInBoundSummeryForRoad(searchCriteriaForInternationalSummary,page,size));
    }

    @PreAuthorize("hasAuthority('get-internationalInboundSummaryAir')")
    @GetMapping("/international-inbound-summery-air")
    public ResponseEntity<Page<InternationalShipmentDto>> getInternationalOutBoundSummeryForAir(@RequestParam(value = "value",required = false) String value,
                                                                                               @RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalSummaryOutbound
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummaryOutbound.class);
        return ResponseEntity.ok(internationalShipmentService.getInternationalOutBoundSummeryForAir(searchCriteriaForInternationalSummary,page,size));
    }

    @PreAuthorize("hasAuthority('get-internationalInboundSummaryRoad')")
    @GetMapping("/international-inbound-summery-road")
    public ResponseEntity<Page<InternationalShipmentDto>> getInternationalOutBoundSummeryForRoad(@RequestParam(value = "value",required = false) String value,
                                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        SearchCriteriaForInternationalSummaryOutbound
                searchCriteriaForInternationalSummary = new ObjectMapper().readValue(value, SearchCriteriaForInternationalSummaryOutbound.class);
        return ResponseEntity.ok(internationalShipmentService.getInternationalOutBoundSummeryForRoad(searchCriteriaForInternationalSummary,page,size));
    }

    @PreAuthorize("hasAuthority('update-internationalShipment')")
    @PatchMapping("/update-international-shipment/{id}/org-id/{oId}/des-id/{dId}")
    public ResponseEntity<InternationalShipmentDto> update(@PathVariable Long id, @RequestBody InternationalShipmentDto internationalShipmentDto,@PathVariable Long oId,@PathVariable Long dId){
        return ResponseEntity.ok(internationalShipmentService.updateInternationalShipment(id,internationalShipmentDto,oId,dId));
    }

    @PreAuthorize("hasAuthority('delete-internationalShipment')")
    @DeleteMapping("/delete-international-shipment/{id}")
    public ResponseEntity<ApiResponse> deleteDomesticShipment(@PathVariable Long id){
        return ResponseEntity.ok(internationalShipmentService.deleteInternationalShipment(id));
    }

    @PreAuthorize("hasAuthority('get-internationalDashboardCountAir')")
    @GetMapping("/dashboard-international-count-air")
    public ResponseEntity<Map<String,Integer>> getDashboardDataCountForAir(@RequestParam Integer year){
        return ResponseEntity.ok(internationalShipmentService.getAllDashboardDataCountForAir(year));
    }

    @PreAuthorize("hasAuthority('get-internationalDashboardCountRoad')")
    @GetMapping("/dashboard-international-count-road")
    public ResponseEntity<Map<String,Integer>> getDashboardDataCountForRoad(@RequestParam Integer year){
        return ResponseEntity.ok(internationalShipmentService.getAllDashboardDataCountForRoad(year));
    }

//    @GetMapping("/low-and-high-volume-by-location-inbound-international-air")
//    public ResponseEntity<Map<String,Integer>> lowAndHighVolumeWithLocationForInboundForInternationalAir(@RequestParam Integer year){
//        return ResponseEntity.ok(internationalShipmentService.lowAndHighVolumeWithLocationForInboundForInternationalAir(year));
//    }
//
//    @GetMapping("/low-and-high-volume-by-location-inbound-international-road")
//    public ResponseEntity<Map<String,Integer>> lowAndHighVolumeWithLocationForInboundForInternationalRoad(@RequestParam Integer year){
//        return ResponseEntity.ok(internationalShipmentService.lowAndHighVolumeWithLocationForInboundForInternationalRoad(year));
//    }
//
//    @GetMapping("/low-and-high-volume-by-location-outbound-international-air")
//    public ResponseEntity<Map<String,Integer>> lowAndHighVolumeWithLocationForOutboundForInternationalAir(@RequestParam Integer year){
//        return ResponseEntity.ok(internationalShipmentService.lowAndHighVolumeWithLocationForOutboundForInternationalAir(year));
//    }
//
//    @GetMapping("/low-and-high-volume-by-location-outbound-international-road")
//    public ResponseEntity<Map<String,Integer>> lowAndHighVolumeWithLocationForOutboundForInternationalRoad(@RequestParam Integer year){
//        return ResponseEntity.ok(internationalShipmentService.lowAndHighVolumeWithLocationForOutboundForInternationalRoad(year));
//    }

    @PreAuthorize("hasAuthority('lowToHighVolumeOutbound-internationalShipmentAir')")
    @GetMapping("low-to-high-international-air-outbound-test")
    public ResponseEntity<Map<String,Map<String,Integer>>> getOutBoundForInternationalAirDashboardTest(@RequestParam Integer year){
        return ResponseEntity.ok(internationalShipmentService.getOutBoundForInternationalAirDashboardTest(year));
    }

    @PreAuthorize("hasAuthority('lowToHighVolumeOutbound-internationalShipmentRoad')")
    @GetMapping("low-to-high-international-road-outbound-test")
    public ResponseEntity<Map<String,Map<String,Integer>>> getOutBoundForInternationalRoadDashboardTest(@RequestParam Integer year){
        return ResponseEntity.ok(internationalShipmentService.getOutBoundForInternationalRoadDashboardTest(year));
    }

}
