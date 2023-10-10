package com.pehlivan.security.repository;

import com.pehlivan.security.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    boolean existsByCode(String code);
    Optional<Permission> findByCode(String  code);
}
