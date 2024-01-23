package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForSummary;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.dto.LocationDto;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.exception.UserNotFoundException;
import com.example.CargoTracking.repository.*;
import com.example.CargoTracking.specification.DomesticShipmentSpecification;
import com.example.CargoTracking.specification.DomesticSummarySpecification;
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

import java.util.concurrent.CompletableFuture;


import javax.transaction.Transactional;
import java.rmi.server.ExportException;
import java.time.Duration;
import java.util.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
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


    @Transactional
    public DomesticShipmentDto addShipment(DomesticShipmentDto domesticShipmentDto) throws IOException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);

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

            List<DomesticShipment> all = domesticShipmentRepository.findAll();
            for (DomesticShipment domesticShipment : all) {
                if (domesticShipment.getPreAlertNumber().equals(domesticShipmentDto.getPreAlertNumber())) {
                    throw new RecordNotFoundException(String.format("Shipment with the given pre alert number is already exist"));
                }
            }
            DomesticShipment unSaveDomesticShipment = toEntity(domesticShipmentDto);
            unSaveDomesticShipment.setCreatedAt(LocalDate.now());
            unSaveDomesticShipment.setCreatedBy(user);
            unSaveDomesticShipment.setRedFlag(Boolean.FALSE);
            unSaveDomesticShipment.setActiveStatus(Boolean.TRUE);
            unSaveDomesticShipment.setPreAlertType("Domestic");
            unSaveDomesticShipment.setCreatedTime(LocalDateTime.now());

            DomesticShipment domesticShipment = domesticShipmentRepository.save(unSaveDomesticShipment);

            DomesticShipmentHistory domesticShipmentHistory = DomesticShipmentHistory.builder()
                    .status(domesticShipmentDto.getStatus())
                    .activeStatus(Boolean.TRUE)
                    .processTime(LocalDateTime.now())
                    .locationCode(domesticShipmentDto.getOriginLocation())
                    .user(user.getId())
                    .domesticShipment(domesticShipment)
                    .remarks(domesticShipment.getRemarks())
                    .build();

            domesticShipmentHistoryRepository.save(domesticShipmentHistory);

            String originEmails = locationService.getLocationByName(domesticShipment.getOriginLocation(), "Domestic")
                    .getOriginEmail();
            String[] resultListOrigin = originEmails.split(",");
            List<String> originEmailAddresses = new ArrayList<>(Arrays.asList(resultListOrigin));

            String destinationEmails = locationService.getLocationByName(domesticShipment.getDestinationLocation(), "Domestic")
                    .getDestinationEmail();
            String[] resultListDestination = destinationEmails.split(",");
            List<String> destinationEmailAddresses = new ArrayList<>(Arrays.asList(resultListDestination));


            List<String> emails = new ArrayList<>();
            emails.addAll(originEmailAddresses);
            emails.addAll(destinationEmailAddresses);

            String subject = "TSM Pre-Alert(D): " + domesticShipment.getRouteNumber() + "/" + domesticShipment.getVehicleType() + "/" + domesticShipment.getReferenceNumber() + "/" + domesticShipment.getEtd();
            Map<String, Object> model = new HashMap<>();
            model.put("field1", domesticShipment.getCreatedAt().toString());
            model.put("field2", domesticShipment.getReferenceNumber());
            model.put("field3", domesticShipment.getOriginLocation());
            model.put("field4", domesticShipment.getDestinationLocation());
            model.put("field5", domesticShipment.getNumberOfShipments() != null ? domesticShipment.getNumberOfShipments().toString() : "0");
            model.put("field6", domesticShipment.getVehicleType());
            model.put("field7", domesticShipment.getNumberOfBags().toString());
            model.put("field8", domesticShipment.getNumberOfPallets().toString());
            model.put("field9", domesticShipment.getWeight().toString());
            model.put("field10", "Road");
            model.put("field11", domesticShipment.getEtd().toLocalDate().toString());
            model.put("field12", domesticShipment.getEta().toLocalDate().toString());
            model.put("field13", domesticShipment.getEtd().toLocalTime().toString());
            model.put("field14", domesticShipment.getEta().toLocalTime().toString());
            model.put("field15", domesticShipment.getRemarks());

            sendEmailsAsync(emails, subject, "domestic-email-template.ftl", model);
            return toDto(domesticShipment);
        }

        throw new UserNotFoundException(String.format("User not found while creating domestic shipment"));
    }

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
            User user = userRepository.findByEmail(username);
            String role ="";
            for (Roles roleList:user.getRoles()) {
                Optional<Roles> roles = Optional.ofNullable(roleRepository
                        .findByName(roleList.getName())
                        .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

                role = roles.get().getName();
            }
            if(role.equals("ROLE_ADMIN")){
                searchCriteriaForDomesticShipment.setUser(null);
                Specification<DomesticShipment> domesticShipmentSpecification = DomesticShipmentSpecification.getSearchSpecification(searchCriteriaForDomesticShipment);
                Page<DomesticShipment> domesticShipmentPage = domesticShipmentRepository.findAll(domesticShipmentSpecification, pageable);
                Page<DomesticShipmentDto> domesticShipmentDtoPage = domesticShipmentPage.map(entity -> toDto(entity));

                return domesticShipmentDtoPage;
            }else{
                searchCriteriaForDomesticShipment.setUser(user);
                Specification<DomesticShipment> domesticShipmentSpecification = DomesticShipmentSpecification.getSearchSpecification(searchCriteriaForDomesticShipment);
                Page<DomesticShipment> domesticShipmentPage = domesticShipmentRepository.findAll(domesticShipmentSpecification, pageable);
                Page<DomesticShipmentDto> domesticShipmentDtoPage = domesticShipmentPage.map(entity -> toDto(entity));
                return domesticShipmentDtoPage;
            }
//            if ((user.getLocation() == null) &&
//                    (searchCriteriaForDomesticShipment.getFromDate().isEmpty() && searchCriteriaForDomesticShipment.getToDate().isEmpty() &&
//                            searchCriteriaForDomesticShipment.getOrigin().isEmpty() && searchCriteriaForDomesticShipment.getDestination().isEmpty() &&
//                            searchCriteriaForDomesticShipment.getStatus().isEmpty() && searchCriteriaForDomesticShipment.getRouteNumber().isEmpty())) {
//
//                Page<DomesticShipment> domesticShipmentPage = domesticShipmentRepository.findAllByActiveStatus(pageable,
//                        searchCriteriaForDomesticShipment.isActiveStatus());
//                Page<DomesticShipmentDto> domesticShipmentDtoPage = domesticShipmentPage.map(entity -> toDto(entity));
//                return domesticShipmentDtoPage;
//
//            }
//            if ((user.getLocation() == null) &&
//                    ((!searchCriteriaForDomesticShipment.getFromDate().isEmpty() || !searchCriteriaForDomesticShipment.getToDate().isEmpty() ||
//                            !searchCriteriaForDomesticShipment.getOrigin().isEmpty() || !searchCriteriaForDomesticShipment.getDestination().isEmpty() ||
//                            !searchCriteriaForDomesticShipment.getStatus().isEmpty() || !searchCriteriaForDomesticShipment.getRouteNumber().isEmpty()))) {
//                searchCriteriaForDomesticShipment.setUser(null);
//                Specification<DomesticShipment> domesticShipmentSpecification = DomesticShipmentSpecification.getSearchSpecification(searchCriteriaForDomesticShipment);
//                Page<DomesticShipment> domesticShipmentPage = domesticShipmentRepository.findAll(domesticShipmentSpecification, pageable);
//                Page<DomesticShipmentDto> domesticShipmentDtoPage = domesticShipmentPage.map(entity -> toDto(entity));
//
//                return domesticShipmentDtoPage;
//            } else {
//                searchCriteriaForDomesticShipment.setUser(user);
//                Specification<DomesticShipment> domesticShipmentSpecification = DomesticShipmentSpecification.getSearchSpecification(searchCriteriaForDomesticShipment);
//                Page<DomesticShipment> domesticShipmentPage = domesticShipmentRepository.findAll(domesticShipmentSpecification, pageable);
//                Page<DomesticShipmentDto> domesticShipmentDtoPage = domesticShipmentPage.map(entity -> toDto(entity));
//                return domesticShipmentDtoPage;
//
//            }
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

//    public Page<DomesticShipmentDto> getInboundShipment(SearchCriteriaForSummary searchCriteriaForSummary, int page, int size) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            Pageable pageable = PageRequest.of(page, size);
//            Page<DomesticShipment> domesticShipmentPage;
//            String username = ((UserDetails) principal).getUsername();
//            User user = userRepository.findByEmail(username);
//            if ((user.getLocation() == null) && ((searchCriteriaForSummary.getDestination() == null || searchCriteriaForSummary.getDestination() == "") && (searchCriteriaForSummary.getOrigin() == null|| searchCriteriaForSummary.getOrigin()=="" )
//                    && (searchCriteriaForSummary.getToDate() == null || searchCriteriaForSummary.getToDate() == "") && (searchCriteriaForSummary.getFromDate() == null || searchCriteriaForSummary.getFromDate()=="")
//                    && (searchCriteriaForSummary.getStatus() == null|| searchCriteriaForSummary.getStatus() == ""))) {
//                throw new RecordNotFoundException(String.format("Domestic shipment Not Found because user haven't an origin"));
//            }
//            if ((searchCriteriaForSummary.getDestination() == null || searchCriteriaForSummary.getDestination()=="" ) && (searchCriteriaForSummary.getOrigin() == null ||searchCriteriaForSummary.getOrigin() =="")
//                    && (searchCriteriaForSummary.getToDate() == null|| searchCriteriaForSummary.getToDate() == "") && (searchCriteriaForSummary.getFromDate() == null || searchCriteriaForSummary.getFromDate()=="")
//                    && (searchCriteriaForSummary.getStatus() == null|| searchCriteriaForSummary.getStatus() == "")) {
//                Page<DomesticShipment> pageDomesticShipment =
//                        domesticShipmentRepository.findByDestinationLocation(user.getLocation().getLocationName(), pageable);
//                Page<DomesticShipmentDto> pageDomesticShipmentDto = pageDomesticShipment.map(entity -> toDto(entity));
//                return pageDomesticShipmentDto;
//            } else {
//                if (user.getLocation() != null) {
//                    if (searchCriteriaForSummary.getDestination() == null || searchCriteriaForSummary.getDestination().isEmpty()) {
//                        searchCriteriaForSummary.setDestination(user.getLocation().getLocationName());
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
//        }
//
//        throw new UserNotFoundException("User not found");
//    }
//
    @Transactional
    public DomesticShipmentDto updateDomesticShipment(Long id, DomesticShipmentDto domesticShipmentDto) {
        Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);
        if (domesticShipment.isPresent()) {
            domesticShipment.get().setUpdatedAt(LocalDate.now());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userRepository.findByEmail(username);
                domesticShipment.get().setUpdatedBy(user);
                List<DomesticShipment> all = domesticShipmentRepository.findAll();
                for (DomesticShipment domesticShipmentForPreAlertNumber : all) {
                    if (domesticShipmentForPreAlertNumber.getPreAlertNumber().equals(domesticShipmentDto.getPreAlertNumber())) {
                        if (domesticShipment.get().getId() != domesticShipmentDto.getId()) {
                            throw new RecordNotFoundException(String.format("Shipment with the given pre alert number is already exist"));

                        }
                    }
                }
                domesticShipment.get().setPreAlertNumber(domesticShipmentDto.getPreAlertNumber());
                domesticShipment.get().setOriginFacility(domesticShipmentDto.getOriginFacility());
                domesticShipment.get().setOriginLocation(domesticShipmentDto.getOriginLocation());
                domesticShipment.get().setRefrigeratedTruck(domesticShipmentDto.getRefrigeratedTruck());
                domesticShipment.get().setDestinationFacility(domesticShipmentDto.getDestinationFacility());
                domesticShipment.get().setDestinationLocation(domesticShipmentDto.getDestinationLocation());
                domesticShipment.get().setRouteNumber(domesticShipmentDto.getRouteNumber());
                domesticShipment.get().setNumberOfShipments(domesticShipmentDto.getNumberOfShipments());
                domesticShipment.get().setWeight(domesticShipmentDto.getWeight());
                domesticShipment.get().setEtd(domesticShipmentDto.getEtd());
                domesticShipment.get().setEta(domesticShipmentDto.getEta());
                domesticShipment.get().setAtd(domesticShipmentDto.getAtd());
                domesticShipment.get().setDriverName(domesticShipmentDto.getDriverName());
                domesticShipment.get().setDriverContact(domesticShipmentDto.getDriverContact());
                domesticShipment.get().setReferenceNumber(domesticShipmentDto.getReferenceNumber());
                domesticShipment.get().setVehicleType(domesticShipmentDto.getVehicleType());
                domesticShipment.get().setNumberOfPallets(domesticShipmentDto.getNumberOfPallets());
                domesticShipment.get().setNumberOfBags(domesticShipmentDto.getNumberOfBags());
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

//                  if(!domesticShipment.get().getStatus().equals(domesticShipmentDto.getStatus())){
//                      List<String> emails = userRepository.findEmailByLocation(domesticShipment.get().getDestinationLocation());
//                      emails.add(domesticShipment.get().getCreatedBy().getEmail());
//
//                      for (String to :emails) {
////                          emailService.sendHtmlEmail(to,"Shipment status is changed");
//                      }
//
//                  }

                if (domesticShipmentDto.getStatus().equalsIgnoreCase("Arrived")) {
                    domesticShipment.get().setArrivedTime(LocalDateTime.now());
                    Duration duration = Duration.between(domesticShipment.get().getCreatedTime(), LocalDateTime.now());
                    domesticShipment.get().setTransitTimeTaken(duration.toMinutes());
                }

                if (domesticShipmentDto.getStatus().equalsIgnoreCase("Cleared")) {
                    if (domesticShipment.get().getRedFlag()) {
                        domesticShipment.get().setRedFlag(false);
                    }
                }
                DomesticShipmentHistory domesticShipmentHistory;
                DomesticShipment save;
                if (domesticShipment.get().getStatus() != domesticShipmentDto.getStatus()) {
                    domesticShipment.get().setStatus(domesticShipmentDto.getStatus());
                    save = domesticShipmentRepository.save(domesticShipment.get());

                    domesticShipmentHistory = DomesticShipmentHistory.builder()
                            .status(save.getStatus())
                            .processTime(LocalDateTime.now())
                            .locationCode(save.getOriginLocation())
                            .user(user.getId())
                            .domesticShipment(save)
                            .remarks(save.getRemarks())
                            .build();

                    domesticShipmentHistoryRepository.save(domesticShipmentHistory);
                } else {
                    save = domesticShipmentRepository.save(domesticShipment.get());
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

    public ApiResponse addAttachment(Long id, String attachmentType, MultipartFile file) throws IOException {
        Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);
        FileMetaData byFileName = fileMetaDataRepository.findByFileName(file.getOriginalFilename());
        if (byFileName == null) {
            String fileUrl = storageService.uploadFile(file.getBytes(), file.getOriginalFilename());
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            FileMetaData fileMetaData = new FileMetaData();
            fileMetaData.setFileUrl(fileUrl);
            fileMetaData.setFileExtension(fileExtension);
            fileMetaData.setFileName(file.getOriginalFilename());
            fileMetaData.setDomesticShipment(domesticShipment.get());
            fileMetaData.setAttachmentType(attachmentType);
            fileMetaDataRepository.save(fileMetaData);
            return ApiResponse.builder()
                    .message("File uploaded to the server successfully")
                    .statusCode(HttpStatus.OK.value())
                    .result(Collections.emptyList())
                    .build();
        } else {
            throw new RecordNotFoundException(String.format("File already exists on the bucket with this name"));
        }

    }
//
//    @Scheduled(fixedRate = 20 * 60 * 1000)
////    @Scheduled(cron = "0 0 12 * * ?")
//    public void redFlag() {
//
//        LocalDate oneDayOlderDate = LocalDate.now().minusDays(1);
//
//        List<DomesticShipment> domesticShipmentList = domesticShipmentRepository.findByCreatedAt(oneDayOlderDate);
//
//        try {
//            LocalDateTime currentDate = LocalDateTime.now();
//
//            if (!domesticShipmentList.isEmpty()) {
//                for (DomesticShipment entity : domesticShipmentList) {
//                    if (entity.getEta().isBefore(currentDate) && !entity.getRedFlag() && !entity.getStatus().equals("Arrived")) {
//                        entity.setRedFlag(true);
//                    }
//                }
//
//                domesticShipmentRepository.saveAll(domesticShipmentList);
//            }
//
//            List<DomesticShipment> domesticShipmentList1 = domesticShipmentRepository.findAll();
//            if (!domesticShipmentList1.isEmpty()) {
//                for (DomesticShipment shipment : domesticShipmentList1) {
//                    if (shipment.getArrivedTime() != null && shipment.getStatus() != "Cleared") {
//                        Duration duration = Duration.between(shipment.getArrivedTime(), LocalDateTime.now());
//                        if (duration.toMinutes() >= 480 && duration.toMinutes() <= 1440) {
//                            if (!shipment.isEscalationFlagOne()) {
//
//                                String originEmails = locationService.getLocationByName(shipment.getOriginLocation(), "Domestic")
//                                        .getOriginEscalation();
//                                String[] resultListOriginEscalation = originEmails.split(",");
//
//                                String destinationEmails = locationService.getLocationByName(shipment.getDestinationLocation(), "Domestic")
//                                        .getDestinationEscalation();
//                                String[] resultListDestinationEscalation = destinationEmails.split(",");
//
//                                List<String> emails = new ArrayList<>();
//                                emails.add(resultListOriginEscalation[0]);
//                                emails.add(resultListDestinationEscalation[0]);
//                                String subject = "TSM 1st Escalation (D): " + shipment.getRouteNumber() + "/" + shipment.getVehicleType() + "/" + shipment.getReferenceNumber() + "/" + shipment.getEtd();
//                                Map<String, Object> model = new HashMap<>();
//                                model.put("field1", shipment.getReferenceNumber());
//                                model.put("field2", shipment.getDestinationLocation());
//                                for (String to : emails) {
//                                    emailService.sendHtmlEmail(to, subject, "shipment-delay-email-template.ftl", model);
//                                }
//                                shipment.setEscalationFlagOne(true);
//                            }
//                        }
//                        if (duration.toMinutes() >= 1441 && duration.toMinutes() >= 2880) {
//                            if (!shipment.isEscalationFlagTwo()) {
//                                String originEmails = locationService.getLocationByName(shipment.getOriginLocation(), "Domestic")
//                                        .getOriginEscalation();
//                                String[] resultListOriginEscalation = originEmails.split(",");
//
//                                String destinationEmails = locationService.getLocationByName(shipment.getDestinationLocation(), "Domestic")
//                                        .getDestinationEscalation();
//                                String[] resultListDestinationEscalation = destinationEmails.split(",");
//                                List<String> emails = new ArrayList<>();
//                                emails.add(resultListOriginEscalation[1]);
//                                emails.add(resultListDestinationEscalation[1]);
//                                String subject = "TSM 2nd Escalation (D): " + shipment.getRouteNumber() + "/" + shipment.getVehicleType() + "/" + shipment.getReferenceNumber() + "/" + shipment.getEtd();
//                                Map<String, Object> model = new HashMap<>();
//                                model.put("field1", shipment.getReferenceNumber());
//                                model.put("field2", shipment.getDestinationLocation());
//                                for (String to : emails) {
//                                    emailService.sendHtmlEmail(to, subject, "shipment-delay-email-template.ftl", model);
//                                }
//                                shipment.setEscalationFlagTwo(true);
//                            }
//                        }
//                        if (duration.toMinutes() >= 2881) {
//                            if (!shipment.isEscalationFlagThree()) {
//                                String originEmails = locationService.getLocationByName(shipment.getOriginLocation(), "Domestic")
//                                        .getOriginEscalation();
//                                String[] resultListOriginEscalation = originEmails.split(",");
//
//                                String destinationEmails = locationService.getLocationByName(shipment.getDestinationLocation(), "Domestic")
//                                        .getDestinationEscalation();
//                                String[] resultListDestinationEscalation = destinationEmails.split(",");
//                                List<String> emails = new ArrayList<>();
//                                emails.add(resultListOriginEscalation[2]);
//                                emails.add(resultListDestinationEscalation[2]);
//                                String subject = "TSM 3rd Escalation (D): " + shipment.getRouteNumber() + "/" + shipment.getVehicleType() + "/" + shipment.getReferenceNumber() + "/" + shipment.getEtd();
//                                Map<String, Object> model = new HashMap<>();
//                                model.put("field1", shipment.getReferenceNumber());
//                                model.put("field2", shipment.getDestinationLocation());
//                                for (String to : emails) {
//                                    emailService.sendHtmlEmail(to, subject, "shipment-delay-email-template.ftl", model);
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
//
//    public Page<DomesticShipmentDto> getAllForSummery(SearchCriteriaForSummary searchCriteriaForSummary, int page, int size) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            Pageable pageable = PageRequest.of(page, size);
//            Page<DomesticShipment> domesticShipmentPage;
//            String username = ((UserDetails) principal).getUsername();
//            User user = userRepository.findByEmail(username);
//            if ((user.getLocation() == null) && (searchCriteriaForSummary.getDestination() == null && searchCriteriaForSummary.getOrigin() == null
//                    && searchCriteriaForSummary.getToDate() == null && searchCriteriaForSummary.getFromDate() == null
//                    && searchCriteriaForSummary.getStatus() == null)) {
//                throw new RecordNotFoundException(String.format("Domestic shipment Not Found because user haven't an origin"));
//            }
//            if (searchCriteriaForSummary.getDestination() == null && searchCriteriaForSummary.getOrigin() == null
//                    && searchCriteriaForSummary.getToDate() == null && searchCriteriaForSummary.getFromDate() == null
//                    && searchCriteriaForSummary.getStatus() == null) {
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



    public List<DomesticShipmentDto> toDtoList(List<DomesticShipment> domesticShipmentList) {
        return domesticShipmentList.stream().map(this::toDto).collect(Collectors.toList());
    }

    private DomesticShipmentDto toDto(DomesticShipment domesticShipment) {
        return modelMapper.map(domesticShipment, DomesticShipmentDto.class);
    }


    private DomesticShipment toEntity(DomesticShipmentDto domesticShipmentDto) {
        return modelMapper.map(domesticShipmentDto, DomesticShipment.class);
    }
}
