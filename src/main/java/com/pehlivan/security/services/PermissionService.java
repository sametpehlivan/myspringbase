package com.pehlivan.security.services;

import com.pehlivan.security.model.Permission;
import com.pehlivan.security.repository.PermissionRepository;
import com.pehlivan.security.services.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    public boolean existByCode(String code){
        return permissionRepository.existsByCode(code);
    }
    public Permission findByCode(String code){
        return permissionRepository.findByCode(code)
                .orElseThrow(()-> new NotFoundException("Not Found Permission"));
    }
    public List<Permission> findAll(){
        return  permissionRepository.findAll();
    }
}
