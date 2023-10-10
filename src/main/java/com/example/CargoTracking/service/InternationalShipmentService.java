package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.InternationalShipmentDto;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.model.ShipmentHistory;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.repository.InternationalShipmentRepository;
import com.example.CargoTracking.repository.ShipmentHistoryRepository;
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
public class InternationalShipmentService {
    @Autowired
    InternationalShipmentRepository internationalShipmentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShipmentHistoryRepository shipmentHistoryRepository;
    @Autowired
    EmailService emailService;


    public InternationalShipmentDto addShipment(InternationalShipmentDto internationalShipmentDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByName(username);

            internationalShipmentDto.setCreatedAt(LocalDate.now());
            internationalShipmentDto.setCreatedBy(username);

            InternationalShipment internationalShipment = internationalShipmentRepository
                    .save(toEntity(internationalShipmentDto));
            ShipmentHistory shipmentHistory = ShipmentHistory.builder()
                    .status("Pre-Alert Created")
                    .processTime(LocalDateTime.now())
                    .locationCode(internationalShipment.getOriginCountry())
                    .user(user.getId())
                    .domesticShipment(null)
                    .type("International")
                    .internationalShipment(internationalShipment)
                    .remarks(internationalShipment.getRemarks())
                    .build();

            shipmentHistoryRepository.save(shipmentHistory);

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
}
