package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.ShipmentDto;
import com.example.CargoTracking.model.Shipment;
import com.example.CargoTracking.model.ShipmentHistory;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.repository.ShipmentHistoryRepository;
import com.example.CargoTracking.repository.ShipmentRepository;
import com.example.CargoTracking.repository.UserRepository;
import com.example.CargoTracking.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    JwtUtil jwtUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShipmentHistoryRepository shipmentHistoryRepository;

    public ShipmentDto addShipment(ShipmentDto shipmentDto, HttpServletRequest request){
        Shipment shipment = shipmentRepository.save(toEntity(shipmentDto));

        final String authorizationHeader = request.getHeader("Authorization");
            String jwt = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            User user = userRepository.findByName(username);

        ShipmentHistory shipmentHistory = ShipmentHistory.builder()
                .status(shipmentDto.getStatus())
                .processTime(LocalDateTime.now())
                .locationCode(shipmentDto.getOriginCountry())
                .user(user.getId())
                .shipment(shipment)
                .remarks(shipment.getRemarks())
                .build();

        shipmentHistoryRepository.save(shipmentHistory);

        return  toDto(shipment);
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
