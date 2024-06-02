package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummaryOutbound;
import com.example.CargoTracking.dto.InternationalShipmentDto;
import com.example.CargoTracking.dto.ProductFieldDto;
import com.example.CargoTracking.dto.ProductFieldValuesDto;
import com.example.CargoTracking.dto.SendEmailAddressForOutlookManual;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.exception.UserNotFoundException;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.*;

import com.example.CargoTracking.specification.InternationalShipmentSpecification;
import com.example.CargoTracking.specification.InternationalSummarySpecification;
import com.example.CargoTracking.specification.InternationalSummarySpecificationForOutbound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class InternationalShipmentService {
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    InternationalShipmentHistoryRepository internationalShipmentHistoryRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    StorageService storageService;
    @Autowired
    FileMetaDataRepository fileMetaDataRepository;
    @Autowired
    LocationService locationService;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProductFieldService productFieldService;


    @Transactional
    public InternationalShipmentDto addShipment(InternationalShipmentDto internationalShipmentDto,Long orgLocationId,Long desLocationId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmployeeId(username);

            List<InternationalShipment> all = internationalShipmentRepository.findAll();
            for (InternationalShipment internationalShipment: all) {
                if(internationalShipment.getPreAlertNumber().equals(internationalShipmentDto.getPreAlertNumber())){
                    throw new RecordNotFoundException(String.format("Shipment with the given pre alert number is already exist"));
                }
            }
            InternationalShipment unSaveInternationalShipment = toEntity(internationalShipmentDto);
            unSaveInternationalShipment.setCreatedAt(LocalDate.now());
            unSaveInternationalShipment.setCreatedBy(user);
            unSaveInternationalShipment.setRedFlag(Boolean.FALSE);
            unSaveInternationalShipment.setOriginLocationId(orgLocationId);
            unSaveInternationalShipment.setDestinationLocationId(desLocationId);
            unSaveInternationalShipment.setPreAlertType(unSaveInternationalShipment.getType().equalsIgnoreCase("By Road") ? "International-Road" : "International-Air");
            LocalDateTime currentTime = LocalDateTime.now();
            unSaveInternationalShipment.setCreatedTime(currentTime);
            unSaveInternationalShipment.setUpdatedTime(currentTime);
            unSaveInternationalShipment.setActiveStatus(true);

            InternationalShipment internationalShipment = internationalShipmentRepository
                    .save(unSaveInternationalShipment);


            InternationalShipmentHistory shipmentHistory = InternationalShipmentHistory.builder()
                    .status(internationalShipment.getStatus())
                    .processTime(currentTime)
                    .locationCode(internationalShipment.getOriginCountry())
                    .user(user.getEmployeeId())
                    .type(internationalShipment.getType())
                    .internationalShipment(internationalShipment)
                    .remarks(internationalShipment.getRemarks())
                    .build();

            internationalShipmentHistoryRepository.save(shipmentHistory);

            String originEmails = locationRepository.findById(orgLocationId).get().getOriginEmail();
            String[] resultListOrigin = originEmails.split(",");
            List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOrigin));

            String destinationEmails = locationRepository.findById(desLocationId).get().getDestinationEmail();
            String[] resultListDestination = destinationEmails.split(",");
                List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestination));

            List<String> emails = new ArrayList<>();
            emails.addAll(originEmailAddresses);
            emails.addAll(destinationEmailAddresses);

            String subject;
            Map<String,Object> model = new HashMap<>();
            String template;
            if(internationalShipment.getType().equalsIgnoreCase("By Air") ){
                template = "international-air-email-template.ftl";
                subject = "TSM Pre-Alert(A): "+internationalShipment.getRouteNumber()+"/"+internationalShipment.getFlightNumber().toString()+"/"+internationalShipment.getPreAlertNumber()+"/"+internationalShipment.getEtd();
                model.put("field1",internationalShipment.getCreatedAt().toString());
                model.put("field2",internationalShipment.getReferenceNumber());
                model.put("field3",internationalShipment.getOriginCountry());
                model.put("field4",internationalShipment.getDestinationCountry());
                model.put("field5",internationalShipment.getOriginLocation());
                model.put("field6",internationalShipment.getDestinationLocation());
                model.put("field7",internationalShipment.getNumberOfShipments() != null?internationalShipment.getNumberOfShipments().toString():"0");
                model.put("field8",internationalShipment.getCarrier());
                model.put("field9",String.valueOf(internationalShipment.getPreAlertNumber()));
                model.put("field10",internationalShipment.getFlightNumber().toString());
                model.put("field11",internationalShipment.getNumberOfBags().toString());
                model.put("field12",internationalShipment.getNumberOfPallets().toString());
                model.put("field13",internationalShipment.getActualWeight().toString());
                model.put("field14",internationalShipment.getShipmentMode());
                model.put("field15",internationalShipment.getEtd().toLocalDate().toString());
                model.put("field16",internationalShipment.getEta().toLocalDate().toString());
                model.put("field17",internationalShipment.getEtd().toLocalTime().toString());
                model.put("field18",internationalShipment.getEta().toLocalTime().toString());
                model.put("field19",internationalShipment.getRemarks());
            }else{
                template = "international-road-email-template.ftl";
                subject = "TSM Pre_Alert(R): "+internationalShipment.getRouteNumber()+"/"+internationalShipment.getVehicleType()+"/"+internationalShipment.getPreAlertNumber()+"/"+internationalShipment.getEtd();
                model.put("field1",internationalShipment.getCreatedAt().toString());
                model.put("field2",internationalShipment.getReferenceNumber());
                model.put("field3",internationalShipment.getOriginCountry());
                model.put("field4",internationalShipment.getDestinationCountry());
                model.put("field5",internationalShipment.getOriginLocation());
                model.put("field6",internationalShipment.getDestinationLocation());
                model.put("field7",internationalShipment.getNumberOfShipments() != null?internationalShipment.getNumberOfShipments().toString():"0");
                model.put("field8",internationalShipment.getVehicleType());
                model.put("field9",internationalShipment.getNumberOfBags().toString());
                model.put("field10",internationalShipment.getNumberOfPallets().toString());
                model.put("field11",internationalShipment.getActualWeight().toString());
                model.put("field12",internationalShipment.getShipmentMode());
                model.put("field13",internationalShipment.getEtd().toLocalDate().toString());
                model.put("field14",internationalShipment.getEta().toLocalDate().toString());
                model.put("field15",internationalShipment.getEtd().toLocalTime().toString());
                model.put("field16",internationalShipment.getEta().toLocalTime().toString());
                model.put("field17",internationalShipment.getRemarks());
            }

            emailService.sendHtmlEmail(resultListDestination, resultListOrigin,subject,template,model);
//            sendEmailsAsync(emails, subject, template, model);


            return  toDto(internationalShipment);

        }
        throw new UserNotFoundException(String.format("User not found while creating international shipment"));
    }

    private void sendEmailsAsync(List<String> emails, String subject, String template, Map<String, Object> model) {
        CompletableFuture.runAsync(() -> {
            for (String to : emails) {
                emailService.sendHtmlEmail(to, subject, template, model);
            }
        });
    }

    public List<InternationalShipmentDto> getAll() {
        return toDtoList(internationalShipmentRepository.findAll());
    }

    public Page<InternationalShipmentDto>   getAllByUserAndForAir(SearchCriteriaForInternationalShipment searchCriteriaForInternationalSummary, int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmployeeId(username);
            String role ="";
            for (Roles roleList:user.getRoles()) {
              Optional<Roles> roles = Optional.ofNullable(roleRepository
                    .findByName(roleList.getName())
                    .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

              role = roles.get().getName();
            }
            if(role.equals("ROLE_ADMIN")){
                searchCriteriaForInternationalSummary.setUser(null);
                searchCriteriaForInternationalSummary.setType("By Air");

                Specification<InternationalShipment> internationalShipmentSpecification= InternationalShipmentSpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
                Page<InternationalShipment> InternationalShipmentPage = internationalShipmentRepository.findAll(internationalShipmentSpecification,pageable);
                Page<InternationalShipmentDto> internationalShipmentDtoPage = InternationalShipmentPage.map(entity->toDto(entity));

                return internationalShipmentDtoPage;
            }else{
              searchCriteriaForInternationalSummary.setUser(user);
              searchCriteriaForInternationalSummary.setType("By Air");

              Specification<InternationalShipment> internationalShipmentSpecification= InternationalShipmentSpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
              Page<InternationalShipment> InternationalShipmentPage = internationalShipmentRepository.findAll(internationalShipmentSpecification,pageable);
              Page<InternationalShipmentDto> internationalShipmentDtoPage = InternationalShipmentPage.map(entity->toDto(entity));

              return internationalShipmentDtoPage;
            }
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public Page<InternationalShipmentDto> getAllByUserAndForRoad(SearchCriteriaForInternationalShipment searchCriteriaForInternationalSummary, int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmployeeId(username);
          String role ="";
          for (Roles roleList:user.getRoles()) {
            Optional<Roles> roles = Optional.ofNullable(roleRepository
                    .findByName(roleList.getName())
                    .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

            role = roles.get().getName();
          }
          if(role.equals("ROLE_ADMIN")){
            searchCriteriaForInternationalSummary.setUser(null);
            searchCriteriaForInternationalSummary.setType("By Road");
            Specification<InternationalShipment> internationalShipmentSpecification= InternationalShipmentSpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
            Page<InternationalShipment> InternationalShipmentPage = internationalShipmentRepository.findAll(internationalShipmentSpecification,pageable);
            Page<InternationalShipmentDto> internationalShipmentDtoPage = InternationalShipmentPage.map(entity->toDto(entity));

            return internationalShipmentDtoPage;
          }else{
            searchCriteriaForInternationalSummary.setUser(user);
            searchCriteriaForInternationalSummary.setType("By Road");
            Specification<InternationalShipment> internationalShipmentSpecification= InternationalShipmentSpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
            Page<InternationalShipment> InternationalShipmentPage = internationalShipmentRepository.findAll(internationalShipmentSpecification,pageable);
            Page<InternationalShipmentDto> internationalShipmentDtoPage = InternationalShipmentPage.map(entity->toDto(entity));

            return internationalShipmentDtoPage;
          }
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public InternationalShipmentDto getById(Long id) {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        if(internationalShipment.isPresent()){
            return toDto(internationalShipment.get());
        }
        throw new RecordNotFoundException(String.format("International shipment Not Found By This Id %d",id));
    }

    public SendEmailAddressForOutlookManual getInternationalShipmentEmailAddressById(Long id) {
        Optional<InternationalShipment> internationalShipmentOptional = internationalShipmentRepository.findById(id);
        if(internationalShipmentOptional.isPresent()){
            InternationalShipment internationalShipment = internationalShipmentOptional.get();
            SendEmailAddressForOutlookManual sendEmailAddressForOutlookManual = new SendEmailAddressForOutlookManual();
            Optional<Location> originLocation = locationRepository.findById(internationalShipment.getOriginLocationId());
            Optional<Location> destinationLocation = locationRepository.findById(internationalShipment.getDestinationLocationId());
            String subject;
            if(internationalShipment.getType().equalsIgnoreCase("By Air") ){
                subject = "TSM Pre-Alert(A): "+internationalShipment.getRouteNumber()+"/"+internationalShipment.getFlightNumber().toString()+"/"+internationalShipment.getPreAlertNumber()+"/"+internationalShipment.getEtd();
            }else{
                subject = "TSM Pre_Alert(R): "+internationalShipment.getRouteNumber()+"/"+internationalShipment.getVehicleType()+"/"+internationalShipment.getPreAlertNumber()+"/"+internationalShipment.getEtd();
            }

            sendEmailAddressForOutlookManual.setTo(originLocation.get().getOriginEmail());
            sendEmailAddressForOutlookManual.setCc(destinationLocation.get().getDestinationEmail());
            sendEmailAddressForOutlookManual.setSubject(subject);
            return sendEmailAddressForOutlookManual;
        }
        throw new RecordNotFoundException(String.format("International shipment Not Found By This Id %d",id));
    }

//    @Scheduled(fixedRate = 20 * 60 * 1000)
//    public void redFlag() {
//        List<InternationalShipment> internationalShipmentList = internationalShipmentRepository.findAll();
//        try {
//            LocalDateTime currentDateTime = LocalDateTime.now();
//
//            if(!internationalShipmentList.isEmpty()){
//                for (InternationalShipment entity : internationalShipmentList) {
//                    //field ko light red kerna hai
//
//                    // arrived se 6 hours tak clear nahi hua to red ayee ga
//                    if(entity.getArrivedTime()!=null && !entity.getRedFlag() && entity.getClearedTime() == null){
//                        Duration duration = Duration.between(entity.getArrivedTime(), currentDateTime);
//                        if(duration.toHours()>6){
//                            entity.setRedFlag(Boolean.TRUE);
//                        }
//                    }
//                    //eta se clear nahi hua hai 12 ganhte tak to red and
//                    if(!entity.getRedFlag() && entity.getClearedTime() == null){
//                        Duration duration = Duration.between(entity.getEta(),currentDateTime);
//                        if(duration.toHours()>12){
//                            entity.setRedFlag(Boolean.TRUE);
//                        }
//                    }
//                }
//                internationalShipmentRepository.saveAll(internationalShipmentList);
//            }
//            //escalation bhi eta se jaee gi
//            List<InternationalShipment> internationalShipmentList1 = internationalShipmentRepository.findAll();
//            if(!internationalShipmentList1.isEmpty()){
//                for(InternationalShipment shipment: internationalShipmentList1){
//                    if(shipment.getArrivedTime() != null && shipment.getClearedTime() == null){
//                        Duration duration = Duration.between(shipment.getEta(), currentDateTime);
//
//                        if(duration.toMinutes() >= 720 && duration.toMinutes() <= 1440){
//                            if(!shipment.isEscalationFlagOne()){
//                                List<String> emails = new ArrayList<>();
//                                String originEmails = locationRepository.findById(shipment.getOriginLocationId()).get()
//                                        .getOriginEscalationLevel1();
//                                String[] resultListOriginEscalation = originEmails.split(",");
//                                List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOriginEscalation));
//
//                                String destinationEmails = locationRepository.findById(shipment.getDestinationLocationId()).get()
//                                        .getDestinationEscalationLevel1();
//                                String[] resultListDestinationEscalation = destinationEmails.split(",");
//                                List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestinationEscalation));
//
//                                if(originEmailAddresses.size()>1){
//                                    emails.addAll(originEmailAddresses);
//                                }
//                                if(destinationEmailAddresses.size()>1){
//                                    emails.addAll(destinationEmailAddresses);
//                                }
//
//                                String subject;
//                                if(shipment.getType().equalsIgnoreCase("By Air")){
//                                    subject = "TSM 1st Escalation (A): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getPreAlertNumber() +"/"+ shipment.getEtd();
//                                }else{
//                                    subject = "TSM 1st Escalation (R): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getPreAlertNumber() +"/"+ shipment.getEtd();
//
//                                }
//                                Map<String,Object> model = new HashMap<>();
//                                model.put("field1",shipment.getReferenceNumber());
//                                model.put("field2",shipment.getDestinationLocation());
//
//                                sendEmailsAsync(emails,subject,"shipment-delay-email-template.ftl",model);
//
//                                shipment.setEscalationFlagOne(true);
//                                internationalShipmentRepository.save(shipment);
//                            }
//                        }
//                        if(duration.toMinutes() >= 1441 && duration.toMinutes() <= 2880 ){
//                            if(!shipment.isEscalationFlagTwo()){
//                                List<String> emails = new ArrayList<>();
//                                String originEmails = locationRepository.findById(shipment.getOriginLocationId()).get()
//                                        .getOriginEscalationLevel2();
//                                String[] resultListOriginEscalation = originEmails.split(",");
//                                List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOriginEscalation));
//
//                                String destinationEmails = locationRepository.findById(shipment.getDestinationLocationId()).get()
//                                        .getDestinationEscalationLevel2();
//                                String[] resultListDestinationEscalation = destinationEmails.split(",");
//                                List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestinationEscalation));
//
//                                if(originEmailAddresses.size()>1){
//                                    emails.addAll(originEmailAddresses);
//                                }
//                                if(destinationEmailAddresses.size()>1){
//                                    emails.addAll(destinationEmailAddresses);
//                                }
//                                String subject;
//                                if(shipment.getType().equalsIgnoreCase("By Air")){
//                                    subject = "TSM 2nd Escalation (A): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getPreAlertNumber() +"/"+ shipment.getEtd();
//                                }else{
//                                    subject = "TSM 2nd Escalation (R): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getPreAlertNumber() +"/"+ shipment.getEtd();
//
//                                }
//                                Map<String,Object> model = new HashMap<>();
//                                model.put("field1",shipment.getReferenceNumber());
//                                model.put("field2",shipment.getDestinationLocation());
//                                sendEmailsAsync(emails,subject,"shipment-delay-email-template.ftl",model);
//
//                                shipment.setEscalationFlagTwo(true);
//                                internationalShipmentRepository.save(shipment);
//                            }
//                        }
//                        if(duration.toMinutes() >= 2881){
//                            if(!shipment.isEscalationFlagThree()){
//                                List<String> emails = new ArrayList<>();
//                                String originEmails = locationRepository.findById(shipment.getOriginLocationId()).get()
//                                        .getOriginEscalationLevel3();
//                                String[] resultListOriginEscalation = originEmails.split(",");
//                                List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOriginEscalation));
//
//                                String destinationEmails = locationRepository.findById(shipment.getDestinationLocationId()).get()
//                                        .getDestinationEscalationLevel3();
//                                String[] resultListDestinationEscalation = destinationEmails.split(",");
//                                List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestinationEscalation));
//
//                                if(originEmailAddresses.size()>1){
//                                    emails.addAll(originEmailAddresses);
//                                }
//                                if(destinationEmailAddresses.size()>1){
//                                    emails.addAll(destinationEmailAddresses);
//                                }
//                                String subject;
//                                if(shipment.getType().equalsIgnoreCase("By Air")){
//                                    subject = "TSM 3rd Escalation (A): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getPreAlertNumber() +"/"+ shipment.getEtd();
//
//                                }else{
//                                    subject = "TSM 3rd Escalation (R): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getPreAlertNumber() +"/"+ shipment.getEtd();
//
//                                }
//                                Map<String,Object> model = new HashMap<>();
//                                model.put("field1",shipment.getReferenceNumber());
//                                model.put("field2",shipment.getDestinationLocation());
//                                sendEmailsAsync(emails,subject,"shipment-delay-email-template.ftl",model);
//
//                                shipment.setEscalationFlagThree(true);
//                                internationalShipmentRepository.save(shipment);
//                            }
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Collections.emptyList();
//        }
//    }

    private List<InternationalShipmentDto> toDtoList(List<InternationalShipment> internationalShipmentList){
        return internationalShipmentList.stream().map(this::toDto).collect(Collectors.toList());
    }

    private InternationalShipment toEntity(InternationalShipmentDto internationalShipmentDto){
        return modelMapper.map(internationalShipmentDto,InternationalShipment.class);
    }

    private InternationalShipmentDto toDto(InternationalShipment internationalShipment){
        return modelMapper.map(internationalShipment,InternationalShipmentDto.class);
    }

    public Page<InternationalShipmentDto> getInternationalOutBoundSummeryForAir(SearchCriteriaForInternationalSummaryOutbound searchCriteriaForInternationalSummary,
                                                                                int page, int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmployeeId(username);
            if(searchCriteriaForInternationalSummary.getOrigin().isEmpty()){
                Set<Location> userLocations = user.getLocations();
                if (!userLocations.isEmpty()) {
                    Set<String> internatoionalAirLocationsNamePresentInUser = userLocations.stream()
                            .filter(location -> "International Air".equals(location.getType()))
                            .map(Location::getLocationName)
                            .collect(Collectors.toSet());
                    searchCriteriaForInternationalSummary.setOrigin(internatoionalAirLocationsNamePresentInUser);
                }else{
                    searchCriteriaForInternationalSummary.setOrigin(Collections.emptySet());
                }
            }
            searchCriteriaForInternationalSummary.setType("By Air");
            Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecificationForOutbound.getSearchSpecification(searchCriteriaForInternationalSummary);
            Page<InternationalShipment> internationalShipmentPage =
                    internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
            Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );

            return internationalShipmentPageDto;
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public Page<InternationalShipmentDto> getInternationalInBoundSummeryForAir(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
                                                                               int page, int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmployeeId(username);
            if(searchCriteriaForInternationalSummary.getDestinations().isEmpty()){
              Set<Location> userLocations = user.getLocations();
              if (!userLocations.isEmpty()) {
                Set<String> internatoionalAirLocationsNamePresentInUser = userLocations.stream()
                        .filter(location -> "International Air".equals(location.getType()))
                        .map(Location::getLocationName)
                        .collect(Collectors.toSet());
                searchCriteriaForInternationalSummary.setDestinations(internatoionalAirLocationsNamePresentInUser);
              }else{
                searchCriteriaForInternationalSummary.setDestinations(Collections.emptySet());
              }
            }
            searchCriteriaForInternationalSummary.setType("By Air");
                Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
                Page<InternationalShipment> internationalShipmentPage =
                        internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
                Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );

                return internationalShipmentPageDto;
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public Page<InternationalShipmentDto> getInternationalOutBoundSummeryForRoad(SearchCriteriaForInternationalSummaryOutbound searchCriteriaForInternationalSummary,
                                                                                int page, int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmployeeId(username);
            if(searchCriteriaForInternationalSummary.getOrigin().isEmpty()){
                Set<Location> userLocations = user.getLocations();
                if (!userLocations.isEmpty()) {
                    Set<String> internatoionalRoadLocationsNamePresentInUser = userLocations.stream()
                            .filter(location -> "International Road".equals(location.getType()))
                            .map(Location::getLocationName)
                            .collect(Collectors.toSet());
                    searchCriteriaForInternationalSummary.setOrigin(internatoionalRoadLocationsNamePresentInUser);
                }else{
                    searchCriteriaForInternationalSummary.setOrigin(Collections.emptySet());
                }
            }
            searchCriteriaForInternationalSummary.setType("By Road");

            Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecificationForOutbound.getSearchSpecification(searchCriteriaForInternationalSummary);
            Page<InternationalShipment> internationalShipmentPage =
                    internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
            Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );

            return internationalShipmentPageDto;
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public Page<InternationalShipmentDto> getInternationalInBoundSummeryForRoad(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
                                                                                int page, int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmployeeId(username);
          if(searchCriteriaForInternationalSummary.getDestinations().isEmpty()){
            Set<Location> userLocations = user.getLocations();
            if (!userLocations.isEmpty()) {
              Set<String> internatoionalRoadLocationsNamePresentInUser = userLocations.stream()
                      .filter(location -> "International Road".equals(location.getType()))
                      .map(Location::getLocationName)
                      .collect(Collectors.toSet());
              searchCriteriaForInternationalSummary.setDestinations(internatoionalRoadLocationsNamePresentInUser);
            }else{
              searchCriteriaForInternationalSummary.setDestinations(Collections.emptySet());
            }
          }
          searchCriteriaForInternationalSummary.setType("By Road");

          Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
          Page<InternationalShipment> internationalShipmentPage =
                  internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
          Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );

          return internationalShipmentPageDto;
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    @Transactional
    public InternationalShipmentDto updateInternationalShipment(Long id, InternationalShipmentDto internationalShipmentDto,Long orgLocationId,Long desLocationId) {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        if(internationalShipment.isPresent()){
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof UserDetails){
                String username = ((UserDetails) principal).getUsername();
                User user = userRepository.findByEmployeeId(username);
                List<InternationalShipment> all = internationalShipmentRepository.findAll();
                for (InternationalShipment internationalShipmentForPreAlertNumber: all) {
                    if(internationalShipmentForPreAlertNumber.getPreAlertNumber().equals(internationalShipmentDto.getPreAlertNumber())){
                        if(internationalShipment.get().getId() != internationalShipmentDto.getId()){
                            throw new RecordNotFoundException(String.format("Shipment with the given pre alert number is already exist"));
                        }
                    }
                }

                internationalShipment.get().setUpdatedAt(LocalDate.now());
                internationalShipment.get().setUpdatedBy(user);
                internationalShipment.get().setOriginLocationId(orgLocationId);
                internationalShipment.get().setDestinationLocationId(desLocationId);
                internationalShipment.get().setOriginCountry(internationalShipmentDto.getOriginCountry());

                internationalShipment.get().setNumberOfBagsReceived(internationalShipmentDto.getNumberOfBagsReceived());
                internationalShipment.get().setNumberOfPalletsReceived(internationalShipmentDto.getNumberOfPalletsReceived());

                internationalShipment.get().setOriginFacility(internationalShipmentDto.getOriginFacility());
                internationalShipment.get().setDestinationFacility(internationalShipmentDto.getDestinationFacility());
                internationalShipment.get().setOriginLocation(internationalShipmentDto.getOriginLocation());
                internationalShipment.get().setRefrigeratedTruck(internationalShipmentDto.getRefrigeratedTruck());
                internationalShipment.get().setType(internationalShipmentDto.getType());
                internationalShipment.get().setShipmentMode(internationalShipmentDto.getShipmentMode());
                internationalShipment.get().setPreAlertNumber(internationalShipmentDto.getPreAlertNumber());
                internationalShipment.get().setDestinationCountry(internationalShipmentDto.getDestinationCountry());
                internationalShipment.get().setDestinationLocation(internationalShipmentDto.getDestinationLocation());
                internationalShipment.get().setCarrier(internationalShipmentDto.getCarrier());

                internationalShipment.get().setEtd(internationalShipmentDto.getEtd());
                internationalShipment.get().setEta(internationalShipmentDto.getEta());
                internationalShipment.get().setAtd(internationalShipmentDto.getAtd());
                internationalShipment.get().setFlightNumber(internationalShipmentDto.getFlightNumber());
                internationalShipment.get().setNumberOfShipments(internationalShipmentDto.getNumberOfShipments());

                internationalShipment.get().setActualWeight(internationalShipmentDto.getActualWeight());
                internationalShipment.get().setDriverName(internationalShipmentDto.getDriverName());
                internationalShipment.get().setDriverContact(internationalShipmentDto.getDriverContact());
                internationalShipment.get().setReferenceNumber(internationalShipmentDto.getReferenceNumber());
                internationalShipment.get().setVehicleType(internationalShipmentDto.getVehicleType());
                internationalShipment.get().setNumberOfPallets(internationalShipmentDto.getNumberOfPallets());
                internationalShipment.get().setNumberOfBags(internationalShipmentDto.getNumberOfBags());
                internationalShipment.get().setVehicleNumber(internationalShipmentDto.getVehicleNumber());
                internationalShipment.get().setTagNumber(internationalShipmentDto.getTagNumber());
                internationalShipment.get().setSealNumber(internationalShipmentDto.getSealNumber());
                internationalShipment.get().setAttachments(internationalShipmentDto.getAttachments());
                internationalShipment.get().setNumberOfBoxes(internationalShipmentDto.getNumberOfBoxes());
                internationalShipment.get().setAta(internationalShipmentDto.getAta());
                internationalShipment.get().setTotalShipments(internationalShipmentDto.getTotalShipments());
                internationalShipment.get().setOverages(internationalShipmentDto.getOverages());
                internationalShipment.get().setOverageAWBs(internationalShipmentDto.getOverageAWBs());
                internationalShipment.get().setReceived(internationalShipmentDto.getReceived());
                internationalShipment.get().setShortages(internationalShipmentDto.getShortages());
                internationalShipment.get().setShortageAWBs(internationalShipmentDto.getShortageAWBs());
                internationalShipment.get().setRouteNumber(internationalShipmentDto.getRouteNumber());
                internationalShipment.get().setDamage(internationalShipmentDto.getDamage());
                internationalShipment.get().setDamageAwbs(internationalShipmentDto.getDamageAwbs());
                LocalDateTime currentDateTime = LocalDateTime.now();
                internationalShipment.get().setUpdatedTime(currentDateTime);

                if(internationalShipmentDto.getStatus().equalsIgnoreCase("Arrived")){
                    internationalShipment.get().setArrivedTime(currentDateTime);
                }
                if(internationalShipmentDto.getAta()!=null){
                    Duration duration = Duration.between( internationalShipment.get().getAtd(), internationalShipmentDto.getAta());
                    internationalShipment.get().setTransitTimeTaken(duration.toMinutes());
                }
                if(internationalShipmentDto.getStatus().equalsIgnoreCase("Cleared")){
                    internationalShipment.get().setClearedTime(currentDateTime);
                    if(internationalShipment.get().getRedFlag()){
                        internationalShipment.get().setRedFlag(false);
                    }
                }
                InternationalShipment save;
                if(!internationalShipment.get().getStatus().equals(internationalShipmentDto.getStatus())){
                    internationalShipment.get().setStatus(internationalShipmentDto.getStatus());
                    if(!internationalShipment.get().getRemarks().equalsIgnoreCase(internationalShipmentDto.getRemarks())){
                        internationalShipment.get().setRemarks(internationalShipmentDto.getRemarks());
                    }else{
                        internationalShipment.get().setRemarks("");
                    }

                    save = internationalShipmentRepository.save(internationalShipment.get());

                    InternationalShipmentHistory shipmentHistory = InternationalShipmentHistory.builder()
                            .status(save.getStatus())
                            .processTime(currentDateTime)
                            .locationCode(save.getOriginCountry())
                            .user(user.getEmployeeId())
                            .type(save.getType())
                            .internationalShipment(save)
                            .remarks(save.getRemarks())
                            .build();

                    internationalShipmentHistoryRepository.save(shipmentHistory);

                }else{
                    save = internationalShipmentRepository.save(internationalShipment.get());
                }
                String originEmails = locationRepository.findById(save.getOriginLocationId()).get()
                        .getOriginEmail();
                String[] resultListOrigin = originEmails.split(",");
                List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOrigin));

                String destinationEmails = locationRepository.findById(save.getDestinationLocationId()).get()
                        .getDestinationEmail();
                String[] resultListDestination = destinationEmails.split(",");
                List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestination));


                List<String> emails = new ArrayList<>();
                emails.addAll(originEmailAddresses);
                emails.addAll(destinationEmailAddresses);
                if (save.getStatus().equalsIgnoreCase("Invoicing Completed")) {
                    Map<String, Object> model = new HashMap<>();
                    String subject = "CD Invoice Creation";
                    sendEmailsAsync(emails, subject, "paid-template.ftl", model);
                }

                if (save.getOverages() != null && save.getShortages() != null &&
                        (save.getOverages() > 0 || save.getShortages() > 0)) {


                    String subject;

                    if(save.getType().equalsIgnoreCase("By Air") ){
                        subject = "TSM Pre-Alert(A): "+save.getRouteNumber()+"/"+save.getFlightNumber().toString()+"/"+save.getPreAlertNumber()+"/"+save.getEtd()+"/Report";
                    }else{
                        subject = "TSM Pre_Alert(R): "+save.getRouteNumber()+"/"+save.getVehicleType()+"/"+save.getPreAlertNumber()+"/"+save.getEtd()+"/Report";
                    }

                    Map<String, Object> model = new HashMap<>();
                    model.put("field1", save.getArrivedTime() != null ? save.getArrivedTime().toLocalDate().toString() : "NIL");
                    model.put("field2", save.getNumberOfBags() != null && save.getNumberOfBags() != 0 ? save.getNumberOfBags().toString() : "NIL");
                    model.put("field3", save.getNumberOfBagsReceived() != null && save.getNumberOfBagsReceived() != 0 ? save.getNumberOfBagsReceived().toString() : "NIL");
                    model.put("field4", save.getTotalShipments() != null && save.getTotalShipments() != 0 ? save.getTotalShipments().toString() : "NIL");
                    model.put("field5", save.getReceived() != null && save.getReceived() != 0 ? save.getReceived().toString() : "NIL");
                    model.put("field6", save.getNumberOfPallets() != null && save.getNumberOfPallets() != 0 ? save.getNumberOfPallets().toString() : "NIL");
                    model.put("field7", save.getNumberOfPalletsReceived() != null && save.getNumberOfPalletsReceived() != 0 ? save.getNumberOfPalletsReceived().toString() : "NIL");
                    model.put("field8", save.getShortages() != null && save.getShortages() != 0 ? save.getShortages().toString() : "NIL");
                    model.put("field9", (save.getShortageAWBs() != null && !save.getShortageAWBs().isEmpty()) ? save.getShortageAWBs() : "NIL");
                    model.put("field10", save.getOverages() != null && save.getOverages() != 0 ? save.getOverages().toString() : "NIL");
                    model.put("field11", (save.getOverageAWBs() != null && !save.getOverageAWBs().isEmpty()) ? save.getOverageAWBs() : "NIL");
                    model.put("field12", save.getDamage() != null && save.getDamage() != 0 ? save.getDamage().toString() : "NIL");
                    model.put("field13", (save.getDamageAwbs() != null && !save.getDamageAwbs().isEmpty()) ? save.getDamageAwbs() : "NIL");

                    sendEmailsAsync(emails, subject, "overages-and-shortages-template.ftl", model);
                }
                return toDto(save);
            }else{
                throw new UserNotFoundException(String.format("User not found"));
            }

        }else{
            throw new RecordNotFoundException(String.format("International shipment Not Found By This Id %d",id));
        }
    }

    public ApiResponse addAttachment(Long id, String attachementType, List<MultipartFile> files) throws IOException {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        InternationalShipment internationalShipment_ = internationalShipment.get();

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            String uniqueFileName = generateUniqueFileName(originalFileName);

            FileMetaData byFileName = fileMetaDataRepository.findByFileName(uniqueFileName);

            if (byFileName == null) {
                String fileUrl = storageService.uploadFile(file.getBytes(), uniqueFileName);

                FileMetaData fileMetaData = new FileMetaData();
                fileMetaData.setFileUrl(fileUrl);
                fileMetaData.setFileExtension(fileExtension);
                fileMetaData.setFileName(uniqueFileName);
                fileMetaData.setAttachmentType(attachementType);
                fileMetaData.setInternationalShipment(internationalShipment.get());

                fileMetaDataRepository.save(fileMetaData);

            }
        }
        if ("Import Bayan HV".equals(attachementType)) {
            if ("By Air".equals(internationalShipment_.getType())) {
                ProductFieldDto productFieldDto = productFieldService.findByName("International_Air_Inbound_Email_Address_For_Attachments");
                String subject = "Payment TSM Pre-Alert(A): " + internationalShipment_.getRouteNumber() + "/" + internationalShipment_.getFlightNumber().toString() + "/" + internationalShipment_.getPreAlertNumber() + "/" + internationalShipment_.getEtd();
                Map<String, Object> model = new HashMap<>();
                List<String> emailsAddress = new ArrayList<>();
                for (ProductFieldValuesDto productFieldValuesDto : productFieldDto.getProductFieldValuesList()) {
                    emailsAddress.add(productFieldValuesDto.getName());
                }
                sendEmailsAsync(emailsAddress, subject, "international-upload-attachment-template.ftl", model);
            } else {
                ProductFieldDto productFieldDto = productFieldService.findByName("International_Road_Inbound_Email_Address_For_Attachments");
                String subject = "TSM Pre_Alert(R): " + internationalShipment_.getRouteNumber() + "/" + internationalShipment_.getVehicleType() + "/" + internationalShipment_.getPreAlertNumber() + "/" + internationalShipment_.getEtd();
                Map<String, Object> model = new HashMap<>();
                List<String> emailsAddress = new ArrayList<>();
                for (ProductFieldValuesDto productFieldValuesDto : productFieldDto.getProductFieldValuesList()) {
                    emailsAddress.add(productFieldValuesDto.getName());
                }
                sendEmailsAsync(emailsAddress, subject, "international-upload-attachment-template.ftl", model);
            }
        }


        return ApiResponse.builder()
                .message("File uploaded to the server successfully")
                .statusCode(HttpStatus.OK.value())
                .result(Collections.emptyList())
                .build();

    }

    private String generateUniqueFileName(String originalFileName) {
        long unixTimestamp = System.currentTimeMillis();

        String timestamp = String.valueOf(unixTimestamp);

        int lastIndex = originalFileName.lastIndexOf(".");
        String fileNameWithoutExtension = originalFileName.substring(0, lastIndex);
        String fileExtension = originalFileName.substring(lastIndex);
        return fileNameWithoutExtension + "_" + timestamp + fileExtension;
    }


    public ApiResponse deleteInternationalShipment(Long id) {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        if(internationalShipment.isPresent()){
            InternationalShipment shipment = internationalShipment.get();
            shipment.setActiveStatus(Boolean.FALSE);
            internationalShipmentRepository.save(shipment);

            return ApiResponse.builder()
                    .message("Record delete successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }
        throw new RecordNotFoundException(String.format("Domestic Shipment not found by this id => %d",id));
    }

    public Map<String, Integer> getAllDashboardDataCountForAir(Integer year) {
        Map<String, Integer> dashboardData = new HashMap<>();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmployeeId(userDetails.getUsername());

        String role = user.getRoles().stream()
                .map(Roles::getName)
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));

        Specification<InternationalShipment> specification;
        if (role.equals("ROLE_ADMIN")) {
            specification = InternationalShipmentSpecification.withCreatedYearUserAndType(year, null,"By Air");
        } else {
            specification = InternationalShipmentSpecification.withCreatedYearUserAndType(year, user,"By Air");
        }

        List<InternationalShipment> shipments = internationalShipmentRepository.findAll(specification);

        int outboundCount = shipments.size();
        int totalShipments = shipments.stream()
                .mapToInt(InternationalShipment::getNumberOfShipments)
                .sum();

        dashboardData.put("Outbounds", outboundCount);
        dashboardData.put("TotalShipments", totalShipments);

        Set<String> userLocations = user.getLocations().stream()
                .filter(location -> "International Air".equals(location.getType()))
                .map(Location::getLocationName)
                .collect(Collectors.toSet());

        if (!userLocations.isEmpty()) {
            Specification<InternationalShipment> inboundSpecification =
                    InternationalShipmentSpecification.withDestinationLocationsAndActive(year, userLocations,"By Air");
            int inboundCount = (int) internationalShipmentRepository.count(inboundSpecification);
            dashboardData.put("Inbounds", inboundCount);
        } else {
            dashboardData.put("Inbounds", 0);
        }

        return dashboardData;
    }

    public Map<String, Integer> getAllDashboardDataCountForRoad(Integer year) {
        Map<String, Integer> dashboardData = new HashMap<>();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmployeeId(userDetails.getUsername());

        String role = user.getRoles().stream()
                .map(Roles::getName)
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));

        Specification<InternationalShipment> specification;
        if (role.equals("ROLE_ADMIN")) {
            specification = InternationalShipmentSpecification.withCreatedYearUserAndType(year, null,"By Road");
        } else {
            specification = InternationalShipmentSpecification.withCreatedYearUserAndType(year, user,"By Road");
        }

        List<InternationalShipment> shipments = internationalShipmentRepository.findAll(specification);

        int outboundCount = shipments.size();
        int totalShipments = shipments.stream()
                .mapToInt(InternationalShipment::getNumberOfShipments)
                .sum();

        dashboardData.put("Outbounds", outboundCount);
        dashboardData.put("TotalShipments", totalShipments);

        Set<String> userLocations = user.getLocations().stream()
                .filter(location -> "International Road".equals(location.getType()))
                .map(Location::getLocationName)
                .collect(Collectors.toSet());

        if (!userLocations.isEmpty()) {
            Specification<InternationalShipment> inboundSpecification =
                    InternationalShipmentSpecification.withDestinationLocationsAndActive(year, userLocations,"By Road");
            int inboundCount = (int) internationalShipmentRepository.count(inboundSpecification);
            dashboardData.put("Inbounds", inboundCount);
        } else {
            dashboardData.put("Inbounds", 0);
        }

        return dashboardData;
    }
//
//    public Map<String, Integer> lowAndHighVolumeWithLocationForInboundForInternationalAir(Integer year) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.findByEmployeeId(userDetails.getUsername());
//        Set<String> userLocations = user.getLocations().stream()
//                .filter(location -> "International Air".equals(location.getType()))
//                .map(Location::getLocationName)
//                .collect(Collectors.toSet());
//        Map<String, Integer> inboundMap = new HashMap<>();
//        if (!userLocations.isEmpty()) {
//            Specification<InternationalShipment> inboundSpecification =
//                    InternationalShipmentSpecification.withDestinationLocationsAndActive(year, userLocations,"By Air");
//            List<InternationalShipment> all = internationalShipmentRepository.findAll(inboundSpecification);
//            for (InternationalShipment internationalShipment : all) {
//                String destinationLocation = internationalShipment.getDestinationLocation();
//                if (inboundMap.containsKey(destinationLocation)) {
//                    inboundMap.put(destinationLocation, inboundMap.get(destinationLocation) + 1);
//                } else {
//                    inboundMap.put(destinationLocation, 1);
//                }
//            }
//        }
//        return inboundMap;
//    }
//
//    public Map<String, Integer> lowAndHighVolumeWithLocationForInboundForInternationalRoad(Integer year) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.findByEmployeeId(userDetails.getUsername());
//        Set<String> userLocations = user.getLocations().stream()
//                .filter(location -> "International Road".equals(location.getType()))
//                .map(Location::getLocationName)
//                .collect(Collectors.toSet());
//        Map<String, Integer> inboundMap = new HashMap<>();
//        if (!userLocations.isEmpty()) {
//            Specification<InternationalShipment> inboundSpecification =
//                    InternationalShipmentSpecification.withDestinationLocationsAndActive(year, userLocations,"By Road");
//            List<InternationalShipment> all = internationalShipmentRepository.findAll(inboundSpecification);
//            for (InternationalShipment internationalShipment : all) {
//                String destinationLocation = internationalShipment.getDestinationLocation();
//                if (inboundMap.containsKey(destinationLocation)) {
//                    inboundMap.put(destinationLocation, inboundMap.get(destinationLocation) + 1);
//                } else {
//                    inboundMap.put(destinationLocation, 1);
//                }
//            }
//        }
//        return inboundMap;
//    }
//
//    public Map<String, Integer> lowAndHighVolumeWithLocationForOutboundForInternationalAir(Integer year) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.findByEmployeeId(userDetails.getUsername());
//
//        String role = user.getRoles().stream()
//                .map(Roles::getName)
//                .findFirst()
//                .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));
//
//        Specification<InternationalShipment> specification;
//        if (role.equals("ROLE_ADMIN")) {
//            specification = InternationalShipmentSpecification.withCreatedYearUserAndType(year, null,"By Air");
//        } else {
//            specification = InternationalShipmentSpecification.withCreatedYearUserAndType(year, user,"By Air");
//        }
//
//        List<InternationalShipment> shipments = internationalShipmentRepository.findAll(specification);
//
//        Map<String, Integer> outboundMap = new HashMap<>();
//
//        for (InternationalShipment internationalShipment : shipments) {
//            String originLocation = internationalShipment.getOriginLocation();
//            if (outboundMap.containsKey(originLocation)) {
//                outboundMap.put(originLocation, outboundMap.get(originLocation) + 1);
//            } else {
//                outboundMap.put(originLocation, 1);
//            }
//        }
//        return outboundMap;
//    }
//
//    public Map<String, Integer> lowAndHighVolumeWithLocationForOutboundForInternationalRoad(Integer year) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.findByEmployeeId(userDetails.getUsername());
//
//        String role = user.getRoles().stream()
//                .map(Roles::getName)
//                .findFirst()
//                .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));
//
//        Specification<InternationalShipment> specification;
//        if (role.equals("ROLE_ADMIN")) {
//            specification = InternationalShipmentSpecification.withCreatedYearUserAndType(year, null,"By Road");
//        } else {
//            specification = InternationalShipmentSpecification.withCreatedYearUserAndType(year, user,"By Road");
//        }
//
//        List<InternationalShipment> shipments = internationalShipmentRepository.findAll(specification);
//
//        Map<String, Integer> outboundMap = new HashMap<>();
//
//        for (InternationalShipment internationalShipment : shipments) {
//            String originLocation = internationalShipment.getOriginLocation();
//            if (outboundMap.containsKey(originLocation)) {
//                outboundMap.put(originLocation, outboundMap.get(originLocation) + 1);
//            } else {
//                outboundMap.put(originLocation, 1);
//            }
//        }
//        return outboundMap;
//    }

    public Map<String, Map<String, Integer>> getOutBoundForInternationalAirDashboardTest(Integer year) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmployeeId(userDetails.getUsername());

        String role = user.getRoles().stream()
                .map(Roles::getName)
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));

        Specification<InternationalShipment> specification = role.equals("ROLE_ADMIN")
                ? InternationalShipmentSpecification.withCreatedYearUserAndType(year, null, "By Air")
                : InternationalShipmentSpecification.withCreatedYearUserAndType(year, user, "By Air");

        List<InternationalShipment> shipments = internationalShipmentRepository.findAll(specification);
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();

        for (InternationalShipment shipment : shipments) {
            String origin = shipment.getOriginLocation();
            String destination = shipment.getDestinationLocation();

            resultMap
                    .computeIfAbsent(origin, k -> new HashMap<>())
                    .merge(destination, 1, Integer::sum);
        }

        return resultMap;
    }


    public Map<String, Map<String, Integer>> getOutBoundForInternationalRoadDashboardTest(Integer year) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmployeeId(userDetails.getUsername());

        String role = user.getRoles().stream()
                .map(Roles::getName)
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));

        Specification<InternationalShipment> specification = role.equals("ROLE_ADMIN")
                ? InternationalShipmentSpecification.withCreatedYearUserAndType(year, null, "By Road")
                : InternationalShipmentSpecification.withCreatedYearUserAndType(year, user, "By Road");

        List<InternationalShipment> shipments = internationalShipmentRepository.findAll(specification);
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();

        for (InternationalShipment shipment : shipments) {
            String origin = shipment.getOriginLocation();
            String destination = shipment.getDestinationLocation();

            resultMap
                    .computeIfAbsent(origin, k -> new HashMap<>())
                    .merge(destination, 1, Integer::sum);
        }

        return resultMap;
    }

}
