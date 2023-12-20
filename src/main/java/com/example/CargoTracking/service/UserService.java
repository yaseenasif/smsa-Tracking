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

    public UserResponseDto addUser(UserDto userDto){
        User userByEmail = userRepository.findByEmail(userDto.getEmail());
        if(userByEmail == null){
            try {
                Set<Roles> rolesList = new HashSet<>();
                Location location = null;
                for (Roles roleList:userDto.getRoles()) {
                    Optional<Roles> roles = Optional.ofNullable(roleRepository
                            .findByName(roleList.getName())
                            .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

                    if(roleList.getName().equals("ROLE_ADMIN")){
                        location = null;
                    }else{
                        location = locationRepository.findByLocationName(userDto.getLocation())
                                .orElseThrow(()-> new RecordNotFoundException("Location is not in record"));
                    }
                    rolesList.add(roles.get());
                }





                User user = User.builder()
                        .name(userDto.getName())
                        .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                        .roles(rolesList)
                        .status(Boolean.TRUE)
                        .location(location)
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


    public UserResponseDto updateUser(Long id, UserDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){

            try {
                Set<Roles> rolesList = new HashSet<>();
                Location location = null;
                for (Roles roleList:userDto.getRoles()) {
                    Optional<Roles> roles = Optional.ofNullable(roleRepository
                            .findByName(roleList.getName())
                            .orElseThrow(() -> new RecordNotFoundException("Role is incorrect")));

                    if(roleList.getName().equals("ROLE_ADMIN")){
                        location = null;
                    }else{
                        location = locationRepository.findByLocationName(userDto.getLocation())
                                .orElseThrow(()-> new RecordNotFoundException("Location is not in record"));
                    }
                    rolesList.add(roles.get());
                }



                user.get().setName(userDto.getName());
                user.get().setEmail(userDto.getEmail());
                user.get().setPassword(userDto.getPassword() != null ? bCryptPasswordEncoder.encode(userDto.getPassword()) : user.get().getPassword());
                user.get().setRoles(rolesList);
                user.get().setStatus(Boolean.TRUE);
                user.get().setLocation(location);
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
        }
        throw new RuntimeException("User not found");
    }

    public UserResponseDto getUserById(long id) {
        Optional<User> user = userRepository.findActiveUserById(id);
        if(user.isPresent()){
            return toDtoForResponse(user.get());
        }
        throw new RecordNotFoundException(String.format("User Not Found On Id",id));
    }
}
