package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.*;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.model.InternationalShipmentHistory;
import com.example.CargoTracking.repository.*;
import com.example.CargoTracking.specification.DomesticSummarySpecification;
import com.example.CargoTracking.specification.InternationalSummarySpecification;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReportAndStatusService {
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    InternationalShipmentHistoryRepository internationalShipmentHistoryRepository;
    @Autowired
    DomesticShipmentRepository domesticShipmentRepository;
    @Autowired
    DomesticShipmentHistoryRepository domesticShipmentHistoryRepository;
    @Autowired
    DomesticRouteRepository domesticRouteRepository;
    @Autowired
    VehicleTypeService vehicleTypeService;
    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);


    public List<InternationalAirReportStatusDto> findInternationalAirReportStatus(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary) {
        List<InternationalAirReportStatusDto> internationalAirReportStatusDtoList = new ArrayList<>();
        List<InternationalShipment> internationalShipmentsList = new ArrayList<>();
        if ((searchCriteriaForInternationalSummary.getDestinations() == null || searchCriteriaForInternationalSummary.getDestinations().isEmpty())
                && (searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin().isEmpty())
                && (searchCriteriaForInternationalSummary.getToDate() == null || searchCriteriaForInternationalSummary.getToDate().isEmpty())
                && (searchCriteriaForInternationalSummary.getFromDate() == null || searchCriteriaForInternationalSummary.getFromDate().isEmpty())
                && (searchCriteriaForInternationalSummary.getStatus() == null || searchCriteriaForInternationalSummary.getStatus().isEmpty())
                && (searchCriteriaForInternationalSummary.getRouteNumber() == null || searchCriteriaForInternationalSummary.getRouteNumber().isEmpty())) {
             internationalShipmentsList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Air");
        }else{
            searchCriteriaForInternationalSummary.setType("By Air");
            Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
            internationalShipmentsList = internationalShipmentRepository.findAll(internationalShipmentSpecification);
        }
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
            internationalAirReportStatusDtoList.add(internationalAirReportStatusDto);

        }
        return internationalAirReportStatusDtoList;
    }

    public List<InternationalRoadReportStatusDto> findInternationalRoadReportStatus(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary) {
        List<InternationalRoadReportStatusDto> internationalRoadReportStatusDtoList = new ArrayList<>();
        List<InternationalShipment> internationalShipmentList = new ArrayList<>();
        if ((searchCriteriaForInternationalSummary.getDestinations() == null || searchCriteriaForInternationalSummary.getDestinations().isEmpty())
                && (searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin().isEmpty())
                && (searchCriteriaForInternationalSummary.getToDate() == null || searchCriteriaForInternationalSummary.getToDate().isEmpty())
                && (searchCriteriaForInternationalSummary.getFromDate() == null || searchCriteriaForInternationalSummary.getFromDate().isEmpty())
                && (searchCriteriaForInternationalSummary.getStatus() == null || searchCriteriaForInternationalSummary.getStatus().isEmpty())
                && (searchCriteriaForInternationalSummary.getRouteNumber() == null || searchCriteriaForInternationalSummary.getRouteNumber().isEmpty())) {
            internationalShipmentList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Road");
        }else{
            searchCriteriaForInternationalSummary.setType("By Road");
            Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
            internationalShipmentList = internationalShipmentRepository.findAll(internationalShipmentSpecification);
        }
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
            internationalRoadReportStatusDtoList.add(internationalRoadReportStatusDto);

        }
        return internationalRoadReportStatusDtoList;
    }

    public List<InternationalAirReportPerformance> findInternationalAirReportPerformance(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary) {
        List<InternationalAirReportPerformance> internationalAirReportPerformanceList = new ArrayList<>();
        List<InternationalShipment> internationalShipmentList = new ArrayList<>();
        if ((searchCriteriaForInternationalSummary.getDestinations() == null || searchCriteriaForInternationalSummary.getDestinations().isEmpty())
                && (searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin().isEmpty())
                && (searchCriteriaForInternationalSummary.getToDate() == null || searchCriteriaForInternationalSummary.getToDate().isEmpty())
                && (searchCriteriaForInternationalSummary.getFromDate() == null || searchCriteriaForInternationalSummary.getFromDate().isEmpty())
                && (searchCriteriaForInternationalSummary.getStatus() == null || searchCriteriaForInternationalSummary.getStatus().isEmpty())
                && (searchCriteriaForInternationalSummary.getRouteNumber() == null || searchCriteriaForInternationalSummary.getRouteNumber().isEmpty())) {
            internationalShipmentList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Air");
        }else{
            searchCriteriaForInternationalSummary.setType("By Air");
            Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
            internationalShipmentList = internationalShipmentRepository.findAll(internationalShipmentSpecification);
        }
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
            internationalAirReportPerformanceList.add(internationalAirReportPerformance);
        }
        return internationalAirReportPerformanceList;
    }

    public List<InternationalRoadReportPerformance> findInternationalRoadReportPerformance(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary) {
        List<InternationalRoadReportPerformance> internationalRoadReportPerformanceList = new ArrayList<>();
        List<InternationalShipment> internationalShipmentList = new ArrayList<>();
        if ((searchCriteriaForInternationalSummary.getDestinations() == null || searchCriteriaForInternationalSummary.getDestinations().isEmpty())
                && (searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin().isEmpty())
                && (searchCriteriaForInternationalSummary.getToDate() == null || searchCriteriaForInternationalSummary.getToDate().isEmpty())
                && (searchCriteriaForInternationalSummary.getFromDate() == null || searchCriteriaForInternationalSummary.getFromDate().isEmpty())
                && (searchCriteriaForInternationalSummary.getStatus() == null || searchCriteriaForInternationalSummary.getStatus().isEmpty())
                && (searchCriteriaForInternationalSummary.getRouteNumber() == null || searchCriteriaForInternationalSummary.getRouteNumber().isEmpty())) {
            internationalShipmentList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Road");
        }else{
            searchCriteriaForInternationalSummary.setType("By Road");
            Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
            internationalShipmentList = internationalShipmentRepository.findAll(internationalShipmentSpecification);
        }
//        List<InternationalShipment> internationalShipmentsList = internationalShipmentRepository.findByActiveStatusAndType(true, "By Road");
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
            internationalRoadReportPerformanceList.add(internationalRoadReportPerformance);
        }
        return  internationalRoadReportPerformanceList;
    }

    public List<DomesticPerformance> findDomesticPerformance(SearchCriteriaForSummary searchCriteriaForSummary) {
        logger.info("start");
        List<DomesticPerformance> domesticPerformanceList = new ArrayList<>();
        List<DomesticShipment> domesticShipmentList = new ArrayList<>();
        if ((searchCriteriaForSummary.getDestinations() == null || searchCriteriaForSummary.getDestinations().isEmpty())
                && (searchCriteriaForSummary.getOrigin() == null || searchCriteriaForSummary.getOrigin().isEmpty())
                && (searchCriteriaForSummary.getToDate() == null || searchCriteriaForSummary.getToDate().isEmpty())
                && (searchCriteriaForSummary.getFromDate() == null || searchCriteriaForSummary.getFromDate().isEmpty())
                && (searchCriteriaForSummary.getStatus() == null || searchCriteriaForSummary.getStatus().isEmpty())
                && (searchCriteriaForSummary.getRouteNumber() == null || searchCriteriaForSummary.getRouteNumber().isEmpty())) {
        logger.info("enter in 1st if ");
            domesticShipmentList = domesticShipmentRepository.findAllByActiveStatusMock(true);
            logger.info("end 1st if ");
        }else{
            logger.info("enter in 1st else ");
            Specification<DomesticShipment> domesticSummarySpecification = DomesticSummarySpecification.getSearchSpecification(searchCriteriaForSummary);
            domesticShipmentList = domesticShipmentRepository.findAll(domesticSummarySpecification);
            logger.info("end 1st else");
        }
        for(DomesticShipment domesticShipment: domesticShipmentList){
            logger.info("enter in 2st if");
            DomesticPerformance domesticPerformance = new DomesticPerformance();
            logger.info(String.format("check id ",domesticShipment.getId()));
            domesticPerformance.setId(domesticShipment.getId());
            logger.info("check PreAlertNumber",domesticShipment.getPreAlertNumber());
            domesticPerformance.setPreAlertNumber(domesticShipment.getPreAlertNumber());
            logger.info("check ReferenceNumber",domesticShipment.getReferenceNumber());
            domesticPerformance.setReferenceNumber(domesticShipment.getReferenceNumber());
            logger.info("check Origin ",domesticShipment.getOriginLocation());
            domesticPerformance.setOrigin(domesticShipment.getOriginLocation());
            logger.info("check Destination ",domesticShipment.getDestinationLocation());
            domesticPerformance.setDestination(domesticShipment.getDestinationLocation());
            logger.info("check Route ",domesticShipment.getRouteNumber());
            domesticPerformance.setRoute(domesticShipment.getRouteNumber());
            logger.info("check vehicle ",domesticShipment.getVehicleNumber());
            domesticPerformance.setVehicle(domesticShipment.getVehicleNumber());
            logger.info("check Shipments ",domesticShipment.getTotalShipments());
            domesticPerformance.setShipments(domesticShipment.getTotalShipments());
            logger.info("check Pallets ",domesticShipment.getNumberOfPallets());
            domesticPerformance.setPallets(domesticShipment.getNumberOfPallets());
            logger.info("check VehicleType ",domesticShipment.getVehicleType());
            domesticPerformance.setOccupancy(getOccupancyByVehicleType(domesticShipment.getVehicleType()));
            logger.info("check Bags ",domesticShipment.getNumberOfShipments());
            domesticPerformance.setBags(domesticShipment.getNumberOfShipments());
            logger.info("check domesticRoute");
            DomesticRoute domesticRoute = domesticRouteRepository.findByRoute(domesticShipment.getRouteNumber());
            logger.info("check PlanedEta");
            domesticPerformance.setPlanedEta(domesticRoute.getEta());
            logger.info("check PlanedEtd");
            domesticPerformance.setPlanedEtd(domesticRoute.getEtd());
            logger.info("check Ata");
            domesticPerformance.setAta(domesticShipment.getAta());
            logger.info("check Atd");
            domesticPerformance.setAtd(domesticShipment.getAtd());
            logger.info("end 2st if");
            if(domesticRoute.getEta()!=null && domesticShipment.getAta()!=null){
                logger.info("start 2st in 1st if");
                Duration durationForEtaAndAta = Duration.between(domesticRoute.getEta(), domesticShipment.getAta());
                domesticPerformance.setPlanedEtaVsAta(durationForEtaAndAta.toHours());
                logger.info("end 2st in 1st if");
            }
            if(domesticRoute.getEtd()!=null && domesticShipment.getAtd()!=null){
                logger.info("start 2st in 2st if");
                Duration durationForEtdAndAtd = Duration.between(domesticRoute.getEtd(), domesticShipment.getAtd());
                domesticPerformance.setPlanedEtdVsAtd(durationForEtdAndAtd.toHours());
                logger.info("end 2st in 2st if");
            }
            if(domesticShipment.getAtd()!=null && domesticShipment.getAta()!=null){
                logger.info("start 2st in 3st if");
                Duration durationForTransitTime = Duration.between(domesticShipment.getAta(), domesticShipment.getAtd());
                domesticPerformance.setTransitTime(durationForTransitTime.toHours());
                logger.info("end 2st in 3st if");
            }
            domesticPerformanceList.add(domesticPerformance);
            logger.info("end 2st  if");
        }
        return domesticPerformanceList;
    }


    private String getOccupancyByVehicleType(String name){
        return vehicleTypeService.getVehicleTypeByVehicleTypeName(name).getOccupancy();
    }
}
