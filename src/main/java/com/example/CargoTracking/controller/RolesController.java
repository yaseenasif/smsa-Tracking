package com.example.CargoTracking.controller;

import com.example.CargoTracking.dto.RolesDto;
import com.example.CargoTracking.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolesController {

    @Autowired
    RolesService rolesService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<RolesDto> addRoles(@RequestBody RolesDto rolesDto){
        return ResponseEntity.ok(rolesService.addRoles(rolesDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<List<RolesDto>> getAll(){
        return ResponseEntity.ok(rolesService.getAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/roles/{id}")
    public ResponseEntity<RolesDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(rolesService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable Long id){
        rolesService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully");
    }

}
