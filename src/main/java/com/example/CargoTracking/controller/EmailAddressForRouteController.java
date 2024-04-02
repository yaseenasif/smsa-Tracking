package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.EmailAddressForRoutesDto;
import com.example.CargoTracking.model.EmailAddressForRoutes;
import com.example.CargoTracking.service.EmailAddressForRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailAddressForRouteController {

  @Autowired
  private EmailAddressForRouteService emailAddressForRouteService;
  @PreAuthorize("hasAuthority('addEmail-routes')")
  @PostMapping("add/emails")
  public ResponseEntity<EmailAddressForRoutesDto> addEmail(@RequestBody EmailAddressForRoutesDto emailAddressForRoutesDto){
    return ResponseEntity.ok(emailAddressForRouteService.addEmail(emailAddressForRoutesDto));
  }

  @PreAuthorize("hasAuthority('updateEmail-routes')")
  @PutMapping("update/emails/{id}")
  public ResponseEntity<EmailAddressForRoutesDto> updateEmail(@PathVariable Long id,@RequestBody EmailAddressForRoutesDto emailAddressForRoutesDto){
    return ResponseEntity.ok(emailAddressForRouteService.updateEmail(id,emailAddressForRoutesDto));
  }

  @PreAuthorize("hasAuthority('getEmailByType-routes')")
  @GetMapping("get/emails/type/{type}")
  public ResponseEntity<EmailAddressForRoutesDto> getEmailsByType(@PathVariable(value = "type") String type){
    return ResponseEntity.ok(emailAddressForRouteService.getEmailByType(type));
  }

  @PreAuthorize("hasAuthority('getEmailById-routes')")
  @GetMapping("get/emails/{id}}")
  public ResponseEntity<EmailAddressForRoutesDto> getEmailsByType(@PathVariable(value = "id") Long id){
    return ResponseEntity.ok(emailAddressForRouteService.getEmailById(id));
  }
}
