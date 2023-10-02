package com.example.CargoTracking.controller;

import com.example.CargoTracking.model.Permission;
import com.example.CargoTracking.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/permission")
    public Permission addPermission(@RequestBody Permission permission){
        return permissionService.addPermission(permission);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/permission")
    public ResponseEntity<List<Permission>> getActivePermissions(){
        return ResponseEntity.ok(permissionService.getActivePermissions());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/permission/{id}")
    public ResponseEntity<Permission> findPermissionById(@PathVariable Long id){
        return ResponseEntity.ok(permissionService.findPermissionById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/permission/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id,@RequestBody Permission permission){
        return ResponseEntity.ok(permissionService.updatePermission(id,permission));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/permission/{id}")
    public ResponseEntity<Permission> deletePermissionById(@PathVariable Long id){
        return ResponseEntity.ok(permissionService.deleteById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/permission/active/{id}")
    public ResponseEntity<Permission> makePermissionActive(@PathVariable Long id){
        return ResponseEntity.ok(permissionService.makePermissionActive(id));
    }


}
