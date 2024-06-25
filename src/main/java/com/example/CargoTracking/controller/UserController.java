package com.example.CargoTracking.controller;

import com.example.CargoTracking.criteria.SearchCriteriaForDomesticShipment;
import com.example.CargoTracking.dto.*;
import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.User;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/filter-user")
    public ResponseEntity<Page<UserResponseDto>> getFilterUsers(@RequestParam(defaultValue = "value",required = false) String value,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {

        SearchCriteriaForUser searchCriteriaForUser =  new ObjectMapper().readValue(value, SearchCriteriaForUser.class);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getUsersByLocations(searchCriteriaForUser,pageable));
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
