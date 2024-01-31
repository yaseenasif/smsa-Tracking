package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DriverDto;
import com.example.CargoTracking.dto.UserDto;
import com.example.CargoTracking.dto.UserResponseDto;
import com.example.CargoTracking.exception.RecordNotFoundException;
import com.example.CargoTracking.model.Driver;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.Roles;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.repository.LocationRepository;
import com.example.CargoTracking.repository.RoleRepository;
import com.example.CargoTracking.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public User addUser(UserDto userDto){
        User userByEmail = userRepository.findByEmail(userDto.getEmail());
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

                for (Location location:userDto.getLocations()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));


                    locationList.add(location1.get());
                }

                Set<Location> domesticOriginLocations = new HashSet<>();

                for (Location location:userDto.getDomesticOriginLocations()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    domesticOriginLocations.add(location1.get());
                }

                Set<Location> domesticDestinationLocation = new HashSet<>();

                for (Location location:userDto.getDomesticDestinationLocations()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    domesticDestinationLocation.add(location1.get());
                }

                Set<Location> internationalAirOriginLocation = new HashSet<>();

                for (Location location:userDto.getInternationalAirOriginLocation()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    internationalAirOriginLocation.add(location1.get());
                }

                Set<Location> internationalAirDestinationLocation = new HashSet<>();

                for (Location location:userDto.getInternationalAirDestinationLocation()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    internationalAirDestinationLocation.add(location1.get());
                }

                Set<Location> internationalRoadOriginLocation = new HashSet<>();

                for (Location location:userDto.getInternationalRoadOriginLocation()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    internationalRoadOriginLocation.add(location1.get());
                }

                Set<Location> internationalRoadDestinationLocation = new HashSet<>();

                for (Location location:userDto.getInternationalRoadDestinationLocation()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    internationalRoadDestinationLocation.add(location1.get());
                }

                User user = User.builder()
                        .name(userDto.getName())
                        .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                        .roles(rolesList)
                        .status(Boolean.TRUE)
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
                return save;

            }catch(Exception e){
                throw new RecordNotFoundException("Some information is incorrect");
            }
        }else{
            throw new RecordNotFoundException("Email is Already exist");
        }



    }


    public List<User> getAllUser() {
        List<User> userWithTrueStatus = userRepository.findUserWithTrueStatus();
        return userWithTrueStatus;
    }


    public User updateUser(Long id, UserDto userDto) {
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
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    locationList.add(location1.get());
                }
                }

                Set<Location> domesticOriginLocations = new HashSet<>();
                if(userDto.getDomesticOriginLocations()!=null) {
                    for (Location location : userDto.getDomesticOriginLocations()) {
                        Optional<Location> location1 = Optional.ofNullable(locationRepository
                                .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                                .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                        domesticOriginLocations.add(location1.get());
                    }
                }

                Set<Location> domesticDestinationLocation = new HashSet<>();
                if(userDto.getInternationalAirOriginLocation()!=null){
                for (Location location:userDto.getDomesticDestinationLocations()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    domesticDestinationLocation.add(location1.get());
                }
                }

                Set<Location> internationalAirOriginLocation = new HashSet<>();

                if(userDto.getInternationalAirOriginLocation()!=null){
                    for (Location location:userDto.getInternationalAirOriginLocation()) {
                        Optional<Location> location1 = Optional.ofNullable(locationRepository
                                .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                                .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                        internationalAirOriginLocation.add(location1.get());
                    }
                }


                Set<Location> internationalAirDestinationLocation = new HashSet<>();
                if(userDto.getInternationalAirDestinationLocation()!=null){
                for (Location location:userDto.getInternationalAirDestinationLocation()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    internationalAirDestinationLocation.add(location1.get());
                }
                }

                Set<Location> internationalRoadOriginLocation = new HashSet<>();
                if(userDto.getInternationalRoadOriginLocation()!=null){
                for (Location location:userDto.getInternationalRoadOriginLocation()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    internationalRoadOriginLocation.add(location1.get());
                }
                }

                Set<Location> internationalRoadDestinationLocation = new HashSet<>();
                if(userDto.getInternationalRoadDestinationLocation()!=null){
                for (Location location:userDto.getInternationalRoadDestinationLocation()) {
                    Optional<Location> location1 = Optional.ofNullable(locationRepository
                            .findByLocationNameAndTypeAndFacilityForUser(location.getLocationName(), location.getType())
                            .orElseThrow(() -> new RecordNotFoundException("Location is incorrect")));

                    internationalRoadDestinationLocation.add(location1.get());
                }
                }

                user.get().setName(userDto.getName());
                user.get().setEmail(userDto.getEmail());
                user.get().setPassword(userDto.getPassword() != null ? bCryptPasswordEncoder.encode(userDto.getPassword()) : user.get().getPassword());
                user.get().setRoles(rolesList);
                user.get().setStatus(Boolean.TRUE);
                user.get().setLocations(locationList);
                user.get().setDomesticOriginLocations(domesticOriginLocations);
                user.get().setDomesticDestinationLocations(domesticDestinationLocation);
                user.get().setInternationalAirOriginLocation(internationalAirOriginLocation);
                user.get().setInternationalAirDestinationLocation(internationalAirDestinationLocation);
                user.get() .setInternationalRoadOriginLocation(internationalRoadOriginLocation);
                user.get() .setInternationalRoadDestinationLocation(internationalRoadDestinationLocation);
                User save = userRepository.save(user.get());

                return save;

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
        }
        throw new RuntimeException("User not found");
    }

    public User getUserById(long id) {
        Optional<User> user = userRepository.findActiveUserById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new RecordNotFoundException(String.format("User Not Found On Id",id));
    }

    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            if(user != null){
                return user;
            }
        }else{
            throw new RecordNotFoundException("User Not Found");
        }
        return null;
    }
}
