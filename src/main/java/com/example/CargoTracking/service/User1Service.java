package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.User1Dto;
import com.example.CargoTracking.dto.UserDto;
import com.example.CargoTracking.dto.UserResponseDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.Roles;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.model.User1;
import com.example.CargoTracking.repository.LocationRepository;
import com.example.CargoTracking.repository.RoleRepository;
import com.example.CargoTracking.repository.User1Repository;
import com.example.CargoTracking.repository.UserRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class User1Service {
    @Autowired
    User1Repository user1Repository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    ModelMapper modelMapper;


    public User1Dto addUser(User1Dto userDto) {
        User1 userByEmail = user1Repository.findByEmail(userDto.getEmail());
        if(userByEmail == null){
            try {
                Set<Roles> rolesList = new HashSet<>();

                for (Roles roleList:userDto.getRoles()) {
                    Optional<Roles> roles = Optional.ofNullable(roleRepository
                            .findByName(roleList.getName())
                            .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

                    rolesList.add(roles.get());
                }

                Set<Location> locationList = new HashSet<>();

                for (Location location:userDto.getLocation()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationName(location.getLocationName())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    locationList.add(location1.get());
                }





                User1 user = User1.builder()
                        .name(userDto.getName())
                        .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                        .roles(rolesList)
                        .status(Boolean.TRUE)
                        .locations(locationList)
                        .email(userDto.getEmail())
                        .build();
                User1 save = user1Repository.save(user);
                return toDtoForResponse(save);

            }catch(Exception e){
                throw new RecordNotFoundException("Some information is incorrect");
            }
        }else{
            throw new RecordNotFoundException("Email is Already exist");
        }
    }

    public List<User1Dto> toDtoListForResponse(List<User1> user){
        return user.stream().map(this::toDtoForResponse).collect(Collectors.toList());
    }

    public User1Dto toDto(User1 user){
        return modelMapper.map(user, User1Dto.class);
    }

    public User1Dto toDtoForResponse(User1 user){
        return modelMapper.map(user, User1Dto.class);
    }
}
