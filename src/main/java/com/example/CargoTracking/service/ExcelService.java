package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.*;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.repository.DomesticRouteRepository;
import com.example.CargoTracking.repository.DomesticShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.InputStreamResource;
import java.io.*;

import org.apache.poi.ss.usermodel.*;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

@Service
public class ExcelService {

    @Autowired
    ReportAndStatusService reportAndStatusService;
    @Autowired
    DomesticShipmentRepository domesticShipmentRepository;
    @Autowired
    VehicleTypeService vehicleTypeService;
    @Autowired
    DomesticRouteRepository domesticRouteRepository;
    @Value("${excel.file.location}")
    private String sampleFileLocalLocation;
    public Resource internationalAirReportPerformanceExcelDownload(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary) {
        try{
//            SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary = new SearchCriteriaForInternationalSummary();
            List<InternationalAirReportPerformance> intAirReportPerformance = this.reportAndStatusService.findInternationalAirReportPerformance(searchCriteriaForInternationalSummary);
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

    public Resource internationalRoadReportPerformanceExcelDownload(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary) {
        try{
//            SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary = new SearchCriteriaForInternationalSummary();
            List<InternationalRoadReportPerformance> intRoadReportPerformance = this.reportAndStatusService.findInternationalRoadReportPerformance(searchCriteriaForInternationalSummary);
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

    public Resource internationalAirReportStatusExcelDownload(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary) {
        try{
//            SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary = new SearchCriteriaForInternationalSummary();
            List<InternationalAirReportStatusDto> intAirReportStatus = this.reportAndStatusService.findInternationalAirReportStatus(searchCriteriaForInternationalSummary);
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

    public Resource internationalRoadReportStatusExcelDownload(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary) {
        try{
//            SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary = new SearchCriteriaForInternationalSummary();
            List<InternationalRoadReportStatusDto> intRoadReportStatus = this.reportAndStatusService.findInternationalRoadReportStatus(searchCriteriaForInternationalSummary);
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
            List<DomesticShipment> domesticShipmentList = domesticShipmentRepository.findAllByActiveStatusMock(true);
            List<DomesticPerformance> domesticPerformanceList = new ArrayList<>();
            for(DomesticShipment domesticShipment: domesticShipmentList){
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
                DomesticRoute domesticRoute = domesticRouteRepository.findByRoute(domesticShipment.getRouteNumber());
                domesticPerformance.setPlanedEta(domesticRoute.getEta());
                domesticPerformance.setPlanedEtd(domesticRoute.getEtd());
                domesticPerformance.setAta(domesticShipment.getAta());
                domesticPerformance.setAtd(domesticShipment.getAtd());
                if(domesticRoute.getEta()!=null && domesticShipment.getAta()!=null){
                    Duration durationForEtaAndAta = Duration.between(domesticRoute.getEta(), domesticShipment.getAta());
                    domesticPerformance.setPlanedEtaVsAta(durationForEtaAndAta.toHours());
                }
                if(domesticRoute.getEtd()!=null && domesticShipment.getAtd()!=null){
                    Duration durationForEtdAndAtd = Duration.between(domesticRoute.getEtd(), domesticShipment.getAtd());
                    domesticPerformance.setPlanedEtdVsAtd(durationForEtdAndAtd.toHours());
                }
                if(domesticShipment.getAtd()!=null && domesticShipment.getAta()!=null){
                    Duration durationForTransitTime = Duration.between(domesticShipment.getAta(), domesticShipment.getAtd());
                    domesticPerformance.setTransitTime(durationForTransitTime.toHours());
                }
                domesticPerformanceList.add(domesticPerformance);
            }
            FileInputStream fileInputStream = new FileInputStream(sampleFileLocalLocation + "/domesticPerformance.xlsx");
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
                createCell(row, columnCount++, domesticPerformance.getPlanedEta() != null ? domesticPerformance.getPlanedEta().toString(): " ", style);
                createCell(row, columnCount++, domesticPerformance.getAtd() != null ? domesticPerformance.getAtd().toString(): " ", style);
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
