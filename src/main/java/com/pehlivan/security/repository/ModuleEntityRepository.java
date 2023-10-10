package com.pehlivan.security.repository;

import com.pehlivan.security.model.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleEntityRepository extends JpaRepository<ModuleEntity,Long> {
    boolean existsByModuleName(String name);
    Optional<ModuleEntity> findByModuleName(String name);
}
