package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.User1Dto;
import com.example.CargoTracking.dto.UserDto;
import com.example.CargoTracking.dto.UserResponseDto;
import com.example.CargoTracking.model.User1;
import com.example.CargoTracking.service.User1Service;
import com.example.CargoTracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/api")
public class User1Controller {

    @Autowired
    User1Service userService;
    @PostMapping("/user1")
    public ResponseEntity<User1Dto> addUser(@Valid @RequestBody User1Dto userDto){

        return ResponseEntity.ok(userService.addUser(userDto));
    }
}
