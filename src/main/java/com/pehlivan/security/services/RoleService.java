package com.pehlivan.security.services;

import com.pehlivan.security.model.Role;
import com.pehlivan.security.repository.RoleRepository;
import com.pehlivan.security.services.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    public final RoleRepository roleRepository;
    public Role save(Role role){
        return roleRepository.save(role);
    }
    public boolean existByName(String roleName){
        return roleRepository.existsByName(roleName);
    }
    public Role findByName(String roleName) {
       return roleRepository.findByName(roleName).orElseThrow(()-> new NotFoundException("Role Not Found"));
    }
}
