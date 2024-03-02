package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.EmailAddressForRoutesDto;
import com.example.CargoTracking.model.EmailAddressForRoutes;
import com.example.CargoTracking.service.EmailAddressForRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailAddressForRouteController {

  @Autowired
  private EmailAddressForRouteService emailAddressForRouteService;
  @PostMapping("add/emails")
  public ResponseEntity<EmailAddressForRoutesDto> addEmail(@RequestBody EmailAddressForRoutesDto emailAddressForRoutesDto){
    return ResponseEntity.ok(emailAddressForRouteService.addEmail(emailAddressForRoutesDto));
  }

  @PutMapping("update/emails/{id}")
  public ResponseEntity<EmailAddressForRoutesDto> updateEmail(@PathVariable Long id,@RequestBody EmailAddressForRoutesDto emailAddressForRoutesDto){
    return ResponseEntity.ok(emailAddressForRouteService.updateEmail(id,emailAddressForRoutesDto));
  }

  @GetMapping("get/emails/type/{type}")
  public ResponseEntity<EmailAddressForRoutesDto> getEmailsByType(@PathVariable(value = "type") String type){
    return ResponseEntity.ok(emailAddressForRouteService.getEmailByType(type));
  }
}
