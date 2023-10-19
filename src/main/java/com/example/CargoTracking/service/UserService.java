package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.DriverDto;
import com.example.CargoTracking.dto.UserDto;
import com.example.CargoTracking.model.Driver;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.Roles;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.repository.LocationRepository;
import com.example.CargoTracking.repository.RoleRepository;
import com.example.CargoTracking.repository.UserRepository;
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

    public UserDto addUser(UserDto userDto){
        User userByEmail = userRepository.findByEmail(userDto.getEmail());
        if(userByEmail == null){
            try {
                Optional<Roles> roles = Optional.ofNullable(roleRepository
                        .findByName(userDto.getRole())
                        .orElseThrow(() -> new RuntimeException("Role is incorrect")));
                Location location;
                if(userDto.getRole().equals("ROLE_ADMIN")){
                    location = null;
                }else{
                    location = locationRepository.findByLocationName(userDto.getLocation())
                            .orElseThrow(()-> new RuntimeException("Location is not in record"));
                }


                Set<Roles> rolesList = new HashSet<>();
                rolesList.add(roles.get());


                User user = User.builder()
                        .name(userDto.getName())
                        .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                        .roles(rolesList)
                        .status(Boolean.TRUE)
                        .location(location)
                        .email(userDto.getEmail())
                        .build();
                User save = userRepository.save(user);
                return toDto(save);

            }catch(Exception e){
                throw new RuntimeException("Some information is incorrect");
            }
        }else{
            throw new RuntimeException("Email is Already exist");
        }



    }


    public List<UserDto> getAllUser() {
        List<User> userWithTrueStatus = userRepository.findUserWithTrueStatus();
        return toDtoList(userWithTrueStatus);
    }


    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){

            try {
                Optional<Roles> roles = Optional.ofNullable(roleRepository
                        .findByName(userDto.getRole())
                        .orElseThrow(() -> new RuntimeException("Role is incorrect")));
                Location location;
                if(userDto.getRole().equals("ROLE_ADMIN")){
                    location = null;
                }else{
                    location = locationRepository.findByLocationName(userDto.getLocation())
                            .orElseThrow(()-> new RuntimeException("Location is not in record"));
                }


                Set<Roles> rolesList = new HashSet<>();
                rolesList.add(roles.get());

                User user1 = User.builder()
                        .name(userDto.getName())
                        .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                        .roles(rolesList)
                        .status(Boolean.TRUE)
                        .location(location)
                        .email(userDto.getEmail())
                        .build();
                User save = userRepository.save(user1);
                return toDto(save);

            }catch(Exception e){
                throw new RuntimeException("Some information is incorrect");
            }
        }
        throw new RuntimeException("User Not Found");
    }

    public List<UserDto> toDtoList(List<User> user){
        return user.stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserDto toDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setStatus(Boolean.FALSE);
            User save = userRepository.save(user.get());
            return toDto(save);
        }
        throw new RuntimeException("User not found");
    }
}
