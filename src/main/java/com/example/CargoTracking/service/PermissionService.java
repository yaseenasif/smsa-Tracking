package com.example.CargoTracking.service;

import com.example.CargoTracking.model.Permission;
import com.example.CargoTracking.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission addPermission(Permission permission) {

        Permission permission1 = Permission.builder()
                .name(permission.getName())
                .status(Boolean.TRUE)
                .build();

        return permissionRepository.save(permission1);
    }

    public List<Permission> getActivePermissions() {
        return permissionRepository.getActivePermissions();
    }

    public Permission findPermissionById(Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if(permission.isPresent()){
            return permission.get();
        }

        throw new RuntimeException(String.format("Permission Not Found by this Id => %d" , id));
    }

    public Permission updatePermission(Long id,Permission permission) {
        Optional<Permission> permissionExist = permissionRepository.findById(id);
        if(permissionExist.isPresent()){
            permissionExist.get().setName(permission.getName());
            return permissionRepository.save(permissionExist.get());
        }

        throw new RuntimeException(String.format("Permission Not Found by this Id => %d" , id));
    }

    public Permission deleteById(Long id){
        Optional<Permission> permission = permissionRepository.findById(id);
        if (permission.isPresent()){
            permission.get().setStatus(Boolean.FALSE);
            return permissionRepository.save(permission.get());
        }
        throw new RuntimeException(String.format("Permission Not Found by this Id => %d" , id));
    }

    public Permission makePermissionActive(Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if(permission.isPresent()){
            if(permission.get().isStatus()){
                throw new RuntimeException("Record is already Active");
            }
            permission.get().setStatus(Boolean.TRUE);
            return permissionRepository.save(permission.get());
        }
        throw new RuntimeException(String.format("Permission Not Found by this Id => %d" , id));
    }


}
