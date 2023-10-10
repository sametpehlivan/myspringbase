package com.pehlivan.security.seeder;

import com.google.common.reflect.ClassPath;
import com.pehlivan.security.model.ModuleEntity;
import com.pehlivan.security.model.Permission;
import com.pehlivan.security.services.ModuleEntityService;
import com.pehlivan.security.services.PermissionService;
import jdk.dynalink.beans.StaticClass;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
@Order(10)
public class PermissionSeeder implements ApplicationRunner {
    private final PermissionService permissionService;
    private final ModuleEntityService moduleEntityService;
    private final ModuleSeeder moduleSeeder;
    public static final String[] ACTIONS = {
            "READ", "CREATE", "UPDATE","DELETE","EXECUTE"
    };
    @Override
    public void run(ApplicationArguments args) throws Exception {
        moduleSeeder.run();
        List<ModuleEntity> moduleEntityList = moduleEntityService.findAll();
        moduleEntityList.forEach(module ->{
            Arrays.stream(ACTIONS)
                    .map( act -> module.getModuleName().trim() + ":" + act  )
                    .filter(permCode -> !permissionService.existByCode(permCode))
                    .forEach(permCode -> {
                        Permission perm = new Permission();
                        perm.setCode(permCode);
                        perm.setPermissionName(permCode);
                        perm.setModuleEntity(module);
                        module.getPermissions().add(perm);
                    });

        });
        moduleEntityService.saveAll(moduleEntityList);
    }
}
