package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.InternationalShipmentDto;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.repository.InternationalShipmentRepository;

import com.example.CargoTracking.model.InternationalShipmentHistory;
import com.example.CargoTracking.repository.InternationalShipmentHistoryRepository;
import com.example.CargoTracking.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


    public InternationalShipmentDto addShipment(InternationalShipmentDto internationalShipmentDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);


            InternationalShipment unSaveInternationalShipment = toEntity(internationalShipmentDto);
            unSaveInternationalShipment.setCreatedAt(LocalDate.now());
            unSaveInternationalShipment.setCreatedBy(user);
            InternationalShipment internationalShipment = internationalShipmentRepository
                    .save(unSaveInternationalShipment);


            InternationalShipmentHistory shipmentHistory = InternationalShipmentHistory.builder()
                    .status("Pre-Alert Created")
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
        throw new RuntimeException("Error creating international shipment");
    }

    public List<InternationalShipmentDto> getAll() {
        return toDtoList(internationalShipmentRepository.findAll());
    }
    public List<InternationalShipmentDto> getAllByUserAndForAir() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return toDtoList(internationalShipmentRepository.findAllForAir());
            }
            return toDtoList(internationalShipmentRepository.findAllByCreatedByForAir(user));
        }
        throw new RuntimeException("User Not Found");
    }

    public List<InternationalShipmentDto> getAllByUserAndForRoad() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return toDtoList(internationalShipmentRepository.findAllForRoad());
            }
            return toDtoList(internationalShipmentRepository.findAllByCreatedByForRoad(user));
        }
        throw new RuntimeException("User Not Found");
    }

    public InternationalShipmentDto getById(Long id) {
        Optional<InternationalShipment> internationalShipment = internationalShipmentRepository.findById(id);
        if(internationalShipment.isPresent()){
            return toDto(internationalShipment.get());
        }
        throw new RuntimeException(String.format("International shipment Not Found By This Id %d",id));
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

    public List<InternationalShipmentDto> getInternationalOutBoundSummeryForAir() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return new ArrayList<>();
            }
            return toDtoList(internationalShipmentRepository.findByOriginCountryByAir(user.getLocation().getLocationName()));
        }
        throw new RuntimeException("Shipment not found");
    }

    public List<InternationalShipmentDto> getInternationalOutBoundSummeryForRoad() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return new ArrayList<>();
            }
            return toDtoList(internationalShipmentRepository.findByOriginCountryByRoad(user.getLocation().getLocationName()));
        }
        throw new RuntimeException("Shipment not found");
    }
    public List<InternationalShipmentDto> getInternationalInBoundSummeryForAir(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return new ArrayList<>();
            }
            return toDtoList(internationalShipmentRepository.findByDestinationCountryByAir(user.getLocation().getLocationName()));
        }
        throw new RuntimeException("Shipment not found");
    }

    public List<InternationalShipmentDto> getInternationalInBoundSummeryForRoad(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return new ArrayList<>();
            }
            return toDtoList(internationalShipmentRepository.findByDestinationCountryByRoad(user.getLocation().getLocationName()));
        }
        throw new RuntimeException("Shipment not found");
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
            }else{
                throw new RuntimeException("Error Updating international shipment");
            }
            internationalShipment.get().setOriginCountry(internationalShipmentDto.getOriginCountry());
            internationalShipment.get().setOriginPort(internationalShipmentDto.getOriginPort());
            internationalShipment.get().setRefrigeratedTruck(internationalShipmentDto.getRefrigeratedTruck());
            internationalShipment.get().setType(internationalShipmentDto.getType());
            internationalShipment.get().setShipmentMode(internationalShipmentDto.getShipmentMode());
            internationalShipment.get().setPreAlertNumber(internationalShipmentDto.getPreAlertNumber());
            internationalShipment.get().setDestinationCountry(internationalShipmentDto.getDestinationCountry());
            internationalShipment.get().setDestinationPort(internationalShipmentDto.getDestinationPort());
            internationalShipment.get().setCarrier(internationalShipmentDto.getCarrier());
            internationalShipment.get().setDepartureTime(internationalShipmentDto.getDepartureTime());
            internationalShipment.get().setDepartureDate(internationalShipmentDto.getDepartureDate());
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

            InternationalShipment save = internationalShipmentRepository.save(internationalShipment.get());
            return toDto(save);
        }else{
            throw new RuntimeException("International Shipment Not Found");
        }
    }
}
