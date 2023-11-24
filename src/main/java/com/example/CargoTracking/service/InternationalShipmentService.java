package com.example.CargoTracking.service;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalShipment;
import com.example.CargoTracking.criteria.SearchCriteriaForInternationalSummary;
import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.dto.InternationalShipmentDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.exception.UserNotFoundException;
import com.example.CargoTracking.model.*;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.FileMetaDataRepository;
import com.example.CargoTracking.repository.InternationalShipmentRepository;

import com.example.CargoTracking.repository.InternationalShipmentHistoryRepository;
import com.example.CargoTracking.repository.UserRepository;
import com.example.CargoTracking.specification.DomesticShipmentSpecification;
import com.example.CargoTracking.specification.DomesticSummarySpecification;
import com.example.CargoTracking.specification.InternationalShipmentSpecification;
import com.example.CargoTracking.specification.InternationalSummarySpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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


    public InternationalShipmentDto addShipment(InternationalShipmentDto internationalShipmentDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);


            InternationalShipment unSaveInternationalShipment = toEntity(internationalShipmentDto);
            unSaveInternationalShipment.setCreatedAt(LocalDate.now());
            unSaveInternationalShipment.setCreatedBy(user);
            unSaveInternationalShipment.setRedFlag(Boolean.FALSE);
            unSaveInternationalShipment.setPreAlertNumber(System.currentTimeMillis() / 1000);
            unSaveInternationalShipment.setPreAlertType(unSaveInternationalShipment.getType().equalsIgnoreCase("By Road") ? "International-Road" : "International-Air");
            unSaveInternationalShipment.setCreatedTime(LocalDateTime.now());
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

            List<String> emails = userRepository.findEmailByLocation(internationalShipmentDto.getDestinationCountry());

            for (String to :emails) {
                emailService.sendEmail(to,"Shipment Notification");
            }

            return  toDto(internationalShipment);

        }
        throw new UserNotFoundException(String.format("User not found while creating international shipment"));
    }

    public List<InternationalShipmentDto> getAll() {
        return toDtoList(internationalShipmentRepository.findAll());
    }

    public Page<InternationalShipmentDto>   getAllByUserAndForAir(SearchCriteriaForInternationalShipment searchCriteriaForInternationalSummary, int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            Pageable pageable = PageRequest.of(page, size);

            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if((user.getLocation() == null) &&
                    (searchCriteriaForInternationalSummary.getValue().isEmpty() || searchCriteriaForInternationalSummary.getValue() == null)){
                Page<InternationalShipment> InternationalShipmentPage = internationalShipmentRepository.findAllForAir(pageable);
                Page<InternationalShipmentDto> InternationalShipmentDtoPage = InternationalShipmentPage.map(entity->toDto(entity));
                return InternationalShipmentDtoPage;
            }
            if((user.getLocation() == null) &&
                    ((!searchCriteriaForInternationalSummary.getValue().isEmpty() || searchCriteriaForInternationalSummary.getValue() != null))){
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
//            if(user.getLocation() == null){
//                return toDtoList(internationalShipmentRepository.findAllForAir());
//            }
//            return toDtoList(internationalShipmentRepository.findAllByCreatedByForAir(user));
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public Page<InternationalShipmentDto> getAllByUserAndForRoad(SearchCriteriaForInternationalShipment searchCriteriaForInternationalSummary, int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            Pageable pageable = PageRequest.of(page, size);
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if((user.getLocation() == null) &&
                    (searchCriteriaForInternationalSummary.getValue().isEmpty() || searchCriteriaForInternationalSummary.getValue() == null)){
                Page<InternationalShipment> InternationalShipmentPage = internationalShipmentRepository.findAllForRoad(pageable);
                Page<InternationalShipmentDto> InternationalShipmentDtoPage = InternationalShipmentPage.map(entity->toDto(entity));
                return InternationalShipmentDtoPage;
            }
            if((user.getLocation() == null) &&
                    ((!searchCriteriaForInternationalSummary.getValue().isEmpty() || searchCriteriaForInternationalSummary.getValue() != null))){
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
//            if(user.getLocation() == null){
//                return toDtoList(internationalShipmentRepository.findAllForRoad());
//            }
//            return toDtoList(internationalShipmentRepository.findAllByCreatedByForRoad(user));
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
    @Scheduled(cron = "0 0 12 * * ?")
    public void redFlag() {
        LocalDate oneDayOlderDate = LocalDate.now().minusDays(1);

        List<InternationalShipment> internationalShipmentList = internationalShipmentRepository.findByCreatedAt(oneDayOlderDate);

        try {
            LocalDate currentDate = LocalDate.now();

            for (InternationalShipment entity : internationalShipmentList) {
                if (entity.getEta().isBefore(currentDate) && !entity.getRedFlag() && !entity.getStatus().equals("Arrived")) {
                    entity.setRedFlag(true);
                }
            }

            internationalShipmentRepository.saveAll(internationalShipmentList);
        } catch (Exception e) {
            e.printStackTrace();
            Collections.emptyList();
        }
    }

    private List<InternationalShipmentDto> toDtoList(List<InternationalShipment> internationalShipmentList){
        return internationalShipmentList.stream().map(this::toDto).collect(Collectors.toList());
    }

    private InternationalShipment toEntity(InternationalShipmentDto internationalShipmentDto){
        return modelMapper.map(internationalShipmentDto,InternationalShipment.class);
    }

    private InternationalShipmentDto toDto(InternationalShipment internationalShipment){
        return modelMapper.map(internationalShipment,InternationalShipmentDto.class);
    }

    public Page<InternationalShipmentDto> getInternationalOutBoundSummeryForAir(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
                                                                                int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size);
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if((user.getLocation() == null) && (searchCriteriaForInternationalSummary.getDestination() == null && searchCriteriaForInternationalSummary.getOrigin() == null
                    && searchCriteriaForInternationalSummary.getToDate() == null && searchCriteriaForInternationalSummary.getFromDate() == null
                    && searchCriteriaForInternationalSummary.getStatus() ==null)){
                throw new RecordNotFoundException(String.format("International shipment Not Found because user haven't an origin"));
            }
            if(searchCriteriaForInternationalSummary.getDestination() == null && searchCriteriaForInternationalSummary.getOrigin() == null
                    && searchCriteriaForInternationalSummary.getToDate() == null && searchCriteriaForInternationalSummary.getFromDate() == null
                    && searchCriteriaForInternationalSummary.getStatus() ==null){
                Page<InternationalShipment> pageInternationalShipment =
                        internationalShipmentRepository.findByOriginCountryByAir(user.getLocation().getLocationName(),
                                pageable);
                Page<InternationalShipmentDto> internationalShipmentDtoPage =pageInternationalShipment.map(entity->toDto(entity));
                return internationalShipmentDtoPage;
            }else{
                if(user.getLocation() != null){
                    if(searchCriteriaForInternationalSummary.getOrigin() == null || searchCriteriaForInternationalSummary.getOrigin().isEmpty()) {
                        searchCriteriaForInternationalSummary.setOrigin(user.getLocation().getLocationName());
                    }
                }
                searchCriteriaForInternationalSummary.setType("By Air");
                Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
                Page<InternationalShipment> internationalShipmentPage =
                        internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
                Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );
                return internationalShipmentPageDto;
            }
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public Page<InternationalShipmentDto> getInternationalInBoundSummeryForAir(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
                                                                               int page, int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size);
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if((user.getLocation() == null) && (searchCriteriaForInternationalSummary.getDestination() == null && searchCriteriaForInternationalSummary.getOrigin() == null
                    && searchCriteriaForInternationalSummary.getToDate() == null && searchCriteriaForInternationalSummary.getFromDate() == null
                    && searchCriteriaForInternationalSummary.getStatus() ==null)){
                throw new RecordNotFoundException(String.format("International shipment Not Found because user haven't an origin"));
            }
            if(searchCriteriaForInternationalSummary.getDestination() == null && searchCriteriaForInternationalSummary.getOrigin() == null
                    && searchCriteriaForInternationalSummary.getToDate() == null && searchCriteriaForInternationalSummary.getFromDate() == null
                    && searchCriteriaForInternationalSummary.getStatus() ==null){
                Page<InternationalShipment> pageInternationalShipment =
                        internationalShipmentRepository.findByDestinationCountryByAir(user.getLocation().getLocationName(),
                                pageable);
                Page<InternationalShipmentDto> internationalShipmentDtoPage =pageInternationalShipment.map(entity->toDto(entity));
                return internationalShipmentDtoPage;
            }else{
                if(user.getLocation() != null){
                    if(searchCriteriaForInternationalSummary.getDestination() == null || searchCriteriaForInternationalSummary.getDestination().isEmpty() ){
                        searchCriteriaForInternationalSummary.setDestination(user.getLocation().getLocationName());

                    }
                }
                searchCriteriaForInternationalSummary.setType("By Air");
                Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
                Page<InternationalShipment> internationalShipmentPage =
                        internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
                Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );
                return internationalShipmentPageDto;
            }
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public Page<InternationalShipmentDto> getInternationalOutBoundSummeryForRoad(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
                                                                                 int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size);
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if((user.getLocation() == null) && (searchCriteriaForInternationalSummary.getDestination() == null && searchCriteriaForInternationalSummary.getOrigin() == null
                    && searchCriteriaForInternationalSummary.getToDate() == null && searchCriteriaForInternationalSummary.getFromDate() == null
                    && searchCriteriaForInternationalSummary.getStatus() ==null)){
                throw new RecordNotFoundException(String.format("International shipment Not Found because user haven't an origin"));
            }
            if(searchCriteriaForInternationalSummary.getDestination() == null && searchCriteriaForInternationalSummary.getOrigin() == null
                    && searchCriteriaForInternationalSummary.getToDate() == null && searchCriteriaForInternationalSummary.getFromDate() == null
                    && searchCriteriaForInternationalSummary.getStatus() ==null){
                Page<InternationalShipment> pageInternationalShipment =
                        internationalShipmentRepository.findByOriginCountryByRoad(user.getLocation().getLocationName(),
                                pageable);
                Page<InternationalShipmentDto> internationalShipmentDtoPage =pageInternationalShipment.map(entity->toDto(entity));
                return internationalShipmentDtoPage;
            }else{
                if(user.getLocation() != null){
                    searchCriteriaForInternationalSummary.setOrigin(user.getLocation().getLocationName());
                }
                searchCriteriaForInternationalSummary.setType("By Road");
                Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
                Page<InternationalShipment> internationalShipmentPage =
                        internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
                Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );
                return internationalShipmentPageDto;
            }
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public Page<InternationalShipmentDto> getInternationalInBoundSummeryForRoad(SearchCriteriaForInternationalSummary searchCriteriaForInternationalSummary,
                                                                                int page, int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            Pageable pageable = PageRequest.of(page, size);
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if((user.getLocation() == null) && (searchCriteriaForInternationalSummary.getDestination() == null && searchCriteriaForInternationalSummary.getOrigin() == null
                    && searchCriteriaForInternationalSummary.getToDate() == null && searchCriteriaForInternationalSummary.getFromDate() == null
                    && searchCriteriaForInternationalSummary.getStatus() ==null)){
                throw new RecordNotFoundException(String.format("International shipment Not Found because user haven't an origin"));
            }
            if(searchCriteriaForInternationalSummary.getDestination() == null && searchCriteriaForInternationalSummary.getOrigin() == null
                    && searchCriteriaForInternationalSummary.getToDate() == null && searchCriteriaForInternationalSummary.getFromDate() == null
                    && searchCriteriaForInternationalSummary.getStatus() ==null){
                Page<InternationalShipment> pageInternationalShipment =
                        internationalShipmentRepository.findByDestinationCountryByRoad(user.getLocation().getLocationName(),
                                pageable);
                Page<InternationalShipmentDto> internationalShipmentDtoPage =pageInternationalShipment.map(entity->toDto(entity));
                return internationalShipmentDtoPage;
            }else{
                if(user.getLocation() != null){
                    searchCriteriaForInternationalSummary.setOrigin(user.getLocation().getLocationName());
                }
                searchCriteriaForInternationalSummary.setType("By Road");
                Specification<InternationalShipment> internationalShipmentSpecification = InternationalSummarySpecification.getSearchSpecification(searchCriteriaForInternationalSummary);
                Page<InternationalShipment> internationalShipmentPage =
                        internationalShipmentRepository.findAll(internationalShipmentSpecification, pageable);
                Page<InternationalShipmentDto> internationalShipmentPageDto = internationalShipmentPage.map(entity -> toDto(entity) );
                return internationalShipmentPageDto;
            }
        }
        throw new UserNotFoundException(String.format("User not found"));
    }

    public InternationalShipmentDto updateInternationalShipment(Long id, InternationalShipmentDto internationalShipmentDto) {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        if(internationalShipment.isPresent()){
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof UserDetails){
                String username = ((UserDetails) principal).getUsername();
                User user = userRepository.findByEmail(username);

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
                internationalShipment.get().setDepartureDate(internationalShipmentDto.getDepartureDate());
                internationalShipment.get().setDepartureTime(internationalShipmentDto.getDepartureTime());
                internationalShipment.get().setEtd(internationalShipmentDto.getEtd());
                internationalShipment.get().setEta(internationalShipmentDto.getEta());
                internationalShipment.get().setAtd(internationalShipmentDto.getAtd());
                internationalShipment.get().setFlightNumber(internationalShipmentDto.getFlightNumber());
                internationalShipment.get().setNumberOfShipments(internationalShipmentDto.getNumberOfShipments());
                internationalShipment.get().setArrivalDate(internationalShipmentDto.getArrivalDate());
                internationalShipment.get().setArrivalTime(internationalShipmentDto.getArrivalTime());
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
                internationalShipment.get().setStatus(internationalShipmentDto.getStatus());
                internationalShipment.get().setRemarks(internationalShipmentDto.getRemarks());
                internationalShipment.get().setAta(internationalShipmentDto.getAta());
                internationalShipment.get().setTotalShipments(internationalShipmentDto.getTotalShipments());
                internationalShipment.get().setOverages(internationalShipmentDto.getOverages());
                internationalShipment.get().setOverageAWBs(internationalShipmentDto.getOverageAWBs());
                internationalShipment.get().setReceived(internationalShipmentDto.getReceived());
                internationalShipment.get().setShortages(internationalShipmentDto.getShortages());
                internationalShipment.get().setShortageAWBs(internationalShipmentDto.getShortageAWBs());
                internationalShipment.get().setRouteNumber(internationalShipmentDto.getRouteNumber());
                if(internationalShipment.get().getStatus().equalsIgnoreCase("Arrived")){
                    Duration duration = Duration.between(internationalShipment.get().getCreatedTime(), LocalDateTime.now());
                    internationalShipment.get().setTransitTimeTaken(duration.toMinutes());
                }
                InternationalShipment save = internationalShipmentRepository.save(internationalShipment.get());
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
}
