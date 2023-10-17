package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.UserDto;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.Roles;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.repository.LocationRepository;
import com.example.CargoTracking.repository.RoleRepository;
import com.example.CargoTracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public User addUser(UserDto userDto){

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
            return  userRepository.save(user);

        }catch(Exception e){
            throw new RuntimeException("Some information is incorrect");
            }

    }


    public List<User> getAllUser() {
        return userRepository.findUserWithTrueStatus();
    }


//    public User updateUser(Long id, UserDto userDto) {
//        Optional<User> user = userRepository.findById(id);
//        if(user.isPresent()){
//
//        }
//    }
}
