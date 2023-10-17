package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DomesticShipmentDto;
import com.example.CargoTracking.model.DomesticShipment;
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

//        final String authorizationHeader = request.getHeader("Authorization");
//            String jwt = authorizationHeader.substring(7);
//            String username = jwtUtil.extractUsername(jwt);
//            User user = userRepository.findByName(username);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
                String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByName(username);


            domesticShipmentDto.setCreatedBy(username);
            domesticShipmentDto.setCreatedAt(LocalDate.now());
            DomesticShipment domesticShipment = domesticShipmentRepository.save(toEntity(domesticShipmentDto));

            DomesticShipmentHistory domesticShipmentHistory = DomesticShipmentHistory.builder()
                    .status("Pre-Alert Created")
                    .processTime(LocalDateTime.now())
                    .locationCode(domesticShipmentDto.getOrigin())
                    .user(user.getId())
                    .domesticShipment(domesticShipment)
                    .remarks(domesticShipment.getRemarks())
                    .build();

            domesticShipmentHistoryRepository.save(domesticShipmentHistory);

            List<String> emails = userRepository.findEmailByLocation(domesticShipmentDto.getDestination());

            for (String to :emails) {
                emailService.sendEmail(to,"Shipment Notification");
            }

            return  toDto(domesticShipment);
        }

        throw new RuntimeException("Error creating Domestic shipment");
    }

    public List<DomesticShipmentDto> getAll() {
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
            User user = userRepository.findByName(username);

            return toDtoList(domesticShipmentRepository.findByOriginAndCreatedBy(user.getLocation().getLocationName(),username));
        }

        throw new RuntimeException("Shipment not found");
    }

    public List<DomesticShipmentDto> getInboundShipment() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByName(username);

            return toDtoList(domesticShipmentRepository.findByDestinationAndCreatedBy(user.getLocation().getLocationName(),username));
        }

        throw new RuntimeException("Shipment not found");
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
