package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.ResetPassword;
import com.example.CargoTracking.dto.UserDto;
import com.example.CargoTracking.dto.UserResponseDto;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.payload.ApiResponse;
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


    @PreAuthorize("hasAuthority('add-user')")
    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserDto userDto){

        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @PreAuthorize("hasAuthority('getAll-user')")
    @GetMapping("/all-user")
    public ResponseEntity<List<UserResponseDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PreAuthorize("hasAuthority('getInactive-user')")
    @GetMapping("/inactive-user")
    public ResponseEntity<List<UserResponseDto>> getInActiveUser(){
        return ResponseEntity.ok(userService.getInActiveUser());
    }

    @PreAuthorize("hasAuthority('getById-user')")
    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable long id){
        return  ResponseEntity.ok(userService.getUserById(id));
    }


    @PreAuthorize("hasAuthority('update-user')")
    @PutMapping("/edit-user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserDto user){
        return ResponseEntity.ok(userService.updateUser(id,user));
    }

    @PreAuthorize("hasAuthority('delete-user')")
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PreAuthorize("hasAuthority('getLoggedIn-user')")
    @GetMapping("/get-loggedin/user")
    public ResponseEntity<UserResponseDto> getLoggedInUser(){
        return ResponseEntity.ok(userService.getLoggedInUser());
    }

    @PreAuthorize("hasAuthority('resetPassword-user')")
    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPassword resetPassword){
        return ResponseEntity.ok(userService.resetPassword(resetPassword));
    }

}
