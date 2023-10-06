package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.ShipmentDto;
import com.example.CargoTracking.model.Shipment;
import com.example.CargoTracking.model.ShipmentHistory;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.repository.ShipmentHistoryRepository;
import com.example.CargoTracking.repository.ShipmentRepository;
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
public class ShipmentService {

    @Autowired
    ShipmentRepository shipmentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShipmentHistoryRepository shipmentHistoryRepository;
    @Autowired
    EmailService emailService;

    public ShipmentDto addShipment(ShipmentDto shipmentDto)  {

//        final String authorizationHeader = request.getHeader("Authorization");
//            String jwt = authorizationHeader.substring(7);
//            String username = jwtUtil.extractUsername(jwt);
//            User user = userRepository.findByName(username);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByName(username);


            shipmentDto.setCreatedBy(username);
            shipmentDto.setCreatedAt(LocalDate.now());
            Shipment shipment = shipmentRepository.save(toEntity(shipmentDto));

            ShipmentHistory shipmentHistory = ShipmentHistory.builder()
                    .status("Pre-Alert Created")
                    .processTime(LocalDateTime.now())
                    .locationCode(shipmentDto.getOriginCountry())
                    .user(user.getId())
                    .shipment(shipment)
                    .remarks(shipment.getRemarks())
                    .build();

            shipmentHistoryRepository.save(shipmentHistory);

            List<String> emails = userRepository.findEmailByLocation(shipmentDto.getDestinationCountry());

            for (String to :emails) {
                emailService.sendEmail(to,"Shipment Notification");
            }

            return  toDto(shipment);
        }

        throw new RuntimeException("Error creating shipment");
    }

    public List<ShipmentDto> getAll() {
        return toDtoList(shipmentRepository.findAll());
    }

    public ShipmentDto getById(Long id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        if(shipment.isPresent()){
            return toDto(shipment.get());
        }
        throw new RuntimeException(String.format("Shipment Not Found By This Id %d",id));
    }

    public List<ShipmentDto> getOutboundShipment(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByName(username);

            return toDtoList(shipmentRepository.findByOriginCountryAndCreatedBy(user.getLocation().getLocationName(),username));
        }

        throw new RuntimeException("Shipment not found");
    }

    public List<ShipmentDto> getInboundShipment() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByName(username);

            return toDtoList(shipmentRepository.findByDestinationCountryAndCreatedBy(user.getLocation().getLocationName(),username));
        }

        throw new RuntimeException("Shipment not found");
    }

    public List<ShipmentDto> toDtoList(List<Shipment> shipment){
        return shipment.stream().map(this::toDto).collect(Collectors.toList());
    }

    private ShipmentDto toDto(Shipment shipment) {
        return modelMapper.map(shipment , ShipmentDto.class);
    }


    private Shipment toEntity(ShipmentDto shipmentDto){
        return modelMapper.map(shipmentDto , Shipment.class);
    }

}
