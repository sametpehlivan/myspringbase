package com.pehlivan.security.services;

import com.pehlivan.security.model.ModuleEntity;
import com.pehlivan.security.repository.ModuleEntityRepository;
import com.pehlivan.security.services.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModuleEntityService {
    private final ModuleEntityRepository moduleEntityRepository;
    public boolean existName(String name){
        return moduleEntityRepository.existsByModuleName(name);
    }
    public List<ModuleEntity> saveAll(List<ModuleEntity> moduleEntities)  {
        return moduleEntityRepository.saveAll(moduleEntities);
    }
    public ModuleEntity findByName(String name){
        return moduleEntityRepository.findByModuleName(name).orElseThrow(() -> new NotFoundException("Not Found Module"));
    }
    public List<ModuleEntity> findAll(){
        return moduleEntityRepository.findAll();
    }
}
