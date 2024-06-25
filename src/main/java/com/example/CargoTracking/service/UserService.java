package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.*;
import com.example.CargoTracking.exception.RecordAlreadyExist;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.Roles;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.repository.LocationRepository;
import com.example.CargoTracking.repository.RoleRepository;
import com.example.CargoTracking.repository.UserRepository;
import com.example.CargoTracking.specification.UserSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    ModelMapper modelMapper;



    public UserResponseDto addUser(UserDto userDto){
        User userByEmail = userRepository.findByEmployeeId(userDto.getEmail());
        if(userByEmail == null){
            if (userRepository.findByEmployeeId(userDto.getEmployeeId())!= null) {
                throw new RecordAlreadyExist("Employee Id is Already exist");
            }

            try {
                Set<Roles> rolesList = new HashSet<>();

                for (Roles roleList:userDto.getRoles()) {
                    Optional<Roles> roles = Optional.ofNullable(roleRepository
                            .findByName(roleList.getName())
                            .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

                    rolesList.add(roles.get());
                }

                Set<Location> locationList = new HashSet<>();

                for (Location location:userDto.getLocations()) {
                    Optional<Location> location1 =locationRepository.findById(location.getId());


                    locationList.add(location1.get());
                }

                Set<Location> domesticOriginLocations = new HashSet<>();

                for (Location location:userDto.getDomesticOriginLocations()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    domesticOriginLocations.add(location1.get());
                }

                Set<Location> domesticDestinationLocation = new HashSet<>();

                for (Location location:userDto.getDomesticDestinationLocations()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    domesticDestinationLocation.add(location1.get());
                }

                Set<Location> internationalAirOriginLocation = new HashSet<>();

                for (Location location:userDto.getInternationalAirOriginLocation()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    internationalAirOriginLocation.add(location1.get());
                }

                Set<Location> internationalAirDestinationLocation = new HashSet<>();

                for (Location location:userDto.getInternationalAirDestinationLocation()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    internationalAirDestinationLocation.add(location1.get());
                }

                Set<Location> internationalRoadOriginLocation = new HashSet<>();

                for (Location location:userDto.getInternationalRoadOriginLocation()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    internationalRoadOriginLocation.add(location1.get());
                }

                Set<Location> internationalRoadDestinationLocation = new HashSet<>();

                for (Location location:userDto.getInternationalRoadDestinationLocation()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    internationalRoadDestinationLocation.add(location1.get());
                }

                User user = User.builder()
                        .name(userDto.getName())
                        .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                        .roles(rolesList)
                        .status(Boolean.TRUE)
                        .employeeId(userDto.getEmployeeId())
                        .locations(locationList)
                        .domesticOriginLocations(domesticOriginLocations)
                        .domesticDestinationLocations(domesticDestinationLocation)
                        .internationalAirOriginLocation(internationalAirOriginLocation)
                        .internationalAirDestinationLocation(internationalAirDestinationLocation)
                        .internationalRoadOriginLocation(internationalRoadOriginLocation)
                        .internationalRoadDestinationLocation(internationalRoadDestinationLocation)
                        .email(userDto.getEmail())
                        .build();
                User save = userRepository.save(user);
                return toDtoForResponse(save);

            }catch(Exception e){
                throw new RecordNotFoundException("Some information is incorrect");
            }
        }else{
            throw new RecordNotFoundException("Email is Already exist");
        }
    }


    public List<UserResponseDto> getAllUser() {
        List<User> userWithTrueStatus = userRepository.findUserWithTrueStatus();
        return toDtoListForResponse(userWithTrueStatus);
    }

    public Page<UserResponseDto> getUsersByLocations(SearchCriteriaForUser searchCriteriaForUser, Pageable pageable) {

        Specification<User> userSpecification= UserSpecification.findUser(searchCriteriaForUser);
       Page<User> usersPage = userRepository.findAll(userSpecification, pageable);
        List<UserResponseDto> userDtos= usersPage.stream().map(this::mapToDto).collect(Collectors.toList());
        return new PageImpl<>(userDtos, pageable, usersPage.getTotalElements());
    }

    private UserResponseDto mapToDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    public List<UserResponseDto> getInActiveUser(){
        List<User> userWithFalseStatus = userRepository.findUserWithFalseStatus();
        return toDtoListForResponse(userWithFalseStatus);
    }


    public UserResponseDto updateUser(Long id, UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){

            try {
                Set<Roles> rolesList = new HashSet<>();

                for (Roles roleList:userDto.getRoles()) {
                    Optional<Roles> roles = Optional.ofNullable(roleRepository
                            .findByName(roleList.getName())
                            .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

                    rolesList.add(roles.get());
                }

                Set<Location> locationList = new HashSet<>();
                if(userDto.getLocations()!=null) {
                for (Location location:userDto.getLocations()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    locationList.add(location1.get());
                }
                }

                Set<Location> domesticOriginLocations = new HashSet<>();
                if(userDto.getDomesticOriginLocations()!=null) {
                    for (Location location : userDto.getDomesticOriginLocations()) {
                        Optional<Location> location1 = locationRepository.findById(location.getId());

                        domesticOriginLocations.add(location1.get());
                    }
                }

                Set<Location> domesticDestinationLocation = new HashSet<>();
                if(userDto.getInternationalAirOriginLocation()!=null){
                for (Location location:userDto.getDomesticDestinationLocations()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    domesticDestinationLocation.add(location1.get());
                }
                }

                Set<Location> internationalAirOriginLocation = new HashSet<>();

                if(userDto.getInternationalAirOriginLocation()!=null){
                    for (Location location:userDto.getInternationalAirOriginLocation()) {
                        Optional<Location> location1 = locationRepository.findById(location.getId());

                        internationalAirOriginLocation.add(location1.get());
                    }
                }


                Set<Location> internationalAirDestinationLocation = new HashSet<>();
                if(userDto.getInternationalAirDestinationLocation()!=null){
                for (Location location:userDto.getInternationalAirDestinationLocation()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    internationalAirDestinationLocation.add(location1.get());
                }
                }

                Set<Location> internationalRoadOriginLocation = new HashSet<>();
                if(userDto.getInternationalRoadOriginLocation()!=null){
                for (Location location:userDto.getInternationalRoadOriginLocation()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    internationalRoadOriginLocation.add(location1.get());
                }
                }

                Set<Location> internationalRoadDestinationLocation = new HashSet<>();
                if(userDto.getInternationalRoadDestinationLocation()!=null){
                for (Location location:userDto.getInternationalRoadDestinationLocation()) {
                    Optional<Location> location1 = locationRepository.findById(location.getId());

                    internationalRoadDestinationLocation.add(location1.get());
                }
                }

                user.get().setName(userDto.getName());
                user.get().setEmail(userDto.getEmail());
                user.get().setPassword(userDto.getPassword() != null ? bCryptPasswordEncoder.encode(userDto.getPassword()) : user.get().getPassword());
                user.get().setRoles(rolesList);
                user.get().setEmployeeId(userDto.getEmployeeId());
                user.get().setStatus(Boolean.TRUE);
                user.get().setLocations(locationList);
                user.get().setDomesticOriginLocations(domesticOriginLocations);
                user.get().setDomesticDestinationLocations(domesticDestinationLocation);
                user.get().setInternationalAirOriginLocation(internationalAirOriginLocation);
                user.get().setInternationalAirDestinationLocation(internationalAirDestinationLocation);
                user.get() .setInternationalRoadOriginLocation(internationalRoadOriginLocation);
                user.get() .setInternationalRoadDestinationLocation(internationalRoadDestinationLocation);
                User save = userRepository.save(user.get());

                return toDtoForResponse(save);

            }catch(Exception e){
                throw new RecordNotFoundException("Some information is incorrect");
            }
        }
        throw new RecordNotFoundException("User Not Found");
    }

    public List<UserDto> toDtoList(List<User> user){
        return user.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<UserResponseDto> toDtoListForResponse(List<User> user){
        return user.stream().map(this::toDtoForResponse).collect(Collectors.toList());
    }

    public UserDto toDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    public UserResponseDto toDtoForResponse(User user){
        return modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setStatus(Boolean.FALSE);
            User save = userRepository.save(user.get());
            return toDtoForResponse(save);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    public UserResponseDto getUserById(long id) {
        Optional<User> user = userRepository.findActiveUserById(id);
        if(user.isPresent()){
            return toDtoForResponse(user.get());
        }
        throw new RecordNotFoundException(String.format("User Not Found On Id",id));
    }

    public UserResponseDto getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmployeeId(username);
            if(user != null){
                return toDtoForResponse(user);
            }
        }else{
            throw new RecordNotFoundException("User Not Found");
        }
        return null;
    }

    public ApiResponse resetPassword(ResetPassword resetPassword) {
        Optional<User> activeUserById = userRepository.findActiveUserById(resetPassword.getId());
        if(activeUserById.isPresent()) {
            User user = activeUserById.get();
            if (bCryptPasswordEncoder.matches(resetPassword.getOldPassword(), user.getPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(resetPassword.getNewPassword()));
                User save = userRepository.save(user);
                return ApiResponse.builder()
                        .message("Password updated successfully")
                        .statusCode(HttpStatus.OK.value())
                        .result(Collections.emptyList())
                        .build();
            } else {
                throw new RecordNotFoundException("Incorrect old password");
            }
        } else {
            throw new RecordNotFoundException("User Not Found");
        }
    }
}
