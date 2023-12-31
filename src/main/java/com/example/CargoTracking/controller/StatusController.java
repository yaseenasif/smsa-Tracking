package com.example.CargoTracking.controller;

import com.example.CargoTracking.model.Status;
import com.example.CargoTracking.payload.ApiResponse;
import com.example.CargoTracking.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatusController {
        @Autowired
        StatusService statusService;

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @PostMapping("/status")
        public ResponseEntity<Status> addStatus(@RequestBody Status status){
            return ResponseEntity.ok(statusService.addStatus(status));
        }

        @GetMapping("/status")
        public ResponseEntity<List<Status>> getAll(){
            return ResponseEntity.ok(statusService.getAll());
        }

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @GetMapping("/status/{id}")
        public ResponseEntity<Status> getById(@PathVariable Long id){
            return ResponseEntity.ok(statusService.getById(id));
        }

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @DeleteMapping("/status/{id}")
        public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
                return ResponseEntity.ok(statusService.deleteById(id));
        }

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @PutMapping("/status/{id}")
        public ResponseEntity<Status> updateStatus(@PathVariable Long id,@RequestBody Status status){
                return ResponseEntity.ok(statusService.updateStatus(id,status));
        }

}
