package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.InternationalAirReportPerformance;
import com.example.CargoTracking.dto.InternationalAirReportStatusDto;
import com.example.CargoTracking.dto.InternationalRoadReportPerformance;
import com.example.CargoTracking.dto.InternationalRoadReportStatusDto;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.model.InternationalShipmentHistory;
import com.example.CargoTracking.repository.InternationalShipmentHistoryRepository;
import com.example.CargoTracking.repository.InternationalShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportAndStatusService {
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    InternationalShipmentHistoryRepository internationalShipmentHistoryRepository;

    public List<InternationalAirReportStatusDto> findInternationalAirReportStatus() {
        List<InternationalAirReportStatusDto> internationalAirReportStatusDtoList = new ArrayList<>();
        List<InternationalShipment> internationalShipmentsList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Air");
        for (InternationalShipment internationalShipment : internationalShipmentsList) {
            InternationalAirReportStatusDto internationalAirReportStatusDto = new InternationalAirReportStatusDto();
            internationalAirReportStatusDto.setId(internationalShipment.getId());
            internationalAirReportStatusDto.setPreAlertNumber(internationalShipment.getPreAlertNumber());
            internationalAirReportStatusDto.setReferenceNumber(internationalShipment.getReferenceNumber());
            internationalAirReportStatusDto.setOrigin(internationalShipment.getOriginPort());
            internationalAirReportStatusDto.setDestination(internationalShipment.getDestinationPort());
            internationalAirReportStatusDto.setRoute(internationalShipment.getRouteNumber());
            internationalAirReportStatusDto.setFlightNumber(internationalShipment.getFlightNumber());
            internationalAirReportStatusDto.setShipments(internationalShipment.getNumberOfShipments());
            internationalAirReportStatusDto.setBags(internationalShipment.getNumberOfBags());
            internationalAirReportStatusDto.setEtd(internationalShipment.getEtd());
            internationalAirReportStatusDto.setAtd(internationalShipment.getAtd());
            internationalAirReportStatusDto.setEta(internationalShipment.getEta());
            internationalAirReportStatusDto.setAta(internationalShipment.getAta());
            internationalAirReportStatusDto.setRemarks(internationalShipment.getRemarks());
            List<InternationalShipmentHistory> internationalShipmentHistoryList = internationalShipmentHistoryRepository.findByInternationalShipmentId(internationalShipment.getId());
            for (InternationalShipmentHistory internationalShipmentHistory : internationalShipmentHistoryList) {
                if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Created")) {
                    internationalAirReportStatusDto.setCreated(internationalShipmentHistory.getProcessTime());
                }
                if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Offloaded from Aircarft")) {
                    internationalAirReportStatusDto.setOffLoadedFromAircraft(internationalShipmentHistory.getProcessTime());
                }
                if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Flight Delayed")) {
                    internationalAirReportStatusDto.setFlightDelayed(internationalShipmentHistory.getProcessTime());
                }
                if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Cleared")) {
                    internationalAirReportStatusDto.setCleared(internationalShipmentHistory.getProcessTime());
                    Duration duration = Duration.between(internationalShipmentHistory.getProcessTime(), internationalShipment.getAtd());
                    internationalAirReportStatusDto.setLeadTime(duration.toHours());
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
            internationalAirReportStatusDtoList.add(internationalAirReportStatusDto);

        }
        return internationalAirReportStatusDtoList;
    }

    public List<InternationalRoadReportStatusDto> findInternationalRoadReportStatus() {
        List<InternationalRoadReportStatusDto> internationalRoadReportStatusDtoList = new ArrayList<>();
        List<InternationalShipment> internationalShipmentList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Road");
        for (InternationalShipment internationalShipment : internationalShipmentList) {
            InternationalRoadReportStatusDto internationalRoadReportStatusDto = new InternationalRoadReportStatusDto();
            internationalRoadReportStatusDto.setId(internationalShipment.getId());
            internationalRoadReportStatusDto.setPreAlertNumber(internationalShipment.getPreAlertNumber());
            internationalRoadReportStatusDto.setReferenceNumber(internationalShipment.getReferenceNumber());
            internationalRoadReportStatusDto.setOrigin(internationalShipment.getOriginPort());
            internationalRoadReportStatusDto.setDestination(internationalShipment.getDestinationPort());
            internationalRoadReportStatusDto.setRoute(internationalShipment.getRouteNumber());
            internationalRoadReportStatusDto.setVehicle(internationalShipment.getVehicleNumber());
            internationalRoadReportStatusDto.setShipments(internationalShipment.getTotalShipments());
            internationalRoadReportStatusDto.setPallets(internationalShipment.getNumberOfPallets());
            internationalRoadReportStatusDto.setOccupancy(internationalShipment.getVehicleType());
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
                    Duration duration = Duration.between(internationalShipmentHistory.getProcessTime(), internationalShipment.getAtd());
                    internationalRoadReportStatusDto.setLeadTime(duration.toHours());
                }
                if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Accident")) {
                    internationalRoadReportStatusDto.setAccident(internationalShipmentHistory.getProcessTime());
                }
                if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Border Delay")) {
                    internationalRoadReportStatusDto.setBorderDelay(internationalShipmentHistory.getProcessTime());
                }
            }
            internationalRoadReportStatusDtoList.add(internationalRoadReportStatusDto);

        }
        return internationalRoadReportStatusDtoList;
    }

    public List<InternationalAirReportPerformance> findInternationalAirReportPerformance() {
        List<InternationalAirReportPerformance> internationalAirReportPerformanceList = new ArrayList<>();
        List<InternationalShipment> internationalShipmentsList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Air");
        for (InternationalShipment internationalShipment: internationalShipmentsList){
            InternationalAirReportPerformance internationalAirReportPerformance = new InternationalAirReportPerformance();
            internationalAirReportPerformance.setId(internationalShipment.getId());
            internationalAirReportPerformance.setPreAlertNumber(internationalShipment.getPreAlertNumber());
            internationalAirReportPerformance.setReferenceNumber(internationalShipment.getReferenceNumber());
            internationalAirReportPerformance.setOrigin(internationalShipment.getOriginPort());
            internationalAirReportPerformance.setDestination(internationalShipment.getDestinationPort());
            internationalAirReportPerformance.setRoute(internationalShipment.getRouteNumber());
            internationalAirReportPerformance.setFlight(internationalShipment.getFlightNumber());
            internationalAirReportPerformance.setActualTimeArrival(internationalShipment.getAta());
            internationalAirReportPerformance.setActualTimeDeparture(internationalShipment.getAtd());
            Duration durationForTransitTime = Duration.between(internationalShipment.getAta(), internationalShipment.getAtd());
            internationalAirReportPerformance.setTotalTransitTime(durationForTransitTime.toHours());
            List<InternationalShipmentHistory> internationalShipmentHistoryList = internationalShipmentHistoryRepository.findByInternationalShipmentId(internationalShipment.getId());
            for(InternationalShipmentHistory internationalShipmentHistory: internationalShipmentHistoryList){
                if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Cleared")) {
                    internationalAirReportPerformance.setCleared(internationalShipmentHistory.getProcessTime());
                    Duration durationForLeadTime = Duration.between(internationalShipmentHistory.getProcessTime(), internationalShipment.getAtd());
                    internationalAirReportPerformance.setTotalLeadTime(durationForLeadTime.toHours());
                }
            }
            internationalAirReportPerformanceList.add(internationalAirReportPerformance);
        }
        return internationalAirReportPerformanceList;
    }

    public List<InternationalRoadReportPerformance> findInternationalRoadReportPerformance() {
        List<InternationalRoadReportPerformance> internationalRoadReportPerformanceList = new ArrayList<>();
        List<InternationalShipment> internationalShipmentsList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Road");
        for(InternationalShipment internationalShipment: internationalShipmentsList){
            InternationalRoadReportPerformance internationalRoadReportPerformance = new InternationalRoadReportPerformance();
            internationalRoadReportPerformance.setId(internationalShipment.getId());
            internationalRoadReportPerformance.setPreAlertNumber(internationalShipment.getPreAlertNumber());
            internationalRoadReportPerformance.setReferenceNumber(internationalShipment.getReferenceNumber());
            internationalRoadReportPerformance.setOrigin(internationalShipment.getOriginPort());
            internationalRoadReportPerformance.setDestination(internationalShipment.getDestinationPort());
            internationalRoadReportPerformance.setRoute(internationalShipment.getRouteNumber());
            internationalRoadReportPerformance.setVehicleType(internationalShipment.getVehicleType());
            internationalRoadReportPerformance.setActualTimeArrival(internationalShipment.getAta());
            internationalRoadReportPerformance.setActualTimeDeparture(internationalShipment.getAtd());
            Duration durationForTransitTime = Duration.between(internationalShipment.getAta(), internationalShipment.getAtd());
            internationalRoadReportPerformance.setTotalTransitTime(durationForTransitTime.toHours());
            List<InternationalShipmentHistory> internationalShipmentHistoryList = internationalShipmentHistoryRepository.findByInternationalShipmentId(internationalShipment.getId());
            for(InternationalShipmentHistory internationalShipmentHistory: internationalShipmentHistoryList){
                if (internationalShipmentHistory.getStatus().equalsIgnoreCase("Offloaded at Destination")) {
                    internationalRoadReportPerformance.setOffloaded(internationalShipmentHistory.getProcessTime());
                    Duration durationForLeadTime = Duration.between(internationalShipmentHistory.getProcessTime(), internationalShipment.getAtd());
                    internationalRoadReportPerformance.setTotalLeadTime(durationForLeadTime.toHours());
                }
            }
            internationalRoadReportPerformanceList.add(internationalRoadReportPerformance);
        }
        return  internationalRoadReportPerformanceList;
    }
}
