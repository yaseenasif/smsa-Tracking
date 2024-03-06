package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalRoute;
import com.example.CargoTracking.dto.DomesticRouteDto;
import com.example.CargoTracking.dto.FileMetaDataDto;
import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.EmailAddressForRouteRepository;
import com.example.CargoTracking.repository.InternationalRouteRepository;
import com.example.CargoTracking.repository.InternationalShipmentRepository;
import com.example.CargoTracking.specification.InternationalRouteSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class InternationalRouteService {
    @Autowired
    InternationalRouteRepository internationalRouteRepository;
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EmailAddressForRouteRepository emailAddressForRouteRepository;
    @Autowired
    EmailService emailService;

    public List<InternationalRouteDto> findInternationalRouteForAir(String origin, String destination, int trip) {
        List<InternationalRoute> byOriginAndDestination =
                internationalRouteRepository.findByOriginAndDestinationAndTypeAndStatus(origin, destination,"Air",true);
        if(byOriginAndDestination.isEmpty()){
            throw  new RecordNotFoundException(String.format("No routes available against given origin and destination"));
        }

        List<InternationalShipment> internationalShipment =
                internationalShipmentRepository.findByCreatedAtAndType(LocalDate.now(), "By Air");

        List<InternationalRoute> resultList = new ArrayList<>();
        if(internationalShipment.isEmpty()){
            return toDtoList(byOriginAndDestination);
        }
        List<InternationalRoute> usedRoute = new ArrayList<>();
        for (InternationalRoute route: byOriginAndDestination) {
            for(InternationalShipment shipment: internationalShipment){
                if(route.getRoute().equals(shipment.getRouteNumber())){
                    if(shipment.getTrip() == trip){
                        usedRoute.add(route);
                        break;
                    }

                }
            }

        }
        byOriginAndDestination.removeAll(usedRoute);

        if(byOriginAndDestination.isEmpty()){
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(byOriginAndDestination);
    }

    public List<InternationalRouteDto> findInternationalRouteForRoad(String origin, String destination, int trip) {
        List<InternationalRoute> byOriginAndDestination =
                internationalRouteRepository.findByOriginAndDestinationAndTypeAndStatus(origin, destination,"Road",true);
        if(byOriginAndDestination.isEmpty()){
            throw  new RecordNotFoundException(String.format("No routes available against given origin and destination"));
        }
        List<InternationalShipment> internationalShipment =
                internationalShipmentRepository.findByCreatedAtAndType(LocalDate.now(), "By Road");

        List<InternationalRoute> resultList = new ArrayList<>();

        if(internationalShipment.isEmpty()){
            return toDtoList(byOriginAndDestination);
        }

        List<InternationalRoute> usedRoute = new ArrayList<>();
        for (InternationalRoute route:byOriginAndDestination){
            for(InternationalShipment shipment : internationalShipment){
                if(route.getRoute().equals(shipment.getRouteNumber())){
                    if(shipment.getTrip() == trip){
                        usedRoute.add(route);
                        break;
                    }
                }

            }
        }
        byOriginAndDestination.removeAll(usedRoute);

        if(byOriginAndDestination.isEmpty()){
            throw new RecordNotFoundException(String.format("All routes have been used today"));
        }
        return toDtoList(byOriginAndDestination);
    }

    public List<InternationalRouteDto> toDtoList(List<InternationalRoute> internationalRoutes){
        return internationalRoutes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public InternationalRouteDto toDto(InternationalRoute internationalRoute){
        return modelMapper.map(internationalRoute, InternationalRouteDto.class);
    }

    public Page<InternationalRouteDto> findAllInternationalRouteForAir(SearchCriteriaForInternationalRoute searchCriteriaForInternationalRoute,int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<InternationalRoute> specification = InternationalRouteSpecification.getSearchSpecification((searchCriteriaForInternationalRoute));
        Page<InternationalRoute> internationalRoutePage = internationalRouteRepository.findAll(specification,pageable);
        Page<InternationalRouteDto> internationalRouteDtos =internationalRoutePage.map(entity->toDto(entity));
        return internationalRouteDtos;
    }

    public Page<InternationalRouteDto> findAllInternationalRouteForRoad(SearchCriteriaForInternationalRoute searchCriteriaForInternationalRoute,int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<InternationalRoute> specification = InternationalRouteSpecification.getSearchSpecification((searchCriteriaForInternationalRoute));
        Page<InternationalRoute> internationalRoutePage = internationalRouteRepository.findAll(specification,pageable);
        Page<InternationalRouteDto> internationalRouteDtos =internationalRoutePage.map(entity->toDto(entity));
        return internationalRouteDtos;
    }
    public InternationalRouteDto saveInternationalRoute(InternationalRouteDto internationalRouteDto) {
        InternationalRoute internationalRoute = toEntity(internationalRouteDto);
        internationalRoute.setStatus(Boolean.TRUE);
        internationalRoute = internationalRouteRepository.save(internationalRoute);
        return toDto(internationalRoute);
    }
    public InternationalRoute toEntity(InternationalRouteDto internationalRouteDto){
        return modelMapper.map(internationalRouteDto,InternationalRoute.class);
    }
    @Transactional
    public InternationalRouteDto updateInternationalRoute(Long id, InternationalRouteDto internationalRouteDto) {
        Optional<InternationalRoute> internationalRoute = internationalRouteRepository.findById(id);
        if(internationalRoute.isPresent()){
            InternationalRoute oldInternationalRoute = new InternationalRoute();
            oldInternationalRoute.setOrigin(internationalRoute.get().getOrigin());
            oldInternationalRoute.setDestination(internationalRoute.get().getDestination());
            oldInternationalRoute.setRoute(internationalRoute.get().getRoute());
            oldInternationalRoute.setDriverId(internationalRoute.get().getDriverId());
            oldInternationalRoute.setFlight(internationalRoute.get().getFlight());
            oldInternationalRoute.setEta(internationalRoute.get().getEta());
            oldInternationalRoute.setEtd(internationalRoute.get().getEtd());
            oldInternationalRoute.setRemarks(internationalRoute.get().getRemarks());
            oldInternationalRoute.setType(internationalRoute.get().getType());

            internationalRoute.get().setOrigin(internationalRouteDto.getOrigin());
            internationalRoute.get().setDestination(internationalRouteDto.getDestination());
            internationalRoute.get().setRoute(internationalRouteDto.getRoute());
            internationalRoute.get().setDriverId(internationalRouteDto.getDriverId());
            internationalRoute.get().setFlight(internationalRouteDto.getFlight());
            internationalRoute.get().setEta(internationalRouteDto.getEta());
            internationalRoute.get().setEtd(internationalRouteDto.getEtd());
            internationalRoute.get().setRemarks(internationalRouteDto.getRemarks());
            InternationalRoute save = internationalRouteRepository.save(internationalRoute.get());

            Optional<EmailAddressForRoutes> emailAddress1;
            EmailAddressForRoutes emailAddress = null;
            if(save.getType().equals("Air")){
                emailAddress1 = emailAddressForRouteRepository.findById(2L);

            }else{
                emailAddress1 = emailAddressForRouteRepository.findById(3L);
            }
            if(emailAddress1.isPresent()){
                emailAddress=emailAddress1.get();
            }
            if(emailAddress!=null){
                if(emailAddress.getEmails()!=null || !emailAddress.getEmails().isEmpty()){
                    String[] resultList = emailAddress.getEmails().split(",");
                    List<String> EmailAddresses = new ArrayList<>(Arrays.asList(resultList));

                    List<String> emails = new ArrayList<>();
                    emails.addAll(EmailAddresses);

                    String subject = "International Route Updated By Id: " + save.getId();

                    Map<String, Object> model = new HashMap<>();
                    model.put("field1", save.getId() != null ? save.getId().toString() : "NIL");
                    model.put("field2", oldInternationalRoute.getOrigin() != null ? oldInternationalRoute.getOrigin() : "NIL");
                    model.put("field3", oldInternationalRoute.getDestination() != null ? oldInternationalRoute.getDestination() : "NIL");
                    model.put("field4", oldInternationalRoute.getRoute() != null ? oldInternationalRoute.getRoute() : "NIL");
                    model.put("field5", oldInternationalRoute.getDriverId() != null ? oldInternationalRoute.getDriverId() : "NIL");
                    model.put("field6", oldInternationalRoute.getEtd() != null ? oldInternationalRoute.getEtd().toString() : "NIL");
                    model.put("field7", oldInternationalRoute.getEta() != null ? oldInternationalRoute.getEta().toString() : "NIL");
                    model.put("field8", oldInternationalRoute.getFlight() != null ? oldInternationalRoute.getFlight().toString() : "NIL");
                    model.put("field9", oldInternationalRoute.getType() != null ? oldInternationalRoute.getType() : "NIL");
                    model.put("field10", oldInternationalRoute.getRemarks() != null ? oldInternationalRoute.getRemarks() : "NIL");
                    model.put("field11", save.getOrigin() != null ? save.getOrigin() : "NIL");
                    model.put("field12", save.getDestination() != null ? save.getDestination() : "NIL");
                    model.put("field13", save.getRoute() != null ? save.getRoute() : "NIL");
                    model.put("field14", save.getDriverId() != null ? save.getDriverId() : "NIL");
                    model.put("field15", save.getEtd() != null ? save.getEtd().toString() : "NIL");
                    model.put("field16", save.getEta() != null ? save.getEta().toString() : "NIL");
                    model.put("field17", save.getFlight() != null ? save.getFlight().toString() : "NIL");
                    model.put("field18", save.getType() != null ? save.getType().toString() : "NIL");
                    model.put("field19", save.getRemarks() != null ? save.getRemarks() : "NIL");

                    sendEmailsAsync(emails, subject, "international-route-update-template.ftl", model);
                }
            }
            return toDto(save);

        }else{
            throw new RecordNotFoundException(String.format("Shipment Route is not available for id: %d",id));
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

    public ApiResponse deleteInternationalRoute(Long id) {
        Optional<InternationalRoute> internationalRoute = internationalRouteRepository.findById(id);
        if(internationalRoute.isPresent()){
            InternationalRoute route = internationalRoute.get();
            route.setStatus(Boolean.FALSE);
            internationalRouteRepository.save(route);
            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }else{
            throw new RecordNotFoundException(String.format("Shipment Route is not available for id: %d",id));
        }
    }

    public InternationalRouteDto getInternationalRouteById(Long id) {
        Optional<InternationalRoute> internationalRoute = internationalRouteRepository.findById(id);
        if(internationalRoute.isPresent()){
            return toDto(internationalRoute.get());
        }else{
            throw new RecordNotFoundException(String.format("Shipment Route is not available for id: %d",id));
        }
    }
}
