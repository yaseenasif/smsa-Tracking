package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.UserDto;
import com.example.CargoTracking.dto.UserResponseDto;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/user")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDto userDto){

        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/inactive-user")
    public ResponseEntity<List<User>> getInActiveUser(){
        return ResponseEntity.ok(userService.getInActiveUser());
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        return  ResponseEntity.ok(userService.getUserById(id));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto user){
        return ResponseEntity.ok(userService.updateUser(id,user));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/get-loggedin/user")
    public ResponseEntity<User> getLoggedInUser(){
        return ResponseEntity.ok(userService.getLoggedInUser());
    }


}
