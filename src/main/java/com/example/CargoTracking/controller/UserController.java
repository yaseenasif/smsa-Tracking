package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.UserDto;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto){

        User user = userService.addUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/edit-user/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
//        return ResponseEntity.ok(userService.updateUser(id,userDto));
//    }


}
