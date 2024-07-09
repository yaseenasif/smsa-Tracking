package com.example.CargoTracking.service;
import com.example.CargoTracking.criteria.SearchCriteria;
import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.*;
import com.example.CargoTracking.specification.DomesticRouteSpecification;
import com.example.CargoTracking.specification.DomesticShipmentSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DomesticRouteService {
    @Autowired
    DomesticRouteRepository domesticRouteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EmailAddressForRouteRepository emailAddressForRouteRepository;
    @Autowired
    EmailService emailService;

    @Autowired
    DomesticShipmentRepository domesticShipmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);


    public List<DomesticRouteDto> findDomesticRoute(String origin, String destination) {
        List<DomesticRoute> byOriginAndDestination =
                domesticRouteRepository.findByOriginAndDestinationAndActiveStatus(origin, destination,true);
        if (byOriginAndDestination.isEmpty()) {
            throw new RecordNotFoundException(String.format("No routes available against given origin and destination"));
        }

        List<DomesticShipment> domesticShipment =
                domesticShipmentRepository.findByCreatedAt(LocalDate.now());

        List<DomesticRoute> resultList = new ArrayList<>();
        if (domesticShipment.isEmpty()) {
            return toDtoList(byOriginAndDestination);
        }

        List<DomesticRoute> usedRoute = new ArrayList<>();

        for(DomesticRoute route:byOriginAndDestination){
            for(DomesticShipment shipment : domesticShipment){
                if(route.getRoute().contains("Adhoc") || route.getRoute().contains("adhoc")){
                    break;
                }
                if (route.getRoute().equals(shipment.getRouteNumber())) {
                    usedRoute.add(route);
                    break;
                }
            }
        }
        logger.info("Used route "+usedRoute);
        byOriginAndDestination.removeAll(usedRoute);

        if (byOriginAndDestination.isEmpty()) {
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(byOriginAndDestination);
    }

    public List<DomesticRouteDto> toDtoList(List<DomesticRoute> domesticRoutes) {
        return domesticRoutes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DomesticRouteDto toDto(DomesticRoute domesticRoute) {
        return modelMapper.map(domesticRoute, DomesticRouteDto.class);
    }

    public DomesticRoute toEntity(DomesticRouteDto domesticRouteDto) {
        return modelMapper.map(domesticRouteDto, DomesticRoute.class);
    }

    public DomesticRoute findRouteByRouteNumber(String routeNumber) {
        return domesticRouteRepository.findByRoute(routeNumber);
    }


    public DomesticRouteDto saveDomesticRoute(DomesticRouteDto domesticRouteDto) {
        DomesticRoute domesticRoute = toEntity(domesticRouteDto);

        domesticRoute.setActiveStatus(Boolean.TRUE);

        DomesticRoute seveWithVehicleAndDriver = domesticRouteRepository.save(domesticRoute);
//
//
//        Set<Driver> drivers=new HashSet<>();
//
//        domesticRouteDto.getDrivers().stream().forEach(el->{
//            el.setDomesticRoute(save);
//            drivers.add(el);
//        });
//        domesticRoute.setDrivers(drivers);
//
//        Set<Vehicle> vehicles=new HashSet<>();
//
//        domesticRouteDto.getVehicles().stream().forEach(el->{
//            el.setDomesticRoute(save);
//            vehicles.add(el);
//        });
//        domesticRoute.setDrivers(drivers);
//        domesticRoute.setVehicles(vehicles);
//        DomesticRoute seveWithVehicleAndDriver = domesticRouteRepository.save(domesticRoute);
        return toDto(seveWithVehicleAndDriver);
    }

    public Page<DomesticRouteDto> findAllDomesticRoutes(SearchCriteria searchCriteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<DomesticRoute> domesticRouteSpecification = DomesticRouteSpecification.getSearchSpecification(searchCriteria);
        Page<DomesticRoute> domesticRoutes = domesticRouteRepository.findAll(domesticRouteSpecification, pageable);
        Page<DomesticRouteDto> domesticRouteDtos = domesticRoutes.map(entity -> toDto(entity));

        return domesticRouteDtos;
//        return toDtoList(domesticRouteRepository.getActiveDomesticRoutes());
    }

    public ApiResponse deleteDomesticRoute(Long id) {
        Optional<DomesticRoute> domesticRoute = domesticRouteRepository.findById(id);
        if (domesticRoute.isPresent()) {
            DomesticRoute route = domesticRoute.get();
            route.setActiveStatus(Boolean.FALSE);
            domesticRouteRepository.save(route);
            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }
        throw new RecordNotFoundException(String.format("Domestic Route not found by this id => %d", id));
    }

    @Transactional
    public DomesticRouteDto updateDomesticRoute(Long id, DomesticRouteDto domesticRouteDto) {
        Optional<DomesticRoute> domesticRoute = domesticRouteRepository.findById(id);

        List<Long> vehiclesPresentInDto = domesticRouteDto.getVehicles().stream().map(Vehicle::getId)
                .collect(Collectors.toList());

        if (domesticRoute.isPresent()) {
//            List<Long> vehicleIds = domesticRoute.get().getVehicles()
//                    .stream()
//                    .map(Vehicle::getId)
//                    .filter(vehicleId -> !vehiclesPresentInDto.contains(vehicleId))
//                    .collect(Collectors.toList());
//            domesticRouteRepository.
            DomesticRoute oldDomesticRoute = new DomesticRoute();
            oldDomesticRoute.setOrigin(domesticRoute.get().getOrigin());
            oldDomesticRoute.setDestination(domesticRoute.get().getDestination());
            oldDomesticRoute.setRoute(domesticRoute.get().getRoute());
            oldDomesticRoute.setEtd(domesticRoute.get().getEtd());
            oldDomesticRoute.setEta(domesticRoute.get().getEta());
            oldDomesticRoute.setDriver(domesticRoute.get().getDriver());
            oldDomesticRoute.setDurationLimit(domesticRoute.get().getDurationLimit());
            oldDomesticRoute.setRemarks(domesticRoute.get().getRemarks());

            domesticRoute.get().setOrigin(domesticRouteDto.getOrigin());
            domesticRoute.get().setDestination(domesticRouteDto.getDestination());
            domesticRoute.get().setRoute(domesticRouteDto.getRoute());
            domesticRoute.get().setEtd(domesticRouteDto.getEtd());
            domesticRoute.get().setEta(domesticRouteDto.getEta());
//            domesticRoute.get().setDriver(domesticRouteDto.getDriver());
            domesticRoute.get().setDurationLimit(domesticRouteDto.getDurationLimit());
            domesticRoute.get().setRemarks(domesticRouteDto.getRemarks());
//            domesticRoute.get().setVehicles(domesticRouteDto.getVehicles());
//            domesticRoute.get().setDrivers(domesticRouteDto.getDrivers());
//            Set<Driver> drivers=new HashSet<>();
//
//            domesticRouteDto.getDrivers().stream().forEach(el->{
//                el.setDomesticRoute(domesticRoute.get());
//                drivers.add(el);
//            });
//            domesticRoute.get().setDrivers(drivers);
//
//            Set<Vehicle> vehicles=new HashSet<>();
//
//            domesticRouteDto.getVehicles().stream().forEach(el->{
//                el.setDomesticRoute(domesticRoute.get());
//                vehicles.add(el);
//            });
//            domesticRoute.get().setDrivers(drivers);
//            domesticRoute.get().setVehicles(vehicles);
            DomesticRoute save = domesticRouteRepository.save(domesticRoute.get());

            Optional<EmailAddressForRoutes> domesticRouteEmailAddress1 = emailAddressForRouteRepository.findById(1L);
            if(domesticRouteEmailAddress1.isPresent()){
                EmailAddressForRoutes domesticRouteEmailAddress = domesticRouteEmailAddress1.get();
                if(domesticRouteEmailAddress!=null){
                    if(domesticRouteEmailAddress.getEmails()!=null || !domesticRouteEmailAddress.getEmails().isEmpty()){
                        String[] resultList = domesticRouteEmailAddress.getEmails().split(",");
                        List<String> EmailAddresses = new ArrayList<>(Arrays.asList(resultList));

                        List<String> emails = new ArrayList<>();
                        emails.addAll(EmailAddresses);

                        String subject = "Domestic Route Updated By Id: " + save.getId();

                        Map<String, Object> model = new HashMap<>();
                        model.put("field1", save.getId() != null ? save.getId().toString() : "NIL");
                        model.put("field2", oldDomesticRoute.getOrigin() != null ? oldDomesticRoute.getOrigin() : "NIL");
                        model.put("field3", oldDomesticRoute.getDestination() != null ? oldDomesticRoute.getDestination() : "NIL");
                        model.put("field4", oldDomesticRoute.getRoute() != null ? oldDomesticRoute.getRoute() : "NIL");
                        model.put("field5", oldDomesticRoute.getDriver() != null ? oldDomesticRoute.getDriver() : "NIL");
                        model.put("field6", oldDomesticRoute.getEtd() != null ? oldDomesticRoute.getEtd().toString() : "NIL");
                        model.put("field7", oldDomesticRoute.getEta() != null ? oldDomesticRoute.getEta().toString() : "NIL");
                        model.put("field8", oldDomesticRoute.getDurationLimit() != null ? oldDomesticRoute.getDurationLimit().toString() : "NIL");
                        model.put("field9", oldDomesticRoute.getRemarks() != null ? oldDomesticRoute.getRemarks() : "NIL");
                        model.put("field10", save.getOrigin() != null ? save.getOrigin() : "NIL");
                        model.put("field11", save.getDestination() != null ? save.getDestination() : "NIL");
                        model.put("field12", save.getRoute() != null ? save.getRoute() : "NIL");
                        model.put("field13", save.getDriver() != null ? save.getDriver() : "NIL");
                        model.put("field14", save.getEtd() != null ? save.getEtd().toString() : "NIL");
                        model.put("field15", save.getEta() != null ? save.getEta().toString() : "NIL");
                        model.put("field16", save.getDurationLimit() != null ? save.getDurationLimit().toString() : "NIL");
                        model.put("field17", save.getRemarks() != null ? save.getRemarks() : "NIL");

                        sendEmailsAsync(emails, subject, "domestic-route-update-template.ftl", model);

                    }
                }
            }

            return toDto(save);

        } else {
            throw new RecordNotFoundException(String.format("Domestic Route Not Found By This Id %d", id));
        }
    }

    @Async
    private void sendEmailsAsync(List<String> emails, String subject, String template, Map<String, Object> model) {
        CompletableFuture.runAsync(() -> {
            for (String to : emails) {
                emailService.sendHtmlEmail(to, subject, template, model);
            }
        });
    }
    public DomesticRouteDto getDomesticRouteById(Long id) {
        Optional<DomesticRoute> domesticRoute = domesticRouteRepository.findById(id);
        if(domesticRoute.isPresent()){
            return toDto(domesticRoute.get());
        }else{
            throw new RecordNotFoundException(String.format("Domestic Route Not Found By This Id %d", id));
        }
    }
}
