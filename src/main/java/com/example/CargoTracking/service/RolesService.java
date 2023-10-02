package com.example.CargoTracking.service;

import com.example.CargoTracking.dto.RolesDto;
import com.example.CargoTracking.model.Permission;
import com.example.CargoTracking.model.Roles;
import com.example.CargoTracking.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolesService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;

    public RolesDto addRoles(RolesDto rolesDto) {

        Optional<Roles> existingRole = roleRepository.findById(rolesDto.getId());
        Set<Permission> truePermissions = new HashSet<>();
        if (existingRole.isPresent() && rolesDto!= null) {
            for(Permission permission: rolesDto.getPermissions()){
                if(permission.isStatus()){
                    truePermissions.add(permission);
                }
            }
            existingRole.get().setPermissions(truePermissions);
        }

        return toDto(roleRepository.save(existingRole.get()));
    }

    public List<RolesDto> getAll() {
        return toDtoList(roleRepository.findAll());
    }

    public RolesDto getById(Long id) {
        Optional<Roles> roles = roleRepository.findById(id);
        if (roles.isPresent()){
            return toDto(roles.get());
        }
        throw new RuntimeException(String.format("Role Not Found On this Id => %d",id));
    }

    public void deleteRoleById(Long id){
        Optional<Roles> roles = roleRepository.findById(id);
        if(roles.isPresent()){
            roleRepository.deleteById(id);
        }
        throw new RuntimeException("Record doesn't exist");
    }

    public List<RolesDto> toDtoList(List<Roles> roles){
        return roles.stream().map(this::toDto).collect(Collectors.toList());
    }

    public RolesDto toDto(Roles roles){
        return modelMapper.map(roles, RolesDto.class);
    }
}
