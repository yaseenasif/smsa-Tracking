package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.*;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.model.InternationalShipmentHistory;
import com.example.CargoTracking.repository.DomesticRouteRepository;
import com.example.CargoTracking.repository.DomesticShipmentRepository;
import com.example.CargoTracking.repository.InternationalShipmentHistoryRepository;
import com.example.CargoTracking.repository.InternationalShipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.InputStreamResource;
import java.io.*;

import org.apache.poi.ss.usermodel.*;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.poi.ss.util.CellUtil.createCell;

@Service
@Slf4j
public class ExcelService {

    @Autowired
    ReportAndStatusService reportAndStatusService;
    @Autowired
    DomesticShipmentRepository domesticShipmentRepository;
    @Autowired
    VehicleTypeService vehicleTypeService;
    @Autowired
    InternationalShipmentHistoryRepository internationalShipmentHistoryRepository;
    @Autowired
    DomesticRouteRepository domesticRouteRepository;
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    @Value("${excel.file.location}")
    private String sampleFileLocalLocation;
    public Resource internationalAirReportPerformanceExcelDownload() {
        try{
            List<InternationalShipment> internationalShipmentList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Air");
            List<InternationalAirReportPerformance> intAirReportPerformance = new ArrayList<>();
            for (InternationalShipment internationalShipment: internationalShipmentList){
                InternationalAirReportPerformance internationalAirReportPerformance = new InternationalAirReportPerformance();
                internationalAirReportPerformance.setId(internationalShipment.getId());
                internationalAirReportPerformance.setPreAlertNumber(internationalShipment.getPreAlertNumber());
                internationalAirReportPerformance.setReferenceNumber(internationalShipment.getReferenceNumber());
                internationalAirReportPerformance.setOrigin(internationalShipment.getOriginLocation());
                internationalAirReportPerformance.setDestination(internationalShipment.getDestinationLocation());
                internationalAirReportPerformance.setRoute(internationalShipment.getRouteNumber());
                internationalAirReportPerformance.setFlight(internationalShipment.getFlightNumber());
                internationalAirReportPerformance.setActualTimeArrival(internationalShipment.getAta());
                internationalAirReportPerformance.setActualTimeDeparture(internationalShipment.getAtd());
                if(internationalShipment.getAta()!=null && internationalShipment.getAtd()!=null){
                    Duration durationForTransitTime = Duration.between(internationalShipment.getAta(), internationalShipment.getAtd());
                    internationalAirReportPerformance.setTotalTransitTime(durationForTransitTime.toHours());
                }
                List<InternationalShipmentHistory> internationalShipmentHistoryList = internationalShipmentHistoryRepository.findByInternationalShipmentId(internationalShipment.getId());
                for(InternationalShipmentHistory internationalShipmentHistory: internationalShipmentHistoryList){
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Cleared")) {
                        internationalAirReportPerformance.setCleared(internationalShipmentHistory.getProcessTime());
                        if(internationalShipmentHistory.getProcessTime()!=null && internationalShipment.getAtd()!=null){
                            Duration durationForLeadTime = Duration.between(internationalShipmentHistory.getProcessTime(), internationalShipment.getAtd());
                            internationalAirReportPerformance.setTotalLeadTime(durationForLeadTime.toHours());
                        }

                    }
                }
                intAirReportPerformance.add(internationalAirReportPerformance);
            }

            logger.info(sampleFileLocalLocation+"/internationalAirPerformance.xlsx");
            FileInputStream fileInputStream = new FileInputStream(sampleFileLocalLocation + "/internationalAirPerformance.xlsx");
            Workbook  newWorkBook = WorkbookFactory.create(fileInputStream);
            Sheet summarySheet= newWorkBook.getSheetAt(0);
            int rowCount = 1;

            CellStyle rightAlignedStyle = newWorkBook.createCellStyle();
            rightAlignedStyle.setAlignment(HorizontalAlignment.RIGHT);
            CellStyle style = newWorkBook.createCellStyle();

            for (InternationalAirReportPerformance internationalAirReportPerformance : intAirReportPerformance) {
                Row row = summarySheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, internationalAirReportPerformance.getId() != null ? internationalAirReportPerformance.getId().toString() : " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getPreAlertNumber() != null ? internationalAirReportPerformance.getPreAlertNumber(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getReferenceNumber() != null ? internationalAirReportPerformance.getReferenceNumber(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getOrigin() != null ? internationalAirReportPerformance.getOrigin(): " ", rightAlignedStyle);
                createCell(row, columnCount++, internationalAirReportPerformance.getDestination() != null ? internationalAirReportPerformance.getDestination(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getRoute() != null ? internationalAirReportPerformance.getRoute(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getFlight() != null ? internationalAirReportPerformance.getFlight(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getActualTimeDeparture() != null ? internationalAirReportPerformance.getActualTimeDeparture().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getActualTimeArrival() != null ? internationalAirReportPerformance.getActualTimeArrival().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getCleared() != null ? internationalAirReportPerformance.getCleared().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getTotalTransitTime() != null ? internationalAirReportPerformance.getTotalTransitTime().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportPerformance.getTotalLeadTime() != null ? internationalAirReportPerformance.getTotalLeadTime().toString(): " ", style);

            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newWorkBook.write(byteArrayOutputStream);

            byte[] workbookBytes = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(workbookBytes);

            Resource resource = new InputStreamResource(byteArrayInputStream);
            return resource;

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    public Resource internationalRoadReportPerformanceExcelDownload() {
        try{
            List<InternationalShipment> internationalShipmentList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Road");
            List<InternationalRoadReportPerformance> intRoadReportPerformance = new ArrayList<>();

            for(InternationalShipment internationalShipment: internationalShipmentList){
                InternationalRoadReportPerformance internationalRoadReportPerformance = new InternationalRoadReportPerformance();
                internationalRoadReportPerformance.setId(internationalShipment.getId());
                internationalRoadReportPerformance.setPreAlertNumber(internationalShipment.getPreAlertNumber());
                internationalRoadReportPerformance.setReferenceNumber(internationalShipment.getReferenceNumber());
                internationalRoadReportPerformance.setOrigin(internationalShipment.getOriginLocation());
                internationalRoadReportPerformance.setDestination(internationalShipment.getDestinationLocation());
                internationalRoadReportPerformance.setRoute(internationalShipment.getRouteNumber());
                internationalRoadReportPerformance.setVehicleType(internationalShipment.getVehicleType());
                internationalRoadReportPerformance.setActualTimeArrival(internationalShipment.getAta());
                internationalRoadReportPerformance.setActualTimeDeparture(internationalShipment.getAtd());
                if(internationalShipment.getAta()!=null && internationalShipment.getAtd()!=null){
                    Duration durationForTransitTime = Duration.between(internationalShipment.getAta(), internationalShipment.getAtd());
                    internationalRoadReportPerformance.setTotalTransitTime(durationForTransitTime.toHours());
                }
                List<InternationalShipmentHistory> internationalShipmentHistoryList = internationalShipmentHistoryRepository.findByInternationalShipmentId(internationalShipment.getId());
                for(InternationalShipmentHistory internationalShipmentHistory: internationalShipmentHistoryList){
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Offloaded at Destination")) {
                        internationalRoadReportPerformance.setOffloaded(internationalShipmentHistory.getProcessTime());
                        if(internationalShipmentHistory.getProcessTime()!=null && internationalShipment.getAtd()!=null){
                            Duration durationForLeadTime = Duration.between(internationalShipmentHistory.getProcessTime(), internationalShipment.getAtd());
                            internationalRoadReportPerformance.setTotalLeadTime(durationForLeadTime.toHours());
                        }
                    }
                }
                intRoadReportPerformance.add(internationalRoadReportPerformance);
            }

            FileInputStream fileInputStream = new FileInputStream(sampleFileLocalLocation + "/internationalRoadPerformance.xlsx");
            Workbook  newWorkBook = WorkbookFactory.create(fileInputStream);
            Sheet summarySheet= newWorkBook.getSheetAt(0);
            int rowCount = 1;

            CellStyle rightAlignedStyle = newWorkBook.createCellStyle();
            rightAlignedStyle.setAlignment(HorizontalAlignment.RIGHT);
            CellStyle style = newWorkBook.createCellStyle();

            for (InternationalRoadReportPerformance internationalRoadReportPerformance : intRoadReportPerformance) {
                Row row = summarySheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, internationalRoadReportPerformance.getId() != null ? internationalRoadReportPerformance.getId().toString() : " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getPreAlertNumber() != null ? internationalRoadReportPerformance.getPreAlertNumber(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getReferenceNumber() != null ? internationalRoadReportPerformance.getReferenceNumber(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getOrigin() != null ? internationalRoadReportPerformance.getOrigin(): " ", rightAlignedStyle);
                createCell(row, columnCount++, internationalRoadReportPerformance.getDestination() != null ? internationalRoadReportPerformance.getDestination(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getRoute() != null ? internationalRoadReportPerformance.getRoute(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getVehicleType() != null ? internationalRoadReportPerformance.getVehicleType(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getActualTimeDeparture() != null ? internationalRoadReportPerformance.getActualTimeDeparture().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getActualTimeArrival() != null ? internationalRoadReportPerformance.getActualTimeArrival().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getOffloaded() != null ? internationalRoadReportPerformance.getOffloaded().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getTotalTransitTime() != null ? internationalRoadReportPerformance.getTotalTransitTime().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportPerformance.getTotalLeadTime() != null ? internationalRoadReportPerformance.getTotalLeadTime().toString(): " ", style);

            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newWorkBook.write(byteArrayOutputStream);

            byte[] workbookBytes = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(workbookBytes);

            Resource resource = new InputStreamResource(byteArrayInputStream);
            return resource;

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    public Resource internationalAirReportStatusExcelDownload() {
        try{
            List<InternationalShipment> internationalShipmentsList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Air");
            List<InternationalAirReportStatusDto> intAirReportStatus = new ArrayList<>();
            for (InternationalShipment internationalShipment : internationalShipmentsList) {
                InternationalAirReportStatusDto internationalAirReportStatusDto = new InternationalAirReportStatusDto();
                internationalAirReportStatusDto.setId(internationalShipment.getId());
                internationalAirReportStatusDto.setPreAlertNumber(internationalShipment.getPreAlertNumber());
                internationalAirReportStatusDto.setReferenceNumber(internationalShipment.getReferenceNumber());
                internationalAirReportStatusDto.setOrigin(internationalShipment.getOriginLocation());
                internationalAirReportStatusDto.setDestination(internationalShipment.getDestinationLocation());
                internationalAirReportStatusDto.setRoute(internationalShipment.getRouteNumber());
                internationalAirReportStatusDto.setFlightNumber(internationalShipment.getFlightNumber());
                internationalAirReportStatusDto.setShipments(internationalShipment.getNumberOfShipments());
                internationalAirReportStatusDto.setBags(internationalShipment.getNumberOfBags());
                internationalAirReportStatusDto.setEtd(internationalShipment.getEtd());
                internationalAirReportStatusDto.setAtd(internationalShipment.getAtd());
                internationalAirReportStatusDto.setEta(internationalShipment.getEta());
                internationalAirReportStatusDto.setAta(internationalShipment.getAta());
                if(internationalShipment.getEtd()!=null && internationalShipment.getEta()!=null){
                    Duration durationForEtdVsEta = Duration.between(internationalShipment.getEtd(), internationalShipment.getEta());
                    internationalAirReportStatusDto.setEtdVsEta(durationForEtdVsEta.toHours());
                }
                if(internationalShipment.getEta()!=null && internationalShipment.getAta()!=null){
                    Duration durationForEtaVSAta = Duration.between(internationalShipment.getEta(), internationalShipment.getAta());
                    internationalAirReportStatusDto.setEtaVSAta(durationForEtaVSAta.toHours());
                }

                internationalAirReportStatusDto.setRemarks(internationalShipment.getRemarks());
                List<InternationalShipmentHistory> internationalShipmentHistoryList = internationalShipmentHistoryRepository.findByInternationalShipmentId(internationalShipment.getId());
                for (InternationalShipmentHistory internationalShipmentHistory : internationalShipmentHistoryList) {
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Created")) {
                        internationalAirReportStatusDto.setCreated(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Offloaded from Aircraft")) {
                        internationalAirReportStatusDto.setOffLoadedFromAircraft(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Flight Delayed")) {
                        internationalAirReportStatusDto.setFlightDelayed(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Cleared")) {
                        internationalAirReportStatusDto.setCleared(internationalShipmentHistory.getProcessTime());
                        if(internationalShipment.getAtd()!=null && internationalShipmentHistory.getProcessTime()!=null){
                            Duration duration = Duration.between(internationalShipmentHistory.getProcessTime(), internationalShipment.getAtd());
                            internationalAirReportStatusDto.setLeadTime(duration.toHours());
                        }

                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Not Arrived as planned")) {
                        internationalAirReportStatusDto.setNotArrivedAsPlanned(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("In progress")) {
                        internationalAirReportStatusDto.setInProgress(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Held in Customs")) {
                        internationalAirReportStatusDto.setHeldInCustoms(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Awaiting payment")) {
                        internationalAirReportStatusDto.setAwaitingPayments(internationalShipmentHistory.getProcessTime());
                    }

                }
                intAirReportStatus.add(internationalAirReportStatusDto);

            }

            FileInputStream fileInputStream = new FileInputStream(sampleFileLocalLocation + "/internationalAirStatus.xlsx");
            Workbook  newWorkBook = WorkbookFactory.create(fileInputStream);
            Sheet summarySheet= newWorkBook.getSheetAt(0);
            int rowCount = 1;

            CellStyle rightAlignedStyle = newWorkBook.createCellStyle();
            rightAlignedStyle.setAlignment(HorizontalAlignment.RIGHT);
            CellStyle style = newWorkBook.createCellStyle();

            for (InternationalAirReportStatusDto internationalAirReportStatusDto : intAirReportStatus) {
                Row row = summarySheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, internationalAirReportStatusDto.getId() != null ? internationalAirReportStatusDto.getId().toString() : " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getPreAlertNumber() != null ? internationalAirReportStatusDto.getPreAlertNumber(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getReferenceNumber() != null ? internationalAirReportStatusDto.getReferenceNumber(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getOrigin() != null ? internationalAirReportStatusDto.getOrigin(): " ", rightAlignedStyle);
                createCell(row, columnCount++, internationalAirReportStatusDto.getDestination() != null ? internationalAirReportStatusDto.getDestination(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getRoute() != null ? internationalAirReportStatusDto.getRoute(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getFlightNumber() != null ? internationalAirReportStatusDto.getFlightNumber(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getShipments() != null ? internationalAirReportStatusDto.getShipments().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getBags() != null ? internationalAirReportStatusDto.getBags().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getEtd() != null ? internationalAirReportStatusDto.getEtd().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getAtd() != null ? internationalAirReportStatusDto.getAtd().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getEta() != null ? internationalAirReportStatusDto.getEta().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getAta() != null ? internationalAirReportStatusDto.getAta().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getCreated() != null ? internationalAirReportStatusDto.getCreated().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getOffLoadedFromAircraft() != null ? internationalAirReportStatusDto.getOffLoadedFromAircraft().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getFlightDelayed() != null ? internationalAirReportStatusDto.getFlightDelayed().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getNotArrivedAsPlanned() != null ? internationalAirReportStatusDto.getNotArrivedAsPlanned().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getInProgress() != null ? internationalAirReportStatusDto.getInProgress().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getHeldInCustoms() != null ? internationalAirReportStatusDto.getHeldInCustoms().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getAwaitingPayments() != null ? internationalAirReportStatusDto.getAwaitingPayments().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getCleared() != null ? internationalAirReportStatusDto.getCleared().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getEtdVsEta() != null ? internationalAirReportStatusDto.getEtdVsEta().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getEtaVSAta() != null ? internationalAirReportStatusDto.getEtaVSAta().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getLeadTime() != null ? internationalAirReportStatusDto.getLeadTime().toString(): " ", style);
                createCell(row, columnCount++, internationalAirReportStatusDto.getRemarks() != null ? internationalAirReportStatusDto.getRemarks().toString(): " ", style);

            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newWorkBook.write(byteArrayOutputStream);

            byte[] workbookBytes = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(workbookBytes);

            Resource resource = new InputStreamResource(byteArrayInputStream);
            return resource;

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    public Resource internationalRoadReportStatusExcelDownload() {
        try{
            List<InternationalShipment> internationalShipmentList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Road");
            List<InternationalRoadReportStatusDto> intRoadReportStatus = new ArrayList<>();
            for (InternationalShipment internationalShipment : internationalShipmentList) {
                InternationalRoadReportStatusDto internationalRoadReportStatusDto = new InternationalRoadReportStatusDto();
                internationalRoadReportStatusDto.setId(internationalShipment.getId());
                internationalRoadReportStatusDto.setPreAlertNumber(internationalShipment.getPreAlertNumber());
                internationalRoadReportStatusDto.setReferenceNumber(internationalShipment.getReferenceNumber());
                internationalRoadReportStatusDto.setOrigin(internationalShipment.getOriginLocation());
                internationalRoadReportStatusDto.setDestination(internationalShipment.getDestinationLocation());
                internationalRoadReportStatusDto.setRoute(internationalShipment.getRouteNumber());
                internationalRoadReportStatusDto.setVehicle(internationalShipment.getVehicleNumber());
                internationalRoadReportStatusDto.setShipments(internationalShipment.getTotalShipments());
                internationalRoadReportStatusDto.setPallets(internationalShipment.getNumberOfPallets());
                internationalRoadReportStatusDto.setOccupancy(getOccupancyByVehicleType(internationalShipment.getVehicleType()));
                internationalRoadReportStatusDto.setBags(internationalShipment.getNumberOfBags());
                internationalRoadReportStatusDto.setEtd(internationalShipment.getEtd());
                internationalRoadReportStatusDto.setAtd(internationalShipment.getAtd());
                internationalRoadReportStatusDto.setEta(internationalShipment.getEta());
                internationalRoadReportStatusDto.setAta(internationalShipment.getAta());
                internationalRoadReportStatusDto.setRemarks(internationalShipment.getRemarks());
                List<InternationalShipmentHistory> internationalShipmentHistoryList = internationalShipmentHistoryRepository.findByInternationalShipmentId(internationalShipment.getId());
                for (InternationalShipmentHistory internationalShipmentHistory : internationalShipmentHistoryList) {
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Created")) {
                        internationalRoadReportStatusDto.setCreated(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Departed")) {
                        internationalRoadReportStatusDto.setDeparted(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Not Arrived")) {
                        internationalRoadReportStatusDto.setNotArrived(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Held in Customs")) {
                        internationalRoadReportStatusDto.setHeldInCustoms(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Awaiting payments")) {
                        internationalRoadReportStatusDto.setAwaitingPayments(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Cleared")) {
                        internationalRoadReportStatusDto.setCleared(internationalShipmentHistory.getProcessTime());
                        if(internationalShipment.getAtd()!=null && internationalShipmentHistory.getProcessTime()!=null){
                            Duration duration = Duration.between(internationalShipmentHistory.getProcessTime(), internationalShipment.getAtd());
                            internationalRoadReportStatusDto.setLeadTime(duration.toHours());
                        }
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Accident")) {
                        internationalRoadReportStatusDto.setAccident(internationalShipmentHistory.getProcessTime());
                    }
                    if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Border Delay")) {
                        internationalRoadReportStatusDto.setBorderDelay(internationalShipmentHistory.getProcessTime());
                    }
                }
                intRoadReportStatus.add(internationalRoadReportStatusDto);

            }

            FileInputStream fileInputStream = new FileInputStream(sampleFileLocalLocation + "/internationalRoadStatus.xlsx");
            Workbook  newWorkBook = WorkbookFactory.create(fileInputStream);
            Sheet summarySheet= newWorkBook.getSheetAt(0);
            int rowCount = 1;

            CellStyle rightAlignedStyle = newWorkBook.createCellStyle();
            rightAlignedStyle.setAlignment(HorizontalAlignment.RIGHT);
            CellStyle style = newWorkBook.createCellStyle();

            for (InternationalRoadReportStatusDto internationalRoadReportStatusDto : intRoadReportStatus) {
                Row row = summarySheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, internationalRoadReportStatusDto.getId() != null ? internationalRoadReportStatusDto.getId().toString() : " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getPreAlertNumber() != null ? internationalRoadReportStatusDto.getPreAlertNumber(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getReferenceNumber() != null ? internationalRoadReportStatusDto.getReferenceNumber(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getOrigin() != null ? internationalRoadReportStatusDto.getOrigin(): " ", rightAlignedStyle);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getDestination() != null ? internationalRoadReportStatusDto.getDestination(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getRoute() != null ? internationalRoadReportStatusDto.getRoute(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getVehicle() != null ? internationalRoadReportStatusDto.getVehicle(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getShipments() != null ? internationalRoadReportStatusDto.getShipments().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getPallets() != null ? internationalRoadReportStatusDto.getPallets().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getOccupancy() != null ? internationalRoadReportStatusDto.getOccupancy(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getBags() != null ? internationalRoadReportStatusDto.getBags().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getEtd() != null ? internationalRoadReportStatusDto.getEtd().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getAtd() != null ? internationalRoadReportStatusDto.getAtd().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getEta() != null ? internationalRoadReportStatusDto.getEta().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getAta() != null ? internationalRoadReportStatusDto.getAta().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getCreated() != null ? internationalRoadReportStatusDto.getCreated().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getDeparted() != null ? internationalRoadReportStatusDto.getDeparted().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getNotArrived() != null ? internationalRoadReportStatusDto.getNotArrived().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getHeldInCustoms() != null ? internationalRoadReportStatusDto.getHeldInCustoms().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getAwaitingPayments() != null ? internationalRoadReportStatusDto.getAwaitingPayments().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getAccident() != null ? internationalRoadReportStatusDto.getAccident().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getBorderDelay() != null ? internationalRoadReportStatusDto.getBorderDelay().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getOffloadedAtDestination() != null ? internationalRoadReportStatusDto.getOffloadedAtDestination().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getCleared() != null ? internationalRoadReportStatusDto.getCleared().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getLeadTime() != null ? internationalRoadReportStatusDto.getLeadTime().toString(): " ", style);
                createCell(row, columnCount++, internationalRoadReportStatusDto.getRemarks() != null ? internationalRoadReportStatusDto.getRemarks(): " ", style);

            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newWorkBook.write(byteArrayOutputStream);

            byte[] workbookBytes = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(workbookBytes);

            Resource resource = new InputStreamResource(byteArrayInputStream);
            return resource;

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    public Resource domesticPerformanceExcelDownload() {
        try{
            logger.info("Before findAllByActiveStatusMock");
            List<DomesticShipment> domesticShipmentList = domesticShipmentRepository.findAllByActiveStatusMock(true);
            logger.info("After findAllByActiveStatusMock");
            Set<String> routeNumbers = domesticShipmentList.stream()
                    .map(DomesticShipment::getRouteNumber)
                    .collect(Collectors.toSet());

            Map<String, DomesticRoute> routeMap = domesticRouteRepository
                    .findByRouteIn(routeNumbers)
                    .stream()
                    .collect(Collectors.toMap(DomesticRoute::getRoute, r -> r));

            List<DomesticPerformance> domesticPerformanceList = new ArrayList<>();
            try {
                for (DomesticShipment domesticShipment : domesticShipmentList) {
                    DomesticPerformance domesticPerformance = new DomesticPerformance();
                    domesticPerformance.setId(domesticShipment.getId());
                    domesticPerformance.setPreAlertNumber(domesticShipment.getPreAlertNumber());
                    domesticPerformance.setReferenceNumber(domesticShipment.getReferenceNumber());
                    domesticPerformance.setOrigin(domesticShipment.getOriginLocation());
                    domesticPerformance.setDestination(domesticShipment.getDestinationLocation());
                    domesticPerformance.setRoute(domesticShipment.getRouteNumber());
                    domesticPerformance.setVehicle(domesticShipment.getVehicleNumber());
                    domesticPerformance.setShipments(domesticShipment.getTotalShipments());
                    domesticPerformance.setPallets(domesticShipment.getNumberOfPallets());
                    domesticPerformance.setOccupancy(getOccupancyByVehicleType(domesticShipment.getVehicleType()));
                    domesticPerformance.setBags(domesticShipment.getNumberOfShipments());
                    // logger.info(domesticShipment.getId()+ " Before findByRoute "+domesticShipment.getRouteNumber());
                    DomesticRoute domesticRoute = routeMap.get(domesticShipment.getRouteNumber());
                    // logger.info(domesticShipment.getId()+ " After findByRoute "+domesticShipment.getRouteNumber());
                    LocalDate date = domesticShipment.getCreatedTime().toLocalDate();
                    domesticPerformance.setPlanedEtd(LocalDateTime.of(date, domesticRoute.getEtd()));
                    domesticPerformance.setPlanedEta(LocalDateTime.of(date, domesticRoute.getEtd()).plusHours(domesticRoute.getDurationLimit()));
                    domesticPerformance.setAta(domesticShipment.getAta());
                    domesticPerformance.setAtd(domesticShipment.getAtd());

                    if (domesticRoute.getEta() != null && domesticShipment.getAta() != null) {
                        //logger.info("Before eta vs ata");
                        Duration durationForEtaAndAta = Duration.between(domesticRoute.getEta(), domesticShipment.getAta());
                        domesticPerformance.setPlanedEtaVsAta(durationForEtaAndAta.toHours());
                        // logger.info("After eta vs ata");
                    }
                    if (domesticRoute.getEtd() != null && domesticShipment.getAtd() != null) {
                        //  logger.info("Before etd vs atd");
                        Duration durationForEtdAndAtd = Duration.between(domesticRoute.getEtd(), domesticShipment.getAtd());
                        domesticPerformance.setPlanedEtdVsAtd(durationForEtdAndAtd.toHours());
                        // logger.info("After etd vs atd");
                    }
                    if (domesticShipment.getAtd() != null && domesticShipment.getAta() != null) {
                        // logger.info("Before transit time");
                        Duration durationForTransitTime = Duration.between(domesticShipment.getAtd(), domesticShipment.getAta());
                        domesticPerformance.setTransitTime(durationForTransitTime.toHours());
                        // logger.info("After transit time");
                    }
                    // logger.info("Before add");
                    domesticPerformanceList.add(domesticPerformance);
                    // logger.info("After add");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            logger.info("loop  completed ");
            logger.info("before file extract"+sampleFileLocalLocation + "/domesticPerformance.xlsx");
            FileInputStream fileInputStream = new FileInputStream(sampleFileLocalLocation + "/domesticPerformance.xlsx");
            logger.info("After file extract"+sampleFileLocalLocation + "/domesticPerformance.xlsx");
            Workbook  newWorkBook = WorkbookFactory.create(fileInputStream);
            Sheet summarySheet= newWorkBook.getSheetAt(0);
            int rowCount = 1;

            CellStyle rightAlignedStyle = newWorkBook.createCellStyle();
            rightAlignedStyle.setAlignment(HorizontalAlignment.RIGHT);
            CellStyle style = newWorkBook.createCellStyle();

            for (DomesticPerformance domesticPerformance : domesticPerformanceList) {
                Row row = summarySheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, domesticPerformance.getId() != null ? domesticPerformance.getId().toString() : " ", style);
                createCell(row, columnCount++, domesticPerformance.getPreAlertNumber() != null ? domesticPerformance.getPreAlertNumber(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getReferenceNumber() != null ? domesticPerformance.getReferenceNumber(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getOrigin() != null ? domesticPerformance.getOrigin(): " ", rightAlignedStyle);
                createCell(row, columnCount++, domesticPerformance.getDestination() != null ? domesticPerformance.getDestination(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getRoute() != null ? domesticPerformance.getRoute(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getVehicle() != null ? domesticPerformance.getVehicle(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getShipments() != null ? domesticPerformance.getShipments().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getPallets() != null ? domesticPerformance.getPallets().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getOccupancy() != null ? domesticPerformance.getOccupancy(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getBags() != null ? domesticPerformance.getBags().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getPlanedEtd() != null ? domesticPerformance.getPlanedEtd().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getAtd() != null ? domesticPerformance.getAtd().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getPlanedEta() != null ? domesticPerformance.getPlanedEta().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getAta() != null ? domesticPerformance.getAta().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getPlanedEtdVsAtd() != null ? domesticPerformance.getPlanedEtdVsAtd().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getPlanedEtaVsAta() != null ? domesticPerformance.getPlanedEtaVsAta().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getTransitTime() != null ? domesticPerformance.getTransitTime().toString(): " ", style);

            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newWorkBook.write(byteArrayOutputStream);

            byte[] workbookBytes = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(workbookBytes);

            Resource resource = new InputStreamResource(byteArrayInputStream);
            return resource;

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RecordNotFoundException(e.getMessage());
        }
    }
    private String getOccupancyByVehicleType(String name){
        return vehicleTypeService.getVehicleTypeByVehicleTypeName(name).getOccupancy();
    }
}
