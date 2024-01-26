package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForInternationalShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.dto.InternationalShipmentDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.exception.UserNotFoundException;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.*;

import com.example.CargoTracking.specification.InternationalShipmentSpecification;
import com.example.CargoTracking.specification.InternationalSummarySpecification;
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
    RoleRepository roleRepository;


    @Transactional
    public InternationalShipmentDto addShipment(InternationalShipmentDto internationalShipmentDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);

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
            unSaveInternationalShipment.setPreAlertType(unSaveInternationalShipment.getType().equalsIgnoreCase("By Road") ? "International-Road" : "International-Air");
            unSaveInternationalShipment.setCreatedTime(LocalDateTime.now());
            unSaveInternationalShipment.setActiveStatus(true);

            InternationalShipment internationalShipment = internationalShipmentRepository
                    .save(unSaveInternationalShipment);


            InternationalShipmentHistory shipmentHistory = InternationalShipmentHistory.builder()
                    .status(internationalShipment.getStatus())
                    .processTime(LocalDateTime.now())
                    .locationCode(internationalShipment.getOriginCountry())
                    .user(user.getId())
                    .type(internationalShipment.getType())
                    .internationalShipment(internationalShipment)
                    .remarks(internationalShipment.getRemarks())
                    .build();

            internationalShipmentHistoryRepository.save(shipmentHistory);

            String originEmails = locationService.getLocationByName(internationalShipment.getOriginCountry(), "International")
                    .getOriginEmail();
            String[] resultListOrigin = originEmails.split(",");
            List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOrigin));

            String destinationEmails = locationService.getLocationByName(internationalShipment.getDestinationCountry(), "International")
                    .getDestinationEmail();
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
                subject = "TSM Pre-Alert(A): "+internationalShipment.getRouteNumber()+"/"+internationalShipment.getFlightNumber().toString()+"/"+internationalShipment.getReferenceNumber()+"/"+internationalShipment.getEtd();
                model.put("field1",internationalShipment.getCreatedAt().toString());
                model.put("field2",internationalShipment.getReferenceNumber());
                model.put("field3",internationalShipment.getOriginCountry());
                model.put("field4",internationalShipment.getDestinationCountry());
                model.put("field5",internationalShipment.getOriginPort());
                model.put("field6",internationalShipment.getDestinationPort());
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
                subject = "TSM Pre_Alert(R): "+internationalShipment.getRouteNumber()+"/"+internationalShipment.getVehicleType()+"/"+internationalShipment.getReferenceNumber()+"/"+internationalShipment.getEtd();
                model.put("field1",internationalShipment.getCreatedAt().toString());
                model.put("field2",internationalShipment.getReferenceNumber());
                model.put("field3",internationalShipment.getOriginCountry());
                model.put("field4",internationalShipment.getDestinationCountry());
                model.put("field5",internationalShipment.getOriginPort());
                model.put("field6",internationalShipment.getDestinationPort());
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

            sendEmailsAsync(emails, subject, template, model);


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
            User user = userRepository.findByEmail(username);
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
            User user = userRepository.findByEmail(username);
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

//    @Scheduled(fixedRate = 20 * 60 * 1000)
////    @Scheduled(cron = "0 0 12 * * ?")
//    public void redFlag() {
//        LocalDate oneDayOlderDate = LocalDate.now().minusDays(1);
//
//        List<InternationalShipment> internationalShipmentList = internationalShipmentRepository.findByCreatedAt(oneDayOlderDate);
//        try {
//            LocalDateTime currentDate = LocalDateTime.now();
//
//            if(!internationalShipmentList.isEmpty()){
//                for (InternationalShipment entity : internationalShipmentList) {
//                    if (entity.getEta().isBefore(currentDate) && !entity.getRedFlag() && !entity.getStatus().equals("Arrived")) {
//                        entity.setRedFlag(true);
//                    }
//                }
//                internationalShipmentRepository.saveAll(internationalShipmentList);
//            }
//
//
//
//
//            List<InternationalShipment> internationalShipmentList1 = internationalShipmentRepository.findAll();
//            if(!internationalShipmentList1.isEmpty()){
//                for(InternationalShipment shipment: internationalShipmentList1){
//                    if(shipment.getArrivedTime() != null && shipment.getStatus()!="Cleared"){
//                        Duration duration = Duration.between(shipment.getArrivedTime(), LocalDateTime.now());
//                        if(duration.toMinutes() >= 480 && duration.toMinutes() <= 1440){
//                            if(!shipment.isEscalationFlagOne()){
//                                String originEmailsEscalation = locationService.getLocationByName(shipment.getOriginCountry(), "International")
//                                        .getOriginEscalation();
//                                String[] resultListOriginEscalation = originEmailsEscalation.split(",");
//
//                                String destinationEmailsEscalation = locationService.getLocationByName(shipment.getDestinationCountry(), "International")
//                                        .getDestinationEscalation();
//                                String[] resultListDestinationEscalation = destinationEmailsEscalation.split(",");
//
//                                List<String> emails = new ArrayList<>();
//                                emails.add(resultListOriginEscalation[0]);
//                                emails.add(resultListDestinationEscalation[0]);
//                                String subject;
//                                if(shipment.getType().equalsIgnoreCase("By Air")){
//                                    subject = "TSM 1st Escalation (A): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getReferenceNumber() +"/"+ shipment.getEtd();
//                                }else{
//                                    subject = "TSM 1st Escalation (R): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getReferenceNumber() +"/"+ shipment.getEtd();
//
//                                }
//                                Map<String,Object> model = new HashMap<>();
//                                model.put("field1",shipment.getReferenceNumber());
//                                model.put("field2",shipment.getDestinationPort());
//                                for (String to :emails) {
//                                    emailService.sendHtmlEmail(to,subject,"shipment-delay-email-template.ftl",model);
//                                }
//                                shipment.setEscalationFlagOne(true);
//                            }
//                        }
//                        if(duration.toMinutes() >= 1441 && duration.toMinutes() >= 2880){
//                            if(!shipment.isEscalationFlagTwo()){
//                                String originEmailsEscalation = locationService.getLocationByName(shipment.getOriginCountry(), "International")
//                                        .getOriginEscalation();
//                                String[] resultListOriginEscalation = originEmailsEscalation.split(",");
//
//                                String destinationEmailsEscalation = locationService.getLocationByName(shipment.getDestinationCountry(), "International")
//                                        .getDestinationEscalation();
//                                String[] resultListDestinationEscalation = destinationEmailsEscalation.split(",");
//
//                                List<String> emails = new ArrayList<>();
//                                emails.add(resultListOriginEscalation[1]);
//                                emails.add(resultListDestinationEscalation[1]);
//                                String subject;
//                                if(shipment.getType().equalsIgnoreCase("By Air")){
//                                    subject = "TSM 2nd Escalation (A): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getReferenceNumber() +"/"+ shipment.getEtd();
//                                }else{
//                                    subject = "TSM 2nd Escalation (R): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getReferenceNumber() +"/"+ shipment.getEtd();
//
//                                }
//                                Map<String,Object> model = new HashMap<>();
//                                model.put("field1",shipment.getReferenceNumber());
//                                model.put("field2",shipment.getDestinationPort());
//                                for (String to :emails) {
//                                    emailService.sendHtmlEmail(to,subject,"shipment-delay-email-template.ftl",model);
//                                }
//                                shipment.setEscalationFlagTwo(true);
//                            }
//                        }
//                        if(duration.toMinutes() >= 2881){
//                            if(!shipment.isEscalationFlagThree()){
//                                String originEmailsEscalation = locationService.getLocationByName(shipment.getOriginCountry(), "International")
//                                        .getOriginEscalation();
//                                String[] resultListOriginEscalation = originEmailsEscalation.split(",");
//
//                                String destinationEmailsEscalation = locationService.getLocationByName(shipment.getDestinationCountry(), "International")
//                                        .getDestinationEscalation();
//                                String[] resultListDestinationEscalation = destinationEmailsEscalation.split(",");
//
//                                List<String> emails = new ArrayList<>();
//                                emails.add(resultListOriginEscalation[2]);
//                                emails.add(resultListDestinationEscalation[2]);
//                                String subject;
//                                if(shipment.getType().equalsIgnoreCase("By Air")){
//                                    subject = "TSM 3rd Escalation (A): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getReferenceNumber() +"/"+ shipment.getEtd();
//
//                                }else{
//                                    subject = "TSM 3rd Escalation (R): "+ shipment.getRouteNumber() +"/"+ shipment.getVehicleType() +"/"+ shipment.getReferenceNumber() +"/"+ shipment.getEtd();
//
//                                }
//                                Map<String,Object> model = new HashMap<>();
//                                model.put("field1",shipment.getReferenceNumber());
//                                model.put("field2",shipment.getDestinationPort());
//                                for (String to :emails) {
//                                    emailService.sendHtmlEmail(to,subject,"shipment-delay-email-template.ftl",model);
//                                }
//                                shipment.setEscalationFlagThree(true);
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

//    public Page<InternationalShipmentDto> getInternationalOutBoundSummeryForAir(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
//                                                                                int page, int size) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(principal instanceof UserDetails){
//            Pageable pageable = PageRequest.of(page, size);
//            String username = ((UserDetails) principal).getUsername();
//            User user = userRepository.findByEmail(username);
//            if((user.getLocation() == null) && ((searchCriteriaForInternationalSummary.getDestination() == null || searchCriteriaForInternationalSummary.getDestination() == "") && (searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin() == "")
//                    && (searchCriteriaForInternationalSummary.getToDate() == null || searchCriteriaForInternationalSummary.getToDate() == "") && (searchCriteriaForInternationalSummary.getFromDate() == null || searchCriteriaForInternationalSummary.getFromDate() == "")
//                    && (searchCriteriaForInternationalSummary.getStatus() ==null || searchCriteriaForInternationalSummary.getStatus() == ""))){
//                throw new RecordNotFoundException(String.format("International shipment Not Found because user haven't an origin"));
//            }
//            if((searchCriteriaForInternationalSummary.getDestination() == null || searchCriteriaForInternationalSummary.getDestination() == "") && (searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin() == "")
//                    && (searchCriteriaForInternationalSummary.getToDate() == null || searchCriteriaForInternationalSummary.getToDate() == "") && (searchCriteriaForInternationalSummary.getFromDate() == null || searchCriteriaForInternationalSummary.getFromDate() == "")
//                    && (searchCriteriaForInternationalSummary.getStatus() ==null || searchCriteriaForInternationalSummary.getStatus() == "")){
//                Page<InternationalShipment> pageInternationalShipment =
//                        internationalShipmentRepository.findByOriginCountryByAir(user.getLocation().getLocationName(),
//                                pageable);
//                Page<InternationalShipmentDto> internationalShipmentDtoPage =pageInternationalShipment.map(entity->toDto(entity));
//                return internationalShipmentDtoPage;
//            }else{
//                if(user.getLocation() != null){
//                    if(searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin().isEmpty()) {
//                        searchCriteriaForInternationalSummary.setOrigin(user.getLocation().getLocationName());
//                    }
//                }
//                searchCriteriaForInternationalSummary.setType("By Air");
//                Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
//                Page<InternationalShipment> internationalShipmentPage =
//                        internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
//                Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );
//                return internationalShipmentPageDto;
//            }
//        }
//        throw new UserNotFoundException(String.format("User not found"));
//    }

    public Page<InternationalShipmentDto> getInternationalInBoundSummeryForAir(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
                                                                               int page, int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size);
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
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

//    public Page<InternationalShipmentDto> getInternationalOutBoundSummeryForRoad(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
//                                                                                 int page, int size) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(principal instanceof UserDetails){
//            Pageable pageable = PageRequest.of(page, size);
//            String username = ((UserDetails) principal).getUsername();
//            User user = userRepository.findByEmail(username);
//            if((user.getLocation() == null) && ((searchCriteriaForInternationalSummary.getDestination() == null || searchCriteriaForInternationalSummary.getDestination() == "") && (searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin() == "")
//                    && (searchCriteriaForInternationalSummary.getToDate() == null || searchCriteriaForInternationalSummary.getToDate()=="" ) && (searchCriteriaForInternationalSummary.getFromDate() == null|| searchCriteriaForInternationalSummary.getFromDate() =="")
//                    && (searchCriteriaForInternationalSummary.getStatus() ==null || searchCriteriaForInternationalSummary.getStatus() == ""))){
//                throw new RecordNotFoundException(String.format("International shipment Not Found because user haven't an origin"));
//            }
//            if((searchCriteriaForInternationalSummary.getDestination() == null || searchCriteriaForInternationalSummary.getDestination() == "") && (searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin() == "")
//                    && (searchCriteriaForInternationalSummary.getToDate() == null || searchCriteriaForInternationalSummary.getToDate() == "") && (searchCriteriaForInternationalSummary.getFromDate() == null || searchCriteriaForInternationalSummary.getFromDate() == "")
//                    && (searchCriteriaForInternationalSummary.getStatus() ==null || searchCriteriaForInternationalSummary.getStatus()=="")){
//                Page<InternationalShipment> pageInternationalShipment =
//                        internationalShipmentRepository.findByOriginCountryByRoad(user.getLocation().getLocationName(),
//                                pageable);
//                Page<InternationalShipmentDto> internationalShipmentDtoPage =pageInternationalShipment.map(entity->toDto(entity));
//                return internationalShipmentDtoPage;
//            }else{
//                if(user.getLocation() != null){
//                    searchCriteriaForInternationalSummary.setOrigin(user.getLocation().getLocationName());
//                }
//                searchCriteriaForInternationalSummary.setType("By Road");
//                Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
//                Page<InternationalShipment> internationalShipmentPage =
//                        internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
//                Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );
//                return internationalShipmentPageDto;
//            }
//        }
//        throw new UserNotFoundException(String.format("User not found"));
//    }

    public Page<InternationalShipmentDto> getInternationalInBoundSummeryForRoad(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
                                                                                int page, int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size);
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
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
    public InternationalShipmentDto updateInternationalShipment(Long id, InternationalShipmentDto internationalShipmentDto) {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        if(internationalShipment.isPresent()){
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof UserDetails){
                String username = ((UserDetails) principal).getUsername();
                User user = userRepository.findByEmail(username);
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

                internationalShipment.get().setOriginCountry(internationalShipmentDto.getOriginCountry());
                internationalShipment.get().setOriginPort(internationalShipmentDto.getOriginPort());
                internationalShipment.get().setRefrigeratedTruck(internationalShipmentDto.getRefrigeratedTruck());
                internationalShipment.get().setType(internationalShipmentDto.getType());
                internationalShipment.get().setShipmentMode(internationalShipmentDto.getShipmentMode());
                internationalShipment.get().setPreAlertNumber(internationalShipmentDto.getPreAlertNumber());
                internationalShipment.get().setDestinationCountry(internationalShipmentDto.getDestinationCountry());
                internationalShipment.get().setDestinationPort(internationalShipmentDto.getDestinationPort());
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
                internationalShipment.get().setRemarks(internationalShipmentDto.getRemarks());
                internationalShipment.get().setAta(internationalShipmentDto.getAta());
                internationalShipment.get().setTotalShipments(internationalShipmentDto.getTotalShipments());
                internationalShipment.get().setOverages(internationalShipmentDto.getOverages());
                internationalShipment.get().setOverageAWBs(internationalShipmentDto.getOverageAWBs());
                internationalShipment.get().setReceived(internationalShipmentDto.getReceived());
                internationalShipment.get().setShortages(internationalShipmentDto.getShortages());
                internationalShipment.get().setShortageAWBs(internationalShipmentDto.getShortageAWBs());
                internationalShipment.get().setRouteNumber(internationalShipmentDto.getRouteNumber());
//                if(!internationalShipment.get().getStatus().equals(internationalShipmentDto.getStatus())){
//                    List<String> emails = userRepository.findEmailByLocation(internationalShipment.get().getDestinationCountry());
//                    emails.add(internationalShipment.get().getCreatedBy().getEmail());
//                    for (String to :emails) {
////                        emailService.sendHtmlEmail(to,"Shipment status is changed");
//                    }
//
//                }
                if(internationalShipmentDto.getStatus().equalsIgnoreCase("Arrived")){
                    internationalShipment.get().setArrivedTime(LocalDateTime.now());
                    Duration duration = Duration.between(internationalShipment.get().getCreatedTime(), LocalDateTime.now());
                    internationalShipment.get().setTransitTimeTaken(duration.toMinutes());
                }
                if(internationalShipmentDto.getStatus().equalsIgnoreCase("Cleared")){
                    if(internationalShipment.get().getRedFlag()){
                        internationalShipment.get().setRedFlag(false);
                    }
                }
                InternationalShipment save;
                if(!internationalShipment.get().getStatus().equals(internationalShipmentDto.getStatus())){
                    internationalShipment.get().setStatus(internationalShipmentDto.getStatus());
                    save = internationalShipmentRepository.save(internationalShipment.get());

                    InternationalShipmentHistory shipmentHistory = InternationalShipmentHistory.builder()
                            .status(save.getStatus())
                            .processTime(LocalDateTime.now())
                            .locationCode(save.getOriginCountry())
                            .user(user.getId())
                            .type(save.getType())
                            .internationalShipment(save)
                            .remarks(save.getRemarks())
                            .build();

                    internationalShipmentHistoryRepository.save(shipmentHistory);

                }else{
                    save = internationalShipmentRepository.save(internationalShipment.get());
                }


                return toDto(save);
            }else{
                throw new UserNotFoundException(String.format("User not found"));
            }

        }else{
            throw new RecordNotFoundException(String.format("International shipment Not Found By This Id %d",id));
        }
    }

    public ApiResponse addAttachment(Long id,String attachementType, MultipartFile file) throws IOException {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        FileMetaData byFileName = fileMetaDataRepository.findByFileName(file.getOriginalFilename());
        if(byFileName == null){
            String fileUrl = storageService.uploadFile(file.getBytes(), file.getOriginalFilename());
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            FileMetaData fileMetaData = new FileMetaData();
            fileMetaData.setFileUrl(fileUrl);
            fileMetaData.setFileExtension(fileExtension);
            fileMetaData.setFileName(file.getOriginalFilename());
            fileMetaData.setAttachmentType(attachementType);
            fileMetaData.setInternationalShipment(internationalShipment.get());
            fileMetaDataRepository.save(fileMetaData);
            return ApiResponse.builder()
                    .message("File uploaded to the server successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        }else{
            throw new RecordNotFoundException(String.format("File already exists on the bucket with this name"));
        }

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
}
