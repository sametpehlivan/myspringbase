package com.pehlivan.security.seeder;

import com.pehlivan.security.model.BanStatus;
import com.pehlivan.security.model.User;
import com.pehlivan.security.services.JpaUserDetailService;
import com.pehlivan.security.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Order(30)
@AllArgsConstructor
public class UserSeeder implements ApplicationRunner
{
    private final RoleService roleService;
    private final JpaUserDetailService userService;
    private final PasswordEncoder passwordEncoder;
    public static final String adminUserName = "admin";
    public static final String password = "123456";
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!userService.existsByUserName(adminUserName)){
            var role = roleService.findByName(RoleSeeder.adminRoleName);
            var user = new User();
            user.setUserName(adminUserName);
            user.setIsBanned(BanStatus.NO);
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
        }

    }
}
