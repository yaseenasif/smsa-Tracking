package com.example.CargoTracking.service;
import com.example.CargoTracking.criteria.SearchCriteria;
import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.DomesticRouteRepository;
import com.example.CargoTracking.repository.DomesticShipmentRepository;
import com.example.CargoTracking.repository.EmailAddressForRouteRepository;
import com.example.CargoTracking.repository.UserRepository;
import com.example.CargoTracking.specification.DomesticRouteSpecification;
import com.example.CargoTracking.specification.DomesticShipmentSpecification;
import org.modelmapper.ModelMapper;
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
                if (route.getRoute().equals(shipment.getRouteNumber())) {
                    usedRoute.add(route);
                    break;
                }
            }
        }

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
        domesticRoute = domesticRouteRepository.save(domesticRoute);
        return toDto(domesticRoute);
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
        if (domesticRoute.isPresent()) {
            DomesticRoute oldDomesticRoute = domesticRoute.get();
            domesticRoute.get().setOrigin(domesticRouteDto.getOrigin());
            domesticRoute.get().setDestination(domesticRouteDto.getDestination());
            domesticRoute.get().setRoute(domesticRouteDto.getRoute());
            domesticRoute.get().setEtd(domesticRouteDto.getEtd());
            domesticRoute.get().setEta(domesticRouteDto.getEta());
            domesticRoute.get().setDriver(domesticRouteDto.getDriver());
            domesticRoute.get().setDurationLimit(domesticRouteDto.getDurationLimit());
            domesticRoute.get().setRemarks(domesticRouteDto.getRemarks());

            DomesticRoute save = domesticRouteRepository.save(domesticRoute.get());

            EmailAddressForRoutes domesticRouteEmailAddress = emailAddressForRouteRepository.findByType("domestic");
            if(domesticRouteEmailAddress!=null){
                if(domesticRouteEmailAddress.getEmails()!=null || !domesticRouteEmailAddress.getEmails().isEmpty()){
                    String[] resultList = domesticRouteEmailAddress.getEmails().split(",");
                    List<String> EmailAddresses = new ArrayList<>(Arrays.asList(resultList));

                    List<String> emails = new ArrayList<>();
                    emails.addAll(EmailAddresses);

                    String subject = "Domestic Route Updated By Id: " + save.getId();

                    Map<String, Object> model = new HashMap<>();
                    model.put("field1", save.getId().toString());
                    model.put("field2", oldDomesticRoute.getOrigin());
                    model.put("field3", oldDomesticRoute.getDestination());
                    model.put("field4", oldDomesticRoute.getRoute());
                    model.put("field5", oldDomesticRoute.getDriver());
                    model.put("field6", oldDomesticRoute.getEtd().toString());
                    model.put("field7", oldDomesticRoute.getEta().toString());
                    model.put("field8", oldDomesticRoute.getDurationLimit().toString());
                    model.put("field9", oldDomesticRoute.getRemarks());
                    model.put("field10", save.getOrigin());
                    model.put("field11", save.getDestination());
                    model.put("field12", save.getRoute());
                    model.put("field13", save.getDriver());
                    model.put("field14", save.getEtd().toString());
                    model.put("field15", save.getEta().toString());
                    model.put("field16", save.getDurationLimit().toString());
                    model.put("field17", save.getRemarks());

                    sendEmailsAsync(emails, subject, "domestic-route-update-template.ftl", model);

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
