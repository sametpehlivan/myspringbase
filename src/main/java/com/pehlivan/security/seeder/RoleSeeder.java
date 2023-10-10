package com.pehlivan.security.seeder;

import com.pehlivan.security.model.Permission;
import com.pehlivan.security.model.Role;
import com.pehlivan.security.services.PermissionService;
import com.pehlivan.security.services.RoleService;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Order(20)
public class RoleSeeder implements ApplicationRunner {
    private final RoleService roleService;
    private final PermissionService permissionService;
    public final static String adminRoleName = "SUPERADMIN";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!roleService.existByName(adminRoleName)){
            var permissions = permissionService.findAll();
            var role  = new Role();
            role.setName(adminRoleName);
            role.setPermissions(new HashSet<>(permissions));
            roleService.save(role);
        }
    }
}
