package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.dto.SendEmailAddressForOutlookManual;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.exception.UserNotFoundException;
import com.example.CargoTracking.repository.*;
import com.example.CargoTracking.specification.DomesticShipmentSpecification;
import com.example.CargoTracking.specification.DomesticSummarySpecification;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;


import javax.transaction.Transactional;
import java.time.Duration;
import java.util.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DomesticShipmentService {

  @Autowired
  DomesticShipmentRepository domesticShipmentRepository;
  @Autowired
  ModelMapper modelMapper;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  DomesticShipmentHistoryRepository domesticShipmentHistoryRepository;
  @Autowired
  EmailService emailService;
  @Autowired
  StorageService storageService;
  @Autowired
  FileMetaDataRepository fileMetaDataRepository;
  @Autowired
  LocationService locationService;
  @Autowired
  DomesticRouteRepository domesticRouteRepository;
  @Autowired
  LocationRepository locationRepository;

  private static final Logger logger = LoggerFactory.getLogger(StorageService.class);


  @Transactional
  public DomesticShipmentDto addShipment(DomesticShipmentDto domesticShipmentDto, Long orgLocationId, Long desLocationId) throws IOException {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      String username = ((UserDetails) principal).getUsername();
      User user = userRepository.findByEmployeeId(username);

      LocalDate todaysDate = LocalDate.now();

      List<DomesticShipment> olderDomesticShipmentList = domesticShipmentRepository.findByCreatedAt(todaysDate);

      if (!olderDomesticShipmentList.isEmpty()) {
        for (DomesticShipment shipment : olderDomesticShipmentList) {
          if (shipment.getOriginLocation().equals(domesticShipmentDto.getOriginLocation()) &&
                  shipment.getDestinationLocation().equals(domesticShipmentDto.getDestinationLocation()) &&
                  shipment.getVehicleNumber().equals(domesticShipmentDto.getVehicleNumber())) {
            throw new RecordNotFoundException(String.format("This location and vehicle number has already used in another shipment"));
          }
        }
      }


      DomesticShipment unSaveDomesticShipment = toEntity(domesticShipmentDto);
      unSaveDomesticShipment.setCreatedAt(LocalDate.now());
      if (domesticShipmentDto.getTrip() != 0 && (domesticShipmentDto.getRouteNumber().contains("Adhoc") || domesticShipmentDto.getRouteNumber().contains("adhoc"))) {
        unSaveDomesticShipment.setPreAlertNumber(domesticShipmentDto.getRouteNumber() + " " + domesticShipmentDto.getTrip() + " " + LocalDate.now());
      } else {
        unSaveDomesticShipment.setPreAlertNumber(domesticShipmentDto.getRouteNumber() + " " + LocalDate.now());
      }
      unSaveDomesticShipment.setCreatedBy(user);
      unSaveDomesticShipment.setRedFlag(Boolean.FALSE);
      unSaveDomesticShipment.setActiveStatus(Boolean.TRUE);
      unSaveDomesticShipment.setPreAlertType("Domestic");
      LocalDateTime currentLocalDateTime = LocalDateTime.now();
      unSaveDomesticShipment.setCreatedTime(currentLocalDateTime);
      unSaveDomesticShipment.setUpdatedTime(currentLocalDateTime);
      unSaveDomesticShipment.setOriginLocationId(orgLocationId);
      unSaveDomesticShipment.setDestinationLocationId(desLocationId);
      List<DomesticShipment> all = domesticShipmentRepository.findByCreatedAt(LocalDate.now());
      for (DomesticShipment domesticShipment : all) {
        if (domesticShipment.getPreAlertNumber().equals(unSaveDomesticShipment.getPreAlertNumber())) {
          throw new RecordNotFoundException(String.format("Shipment with the given pre alert number is already exist"));
        }
      }
      DomesticShipment domesticShipment = domesticShipmentRepository.save(unSaveDomesticShipment);
      List<String> locationNameArray = user.getLocations().stream().map(Location::getLocationName).collect(Collectors.toList());
      String locationNamesString = String.join(",", locationNameArray);
      DomesticShipmentHistory domesticShipmentHistory = DomesticShipmentHistory.builder()
              .status(domesticShipmentDto.getStatus())
              .activeStatus(Boolean.TRUE)
              .processTime(currentLocalDateTime)
              .locationCode(locationNamesString)
              .user(user.getEmployeeId())
              .domesticShipment(domesticShipment)
              .remarks(domesticShipment.getRemarks())
              .build();

      domesticShipmentHistoryRepository.save(domesticShipmentHistory);

      String originEmails = locationRepository.findById(orgLocationId).get()
              .getOriginEmail();
      String[] resultListOrigin = originEmails.split(",");
      List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOrigin));

      String destinationEmails = locationRepository.findById(desLocationId).get()
              .getDestinationEmail();
      String[] resultListDestination = destinationEmails.split(",");
      List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestination));


      List<String> emails = new ArrayList<>();
      emails.addAll(originEmailAddresses);
      emails.addAll(destinationEmailAddresses);

      String subject = "TSM Pre-Alert(D): " + domesticShipment.getPreAlertNumber() + "/" + domesticShipment.getVehicleType() + "/" + domesticShipment.getReferenceNumber();

      Map<String, Object> model = new HashMap<>();
      model.put("field1", domesticShipment.getCreatedAt().toString());
      model.put("field2", domesticShipment.getReferenceNumber());
      model.put("field3", domesticShipment.getOriginLocation());
      model.put("field4", domesticShipment.getDestinationLocation());
      model.put("field5", domesticShipment.getNumberOfShipments() != null ? domesticShipment.getNumberOfShipments().toString() : "0");
      model.put("field6", domesticShipment.getVehicleType());
      model.put("field7", domesticShipment.getNumberOfBags().toString());
      model.put("field8", domesticShipment.getNumberOfPallets().toString());
//      model.put("field9", domesticShipment.getWeight().toString());
      model.put("field10", "Road");
      model.put("field11", domesticShipment.getRouteNumber().toString());
      model.put("field15", domesticShipment.getRemarks());
      emailService.sendHtmlEmail(resultListOrigin, resultListDestination, subject, "domestic-email-template.ftl", model);
      return toDto(domesticShipment);
    }

    throw new UserNotFoundException(String.format("User not found while creating domestic shipment"));
  }

  @Async
  private void sendEmailsAsync(List<String> emails, String subject, String template, Map<String, Object> model) {
    CompletableFuture.runAsync(() -> {
      for (String to : emails) {
        emailService.sendHtmlEmail(to, subject, template, model);
      }
    });
  }

  public Page<DomesticShipmentDto> getAll(SearchCriteriaForDomesticShipment searchCriteriaForDomesticShipment, int page, int size) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

      String username = ((UserDetails) principal).getUsername();
      User user = userRepository.findByEmployeeId(username);
      String role = "";
      for (Roles roleList : user.getRoles()) {
        Optional<Roles> roles = Optional.ofNullable(roleRepository
                .findByName(roleList.getName())
                .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

        role = roles.get().getName();
      }
      if (role.equals("ROLE_ADMIN")) {
        searchCriteriaForDomesticShipment.setUser(null);

        Specification<DomesticShipment> domesticShipmentSpecification = DomesticShipmentSpecification.getSearchSpecification(searchCriteriaForDomesticShipment);
        Page<DomesticShipment> domesticShipmentPage = domesticShipmentRepository.findAll(domesticShipmentSpecification, pageable);
        Page<DomesticShipmentDto> domesticShipmentDtoPage = domesticShipmentPage.map(entity -> toDto(entity));

        return domesticShipmentDtoPage;
      } else {
        searchCriteriaForDomesticShipment.setUser(user);

        Specification<DomesticShipment> domesticShipmentSpecification = DomesticShipmentSpecification.getSearchSpecification(searchCriteriaForDomesticShipment);
        Page<DomesticShipment> domesticShipmentPage = domesticShipmentRepository.findAll(domesticShipmentSpecification, pageable);
        Page<DomesticShipmentDto> domesticShipmentDtoPage = domesticShipmentPage.map(entity -> toDto(entity));

        return domesticShipmentDtoPage;
      }
    }
    throw new UserNotFoundException(String.format("User not found"));

  }


  public DomesticShipmentDto getById(Long id) {
    Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);
    if (domesticShipment.isPresent()) {
      return toDto(domesticShipment.get());
    }
    throw new RecordNotFoundException(String.format("Domestic shipment Not Found By This Id %d", id));
  }

  public SendEmailAddressForOutlookManual getDomesticShipmentEmailAddressesById(Long id) {
    Optional<DomesticShipment> domesticShipmentOptional = domesticShipmentRepository.findById(id);
    if (domesticShipmentOptional.isPresent()) {
      DomesticShipment domesticShipment = domesticShipmentOptional.get();
      Optional<Location> originLocation = locationRepository.findById(domesticShipment.getOriginLocationId());
      Optional<Location> destinationLocation = locationRepository.findById(domesticShipment.getDestinationLocationId());
      String subject = "TSM Pre-Alert(D): " + domesticShipment.getPreAlertNumber() + "/" + domesticShipment.getVehicleType() + "/" + domesticShipment.getReferenceNumber();

      SendEmailAddressForOutlookManual sendEmailAddressForOutlookManual = new SendEmailAddressForOutlookManual();
      sendEmailAddressForOutlookManual.setTo(originLocation.get().getOriginEmail());
      sendEmailAddressForOutlookManual.setCc(destinationLocation.get().getDestinationEmail());
      sendEmailAddressForOutlookManual.setSubject(subject);
      return sendEmailAddressForOutlookManual;
    }
    throw new RecordNotFoundException(String.format("Domestic shipment Not Found By This Id %d", id));
  }

//    public Page<DomesticShipmentDto> getOutboundShipment(SearchCriteriaForSummary searchCriteriaForSummary, int page, int size) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            Pageable pageable = PageRequest.of(page, size);
//            Page<DomesticShipment> domesticShipmentPage;
//            String username = ((UserDetails) principal).getUsername();
//            User user = userRepository.findByEmail(username);
//            if ((user.getLocation() == null ) && ((searchCriteriaForSummary.getDestination() == null || searchCriteriaForSummary.getDestination()=="") && (searchCriteriaForSummary.getOrigin() == null || searchCriteriaForSummary.getOrigin()=="")
//                    && (searchCriteriaForSummary.getToDate() == null || searchCriteriaForSummary.getToDate()=="") && (searchCriteriaForSummary.getFromDate() == null || searchCriteriaForSummary.getFromDate()=="")
//                    && (searchCriteriaForSummary.getStatus() == null || searchCriteriaForSummary.getStatus()==""))) {
//
//                throw new RecordNotFoundException(String.format("Domestic shipment Not Found because user haven't an origin"));
//            }
//            if ( (searchCriteriaForSummary.getDestination() == null || searchCriteriaForSummary.getDestination()=="")  && (searchCriteriaForSummary.getOrigin() == null || searchCriteriaForSummary.getOrigin()=="")
//                    && (searchCriteriaForSummary.getToDate() == null || searchCriteriaForSummary.getToDate()=="") && (searchCriteriaForSummary.getFromDate() == null || searchCriteriaForSummary.getFromDate()=="")
//                    && (searchCriteriaForSummary.getStatus() == null || searchCriteriaForSummary.getStatus()=="")) {
//                Page<DomesticShipment> pageDomesticShipment =
//                        domesticShipmentRepository.findByOriginLocation(user.getLocation().getLocationName(), pageable);
//                Page<DomesticShipmentDto> pageDomesticShipmentDto = pageDomesticShipment.map(entity -> toDto(entity));
//                return pageDomesticShipmentDto;
//            } else {
//                if (user.getLocation() != null) {
//                    if (searchCriteriaForSummary.getOrigin() == null || searchCriteriaForSummary.getOrigin().isEmpty()) {
//                        searchCriteriaForSummary.setOrigin(user.getLocation().getLocationName());
//                    }
//                }
//
//                Specification<DomesticShipment> domesticSummarySpecification = DomesticSummarySpecification.getSearchSpecification(searchCriteriaForSummary);
//                Page<DomesticShipment> pageDomesticShipmentDto = domesticShipmentRepository.
//                        findAll(domesticSummarySpecification, pageable);
//                Page<DomesticShipmentDto> pageDomesticShipmentDtoWithSpec = pageDomesticShipmentDto.map(entity -> toDto(entity));
//
//                return pageDomesticShipmentDtoWithSpec;
//            }
//
//        }
//
//        throw new UserNotFoundException("User not found");
//    }

  public Page<DomesticShipmentDto> getInboundShipment(SearchCriteriaForSummary searchCriteriaForSummary, int page, int size) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedTime"));
      Page<DomesticShipment> domesticShipmentPage;
      String username = ((UserDetails) principal).getUsername();
      User user = userRepository.findByEmployeeId(username);

      if (searchCriteriaForSummary.getDestinations().isEmpty()) {
        Set<Location> userLocations = user.getLocations();
        if (!userLocations.isEmpty()) {
          Set<String> domesticLocationNamePresentInUser = userLocations.stream()
                  .filter(location -> "Domestic".equals(location.getType()))
                  .map(Location::getLocationName)
                  .collect(Collectors.toSet());
          searchCriteriaForSummary.setDestinations(domesticLocationNamePresentInUser);
        } else {
          searchCriteriaForSummary.setDestinations(Collections.emptySet());
        }
      }

      Specification<DomesticShipment> domesticSummarySpecification = DomesticSummarySpecification.getSearchSpecification(searchCriteriaForSummary);
      Page<DomesticShipment> pageDomesticShipmentDto = domesticShipmentRepository.
              findAll(domesticSummarySpecification, pageable);
      Page<DomesticShipmentDto> pageDomesticShipmentDtoWithSpec = pageDomesticShipmentDto.map(entity -> toDto(entity));

      return pageDomesticShipmentDtoWithSpec;
    }

    throw new UserNotFoundException("User not found");
  }

  @Transactional
  public DomesticShipmentDto updateDomesticShipment(Long id, DomesticShipmentDto domesticShipmentDto, Long orgLocationId, Long desLocationId) {
    Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);
    if (domesticShipment.isPresent()) {
      domesticShipment.get().setUpdatedAt(LocalDate.now());
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof UserDetails) {
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.findByEmployeeId(username);
        domesticShipment.get().setUpdatedBy(user);
        if (domesticShipmentDto.getTrip() != 0 && (domesticShipmentDto.getRouteNumber().contains("Adhoc") || domesticShipmentDto.getRouteNumber().contains("adhoc"))) {
          domesticShipment.get().setPreAlertNumber(domesticShipmentDto.getRouteNumber() + " " + domesticShipmentDto.getTrip() + " " + LocalDate.now());
        } else {
          domesticShipment.get().setPreAlertNumber(domesticShipmentDto.getRouteNumber() + " " + LocalDate.now());
        }
        domesticShipment.get().setOriginLocationId(orgLocationId);
        domesticShipment.get().setDestinationLocationId(desLocationId);
        domesticShipment.get().setOriginFacility(domesticShipmentDto.getOriginFacility());
        domesticShipment.get().setOriginLocation(domesticShipmentDto.getOriginLocation());
        domesticShipment.get().setRefrigeratedTruck(domesticShipmentDto.getRefrigeratedTruck());
        domesticShipment.get().setDestinationFacility(domesticShipmentDto.getDestinationFacility());
        domesticShipment.get().setDestinationLocation(domesticShipmentDto.getDestinationLocation());
        domesticShipment.get().setRouteNumber(domesticShipmentDto.getRouteNumber());
        domesticShipment.get().setNumberOfShipments(domesticShipmentDto.getNumberOfShipments());
//        domesticShipment.get().setWeight(domesticShipmentDto.getWeight());
        domesticShipment.get().setEtd(domesticShipmentDto.getEtd());
        domesticShipment.get().setEta(domesticShipmentDto.getEta());
        domesticShipment.get().setAtd(domesticShipmentDto.getAtd());
        domesticShipment.get().setDriverName(domesticShipmentDto.getDriverName());
        domesticShipment.get().setDriverContact(domesticShipmentDto.getDriverContact());
        domesticShipment.get().setReferenceNumber(domesticShipmentDto.getReferenceNumber());
        domesticShipment.get().setVehicleType(domesticShipmentDto.getVehicleType());
        domesticShipment.get().setNumberOfPallets(domesticShipmentDto.getNumberOfPallets());
        domesticShipment.get().setNumberOfBags(domesticShipmentDto.getNumberOfBags());
        domesticShipment.get().setNumberOfBoxes(domesticShipmentDto.getNumberOfBoxes());
        domesticShipment.get().setVehicleNumber(domesticShipmentDto.getVehicleNumber());
        domesticShipment.get().setTagNumber(domesticShipmentDto.getTagNumber());
        domesticShipment.get().setSealNumber(domesticShipmentDto.getSealNumber());
        domesticShipment.get().setRemarks(domesticShipmentDto.getRemarks());
        domesticShipment.get().setAta(domesticShipmentDto.getAta());
        domesticShipment.get().setTotalShipments(domesticShipmentDto.getTotalShipments());
        domesticShipment.get().setOverages(domesticShipmentDto.getOverages());
        domesticShipment.get().setOveragesAwbs(domesticShipmentDto.getOveragesAwbs());
        domesticShipment.get().setReceived(domesticShipmentDto.getReceived());
        domesticShipment.get().setShortages(domesticShipmentDto.getShortages());
        domesticShipment.get().setShortagesAwbs(domesticShipmentDto.getShortagesAwbs());
        domesticShipment.get().setAttachments(domesticShipmentDto.getAttachments());
        domesticShipment.get().setDamage(domesticShipmentDto.getDamage());
        domesticShipment.get().setDamageAwbs(domesticShipmentDto.getDamageAwbs());
        domesticShipment.get().setUpdatedTime(LocalDateTime.now());
        domesticShipment.get().setNumberOfBagsReceived(domesticShipmentDto.getNumberOfBagsReceived());
        domesticShipment.get().setNumberOfPalletsReceived(domesticShipmentDto.getNumberOfPalletsReceived());

        List<DomesticShipment> all = domesticShipmentRepository.findByCreatedAt(LocalDate.now());
        for (DomesticShipment domesticShipmentForPreAlertNumber : all) {
          if (domesticShipmentForPreAlertNumber.getPreAlertNumber().equals(domesticShipment.get().getPreAlertNumber())) {
            if (domesticShipment.get().getId() != domesticShipmentDto.getId()) {
              throw new RecordNotFoundException(String.format("Shipment with the given pre alert number is already exist"));

            }
          }
        }
        if (domesticShipmentDto.getStatus().equalsIgnoreCase("Arrived")) {
          domesticShipment.get().setArrivedTime(LocalDateTime.now());
          domesticShipment.get().setRedFlag(false);
        }
        if (domesticShipmentDto.getAta() != null) {
          Duration duration = Duration.between(domesticShipment.get().getAtd() ,domesticShipmentDto.getAta());
          domesticShipment.get().setTransitTimeTaken(duration.toMinutes());
        }

        DomesticShipmentHistory domesticShipmentHistory;
        DomesticShipment save;
        if (domesticShipment.get().getStatus() != domesticShipmentDto.getStatus()) {
          domesticShipment.get().setStatus(domesticShipmentDto.getStatus());
          save = domesticShipmentRepository.save(domesticShipment.get());
          List<String> locationNameArray = user.getLocations().stream().map(Location::getLocationName).collect(Collectors.toList());
          String locationNamesString = String.join(",", locationNameArray);
          domesticShipmentHistory = DomesticShipmentHistory.builder()
                  .status(save.getStatus())
                  .processTime(LocalDateTime.now())
                  .locationCode(locationNamesString)
                  .user(user.getEmployeeId())
                  .domesticShipment(save)
                  .remarks(save.getRemarks())
                  .build();

          domesticShipmentHistoryRepository.save(domesticShipmentHistory);
        } else {
          save = domesticShipmentRepository.save(domesticShipment.get());
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


          String subject = "TSM Pre-Alert(D): " + save.getPreAlertNumber() + "/" + save.getVehicleType() + "/" + save.getReferenceNumber() + "/Report";

          Map<String, Object> model = new HashMap<>();
          model.put("field1", save.getArrivedTime() != null ? save.getArrivedTime().toLocalDate().toString() : "NIL");
          model.put("field2", save.getNumberOfBags() != null && save.getNumberOfBags() != 0 ? save.getNumberOfBags().toString() : "NIL");
          model.put("field3", save.getNumberOfBagsReceived() != null && save.getNumberOfBagsReceived() != 0 ? save.getNumberOfBagsReceived().toString() : "NIL");
          model.put("field4", save.getTotalShipments() != null && save.getTotalShipments() != 0 ? save.getTotalShipments().toString() : "NIL");
          model.put("field5", save.getReceived() != null && save.getReceived() != 0 ? save.getReceived().toString() : "NIL");
          model.put("field6", save.getNumberOfPallets() != null && save.getNumberOfPallets() != 0 ? save.getNumberOfPallets().toString() : "NIL");
          model.put("field7", save.getNumberOfPalletsReceived() != null && save.getNumberOfPalletsReceived() != 0 ? save.getNumberOfPalletsReceived().toString() : "NIL");
          model.put("field8", save.getShortages() != null && save.getShortages() != 0 ? save.getShortages().toString() : "NIL");
          model.put("field9", (save.getShortagesAwbs() != null && !save.getShortagesAwbs().isEmpty()) ? save.getShortagesAwbs() : "NIL");
          model.put("field10", save.getOverages() != null && save.getOverages() != 0 ? save.getOverages().toString() : "NIL");
          model.put("field11", (save.getOveragesAwbs() != null && !save.getOveragesAwbs().isEmpty()) ? save.getOveragesAwbs() : "NIL");
          model.put("field12", save.getDamage() != null && save.getDamage() != 0 ? save.getDamage().toString() : "NIL");
          model.put("field13", (save.getDamageAwbs() != null && !save.getDamageAwbs().isEmpty()) ? save.getDamageAwbs() : "NIL");

          sendEmailsAsync(emails, subject, "overages-and-shortages-template.ftl", model);
        }

        return toDto(save);
      } else {
        throw new UserNotFoundException(String.format("User not found while updating domestic shipment"));
      }

    } else {
      throw new RecordNotFoundException(String.format("Domestic shipment Not Found By This Id %d", id));
    }
  }


  public ApiResponse deleteDomesticShipment(Long id) {
    Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);
    if (domesticShipment.isPresent()) {
      DomesticShipment shipment = domesticShipment.get();
      shipment.setActiveStatus(Boolean.FALSE);
      domesticShipmentRepository.save(shipment);

//            List<DomesticShipmentHistory> domesticShipmentHistoryList = domesticShipmentHistoryRepository.findByDomesticShipmentId(id);
//            for (DomesticShipmentHistory shipmentHistory: domesticShipmentHistoryList) {
//                shipmentHistory.setActiveStatus(Boolean.FALSE);
//                domesticShipmentHistoryRepository.save(shipmentHistory);
//            }

      return ApiResponse.builder()
              .message("Record delete successfully")
              .statusCode(HttpStatus.OK.value())
              .result(Collections.emptyList())
              .build();
    }
    throw new RecordNotFoundException(String.format("Domestic Shipment not found by this id => %d", id));
  }

  public ApiResponse addAttachments(Long id, String attachmentType, List<MultipartFile> files) throws IOException {
    Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);

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
        fileMetaData.setDomesticShipment(domesticShipment.get());
        fileMetaData.setAttachmentType(attachmentType);
        fileMetaDataRepository.save(fileMetaData);
      } else {
        throw new RecordNotFoundException(String.format("File '%s' already exists on the bucket", uniqueFileName));
      }
    }

    return ApiResponse.builder()
            .message("Files uploaded to the server successfully")
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

  public ApiResponse deleteAttachment(Long id) {
    Optional<FileMetaData> fileMetaData = fileMetaDataRepository.findById(id);
    if (fileMetaData.isPresent()) {
      FileMetaData existingFileMetaData = fileMetaData.get();
      String fileName = existingFileMetaData.getFileName();

      fileMetaDataRepository.deleteById(id);

      storageService.deleteFile(fileName);

      return ApiResponse.builder()
              .message("Attachment delete successfully")
              .statusCode(HttpStatus.OK.value())
              .result(Collections.emptyList())
              .build();
    } else {
      throw new RuntimeException(String.format("Attachment with ID %d not found", id));
    }
  }

  @Scheduled(fixedRate = 20 * 60 * 1000)
  public void redFlag() {
//    route number me duration define hoga ek field add kerni hai and
//    faliure tab hoga jab atd se duration se between ziyada gap a jaee ga to red show kra deen ge
//    2gap se escalation send hogi
//    LocalDate oneDayOlderDate = LocalDate.now().minusDays(1);
    List<DomesticShipment> domesticShipmentList = domesticShipmentRepository.findAll();

    try {
      LocalDateTime currentDateTime = LocalDateTime.now();

      if (!domesticShipmentList.isEmpty()) {
        for (DomesticShipment entity : domesticShipmentList) {
          Optional<DomesticRoute> domesticRoute = domesticRouteRepository.findById(entity.getRouteNumberId());
          if (!entity.getRedFlag() && entity.getArrivedTime() == null) {
            Duration duration = Duration.between(entity.getAtd(), currentDateTime);
            if (duration.toHours() > domesticRoute.get().getDurationLimit()) {
              entity.setRedFlag(true);
            }
          }
        }
        domesticShipmentRepository.saveAll(domesticShipmentList);
      }

      List<DomesticShipment> domesticShipmentList1 = domesticShipmentRepository.findAll();
      if (!domesticShipmentList1.isEmpty()) {
        for (DomesticShipment shipment : domesticShipmentList1) {
          if(shipment.getArrivedTime()==null){
            Duration duration = Duration.between(shipment.getAtd(), currentDateTime);

//            String originEmails = locationRepository.findById(shipment.getOriginLocationId()).get()
//                    .getOriginEscalation();
//            String[] resultListOriginEscalation = originEmails.split(",");
//
//            String destinationEmails = locationRepository.findById(shipment.getDestinationLocationId()).get()
//                    .getDestinationEscalation();
//            String[] resultListDestinationEscalation = destinationEmails.split(",");
            Optional<DomesticRoute> domesticRoute = domesticRouteRepository.findById(shipment.getRouteNumberId());

            if (duration.toHours() >= domesticRoute.get().getDurationLimit() + 2 && duration.toHours() < domesticRoute.get().getDurationLimit() + 4) {
              if (!shipment.isEscalationFlagOne()) {
                List<String> emails = new ArrayList<>();
                String originEmails = locationRepository.findById(shipment.getOriginLocationId()).get()
                        .getOriginEscalationLevel1();
                String[] resultListOriginEscalation = originEmails.split(",");
                List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOriginEscalation));

                String destinationEmails = locationRepository.findById(shipment.getDestinationLocationId()).get()
                        .getDestinationEscalationLevel1();
                String[] resultListDestinationEscalation = destinationEmails.split(",");
                List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestinationEscalation));

                if(originEmailAddresses.size()>1){
                  emails.addAll(originEmailAddresses);
                }
                if(destinationEmailAddresses.size()>1){
                  emails.addAll(destinationEmailAddresses);
                }

                String subject = "TSM 1st Escalation (D): " + shipment.getPreAlertNumber() + "/" + shipment.getVehicleType() + "/" + shipment.getReferenceNumber() + "/" + shipment.getEtd();
                Map<String, Object> model = new HashMap<>();
                model.put("field1", shipment.getReferenceNumber());
                model.put("field2", shipment.getDestinationLocation());

                sendEmailsAsync(emails, subject, "shipment-delay-email-template.ftl", model);

                shipment.setEscalationFlagOne(true);
                domesticShipmentRepository.save(shipment);
              }
            }
            if (duration.toHours() >= domesticRoute.get().getDurationLimit() + 4 && duration.toHours() < domesticRoute.get().getDurationLimit() + 6) {
              if (!shipment.isEscalationFlagTwo()) {
                List<String> emails = new ArrayList<>();
                String originEmails = locationRepository.findById(shipment.getOriginLocationId()).get()
                        .getOriginEscalationLevel2();
                String[] resultListOriginEscalation = originEmails.split(",");
                List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOriginEscalation));

                String destinationEmails = locationRepository.findById(shipment.getDestinationLocationId()).get()
                        .getDestinationEscalationLevel2();
                String[] resultListDestinationEscalation = destinationEmails.split(",");
                List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestinationEscalation));

                if(originEmailAddresses.size()>1){
                  emails.addAll(originEmailAddresses);
                }
                if(destinationEmailAddresses.size()>1){
                  emails.addAll(destinationEmailAddresses);
                }

                String subject = "TSM 2nd Escalation (D): " + shipment.getPreAlertNumber() + "/" + shipment.getVehicleType() + "/" + shipment.getReferenceNumber() + "/" + shipment.getEtd();
                Map<String, Object> model = new HashMap<>();
                model.put("field1", shipment.getReferenceNumber());
                model.put("field2", shipment.getDestinationLocation());

                sendEmailsAsync(emails, subject, "shipment-delay-email-template.ftl", model);

                shipment.setEscalationFlagTwo(true);
                domesticShipmentRepository.save(shipment);
              }
            }
            if (duration.toHours() >= domesticRoute.get().getDurationLimit() + 6) {
              if (!shipment.isEscalationFlagThree()) {

                List<String> emails = new ArrayList<>();
                String originEmails = locationRepository.findById(shipment.getOriginLocationId()).get()
                        .getOriginEscalationLevel3();
                String[] resultListOriginEscalation = originEmails.split(",");
                List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOriginEscalation));

                String destinationEmails = locationRepository.findById(shipment.getDestinationLocationId()).get()
                        .getDestinationEscalationLevel3();
                String[] resultListDestinationEscalation = destinationEmails.split(",");
                List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestinationEscalation));
                if(originEmailAddresses.size()>1){
                  emails.addAll(originEmailAddresses);
                }
                if(destinationEmailAddresses.size()>1){
                  emails.addAll(destinationEmailAddresses);
                }

                String subject = "TSM 3rd Escalation (D): " + shipment.getPreAlertNumber() + "/" + shipment.getVehicleType() + "/" + shipment.getReferenceNumber() + "/" + shipment.getEtd();
                Map<String, Object> model = new HashMap<>();
                model.put("field1", shipment.getReferenceNumber());
                model.put("field2", shipment.getDestinationLocation());
                sendEmailsAsync(emails, subject, "shipment-delay-email-template.ftl", model);

                shipment.setEscalationFlagThree(true);
                domesticShipmentRepository.save(shipment);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      Collections.emptyList();
    }
  }

  public List<DomesticShipmentDto> toDtoList(List<DomesticShipment> domesticShipmentList) {
    return domesticShipmentList.stream().map(this::toDto).collect(Collectors.toList());
  }

  private DomesticShipmentDto toDto(DomesticShipment domesticShipment) {
    return modelMapper.map(domesticShipment, DomesticShipmentDto.class);
  }


  private DomesticShipment toEntity(DomesticShipmentDto domesticShipmentDto) {
    return modelMapper.map(domesticShipmentDto, DomesticShipment.class);
  }

  public Map<String, Integer> getAllDashboardData(Integer year) {
    Map<String, Integer> dashboardData = new HashMap<>();

    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepository.findByEmployeeId(userDetails.getUsername());

    String role = user.getRoles().stream()
            .map(Roles::getName)
            .findFirst()
            .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));

    Specification<DomesticShipment> specification;
    if (role.equals("ROLE_ADMIN")) {
      specification = DomesticShipmentSpecification.withCreatedYearAndUser(year, null);
    } else {
      specification = DomesticShipmentSpecification.withCreatedYearAndUser(year, user);
    }

    List<DomesticShipment> shipments = domesticShipmentRepository.findAll(specification);

    int outboundCount = shipments.size();
    int totalShipments = shipments.stream()
            .mapToInt(DomesticShipment::getNumberOfShipments)
            .sum();

    dashboardData.put("Outbounds", outboundCount);
    dashboardData.put("TotalShipments", totalShipments);

    Set<String> userLocations = user.getLocations().stream()
            .filter(location -> "Domestic".equals(location.getType()))
            .map(Location::getLocationName)
            .collect(Collectors.toSet());

    if (!userLocations.isEmpty()) {
      Specification<DomesticShipment> inboundSpecification =
              DomesticShipmentSpecification.withDestinationLocationsAndActive(year, userLocations);
      int inboundCount = (int) domesticShipmentRepository.count(inboundSpecification);
      dashboardData.put("Inbounds", inboundCount);
    } else {
      dashboardData.put("Inbounds", 0);
    }

    return dashboardData;
  }

  //inbound
  public Map<String, Integer> lowAndHighVolumeWithLocationForInboundForDomestic(Integer year) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepository.findByEmployeeId(userDetails.getUsername());
    Set<String> userLocations = user.getLocations().stream()
            .filter(location -> "Domestic".equals(location.getType()))
            .map(Location::getLocationName)
            .collect(Collectors.toSet());
    Map<String, Integer> inboundMap = new HashMap<>();
    if (!userLocations.isEmpty()) {
      Specification<DomesticShipment> inboundSpecification =
              DomesticShipmentSpecification.withDestinationLocationsAndActive(year, userLocations);
      List<DomesticShipment> all = domesticShipmentRepository.findAll(inboundSpecification);
      for (DomesticShipment domesticShipment : all) {
        String destinationLocation = domesticShipment.getDestinationLocation();
        if (inboundMap.containsKey(destinationLocation)) {
          inboundMap.put(destinationLocation, inboundMap.get(destinationLocation) + 1);
        } else {
          inboundMap.put(destinationLocation, 1);
        }
      }
    }
    return inboundMap;
  }

  public Map<String, Integer> lowAndHighVolumeWithLocationForOutboundForDomestic(Integer year) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepository.findByEmployeeId(userDetails.getUsername());

    String role = user.getRoles().stream()
            .map(Roles::getName)
            .findFirst()
            .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));

    Specification<DomesticShipment> specification;
    if (role.equals("ROLE_ADMIN")) {
      specification = DomesticShipmentSpecification.withCreatedYearAndUser(year, null);
    } else {
      specification = DomesticShipmentSpecification.withCreatedYearAndUser(year, user);
    }

    List<DomesticShipment> shipments = domesticShipmentRepository.findAll(specification);

    Map<String, Integer> outboundMap = new HashMap<>();

    for (DomesticShipment domesticShipment : shipments) {
      String originLocation = domesticShipment.getOriginLocation();
      if (outboundMap.containsKey(originLocation)) {
        outboundMap.put(originLocation, outboundMap.get(originLocation) + 1);
      } else {
        outboundMap.put(originLocation, 1);
      }
    }
    return outboundMap;
  }

  public Map<String, Map<String, Integer>> getOutBoundForDashboardTest(Integer year) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepository.findByEmployeeId(userDetails.getUsername());

    String role = user.getRoles().stream()
            .map(Roles::getName)
            .findFirst()
            .orElseThrow(() -> new RecordNotFoundException("Role is incorrect"));

    Specification<DomesticShipment> specification;
    if (role.equals("ROLE_ADMIN")) {
      specification = DomesticShipmentSpecification.withCreatedYearAndUser(year, null);
    } else {
      specification = DomesticShipmentSpecification.withCreatedYearAndUser(year, user);
    }

    List<DomesticShipment> shipments = domesticShipmentRepository.findAll(specification);
    Map<String, Map<String, Integer>> map = new HashMap<>();

    for (DomesticShipment domesticShipment : shipments) {
      Map<String, Integer> innerMap = new HashMap<>();

      if (map.containsKey(domesticShipment.getOriginLocation())) {

      } else {
        List<DomesticShipment> collect = shipments.stream().filter(shipment -> shipment.getOriginLocation().equals(domesticShipment.getOriginLocation())).collect(Collectors.toList());
        for (DomesticShipment collectDomesticShipment : collect) {
          String destinationLocation = collectDomesticShipment.getDestinationLocation();

          if (innerMap.containsKey(destinationLocation)) {
            int count = innerMap.get(destinationLocation);
            innerMap.put(destinationLocation, count + 1);
          } else {
            innerMap.put(destinationLocation, 1);
          }
        }
        map.put(domesticShipment.getOriginLocation(), innerMap);

      }
    }
    return map;
  }
}
