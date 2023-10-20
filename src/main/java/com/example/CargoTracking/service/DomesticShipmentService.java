package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.DomesticShipmentHistory;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.repository.DomesticShipmentHistoryRepository;
import com.example.CargoTracking.repository.DomesticShipmentRepository;
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
public class DomesticShipmentService {

    @Autowired
    DomesticShipmentRepository domesticShipmentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DomesticShipmentHistoryRepository domesticShipmentHistoryRepository;
    @Autowired
    EmailService emailService;

    public DomesticShipmentDto addShipment(DomesticShipmentDto domesticShipmentDto)  {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);

            DomesticShipment unSaveDomesticShipment = toEntity(domesticShipmentDto);
            unSaveDomesticShipment.setCreatedAt(LocalDate.now());
            unSaveDomesticShipment.setCreatedBy(user);
            DomesticShipment domesticShipment = domesticShipmentRepository.save(unSaveDomesticShipment);

            DomesticShipmentHistory domesticShipmentHistory = DomesticShipmentHistory.builder()
                    .status("Pre-Alert Created")
                    .processTime(LocalDateTime.now())
                    .locationCode(domesticShipmentDto.getOriginLocation())
                    .user(user.getId())
                    .domesticShipment(domesticShipment)
                    .remarks(domesticShipment.getRemarks())
                    .build();

            domesticShipmentHistoryRepository.save(domesticShipmentHistory);

            List<String> emails = userRepository.findEmailByLocation(domesticShipmentDto.getDestinationLocation());

            for (String to :emails) {
                emailService.sendEmail(to,"Shipment Notification");
            }

            return  toDto(domesticShipment);
        }

        throw new RuntimeException("Error creating Domestic shipment");
    }

    public List<DomesticShipmentDto> getAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return toDtoList(domesticShipmentRepository.findAll());
            }else{
                return toDtoList(domesticShipmentRepository.findAllByCreatedBy(user));

            }
        }
        return toDtoList(domesticShipmentRepository.findAll());

    }


    public DomesticShipmentDto getById(Long id) {
        Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);
        if(domesticShipment.isPresent()){
            return toDto(domesticShipment.get());
        }
        throw new RuntimeException(String.format("Domestic shipment Not Found By This Id %d",id));
    }

    public List<DomesticShipmentDto> getOutboundShipment(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return new ArrayList<>();
            }
            return toDtoList(domesticShipmentRepository.findByOriginLocation(user.getLocation().getLocationName()));
        }

        throw new RuntimeException("Shipment not found");
    }

    public List<DomesticShipmentDto> getInboundShipment() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user.getLocation() == null){
                return new ArrayList<>();
            }
            return toDtoList(domesticShipmentRepository.findByDestinationLocation(user.getLocation().getLocationName()));
        }

        throw new RuntimeException("Shipment not found");
    }
    public DomesticShipmentDto updateDomesticShipment(Long id, DomesticShipmentDto domesticShipmentDto) {
        Optional<DomesticShipment> domesticShipment = domesticShipmentRepository.findById(id);
        if(domesticShipment.isPresent()){
            domesticShipment.get().setUpdatedAt(LocalDate.now());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              if(principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userRepository.findByEmail(username);
                domesticShipment.get().setUpdatedBy(user);
            }
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
            domesticShipment.get().setStatus(domesticShipmentDto.getStatus());
            domesticShipment.get().setRemarks(domesticShipmentDto.getRemarks());
            domesticShipment.get().setAta(domesticShipmentDto.getAta());
            domesticShipment.get().setTotalShipments(domesticShipmentDto.getTotalShipments());
            domesticShipment.get().setOverages(domesticShipmentDto.getOverages());
            domesticShipment.get().setOveragesAwbs(domesticShipmentDto.getOveragesAwbs());
            domesticShipment.get().setReceived(domesticShipmentDto.getReceived());
            domesticShipment.get().setShortages(domesticShipmentDto.getShortages());
            domesticShipment.get().setShortagesAwbs(domesticShipmentDto.getShortagesAwbs());
            domesticShipment.get().setAttachments(domesticShipmentDto.getAttachments());

            DomesticShipment save = domesticShipmentRepository.save(domesticShipment.get());
            return toDto(save);
        }else{
            throw new RuntimeException("Domestic Shipment Not found");
        }
    }

    public List<DomesticShipmentDto> toDtoList(List<DomesticShipment> domesticShipmentList){
        return domesticShipmentList.stream().map(this::toDto).collect(Collectors.toList());
    }

    private DomesticShipmentDto toDto(DomesticShipment domesticShipment) {
        return modelMapper.map(domesticShipment , DomesticShipmentDto.class);
    }


    private DomesticShipment toEntity(DomesticShipmentDto domesticShipmentDto){
        return modelMapper.map(domesticShipmentDto , DomesticShipment.class);
    }

}
