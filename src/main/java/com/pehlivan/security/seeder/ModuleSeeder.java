package com.pehlivan.security.seeder;

import com.google.common.reflect.ClassPath;
import com.pehlivan.security.model.ModuleEntity;
import com.pehlivan.security.services.ModuleEntityService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class ModuleSeeder{
    private final ModuleEntityService moduleEntityService;
    private final String PACKAGE_NAME = "controllers";
    private final List<String> ignoreModules = List.of("AUTH");
    public void run() throws Exception {
        var controllers = getControllers();
        var list =  controllers
                .map(this::createModuleEntity)
                .filter(it-> !moduleEntityService.existName(it.getModuleName()))
                .filter(it -> !ignoreModules.contains(it.getModuleName()))
                .toList();
        moduleEntityService.saveAll(list);
    }
    public  Stream<? extends Class<?>> getControllers() throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter((it) -> it.getPackageName().contains(PACKAGE_NAME))
                .map(ClassPath.ClassInfo::load)
                .filter((it) -> it.isAnnotationPresent(RestController.class) || it.isAnnotationPresent(Controller.class));

    }
    public  ModuleEntity createModuleEntity(Class<?> it){
        ModuleEntity moduleEntity = new ModuleEntity();
        moduleEntity.setModuleName(createModuleName(it));
        return moduleEntity;
    }
    public String createModuleName(Class<?> it){
        return  it
                .getSimpleName()
                .toUpperCase()
                .replace("CONTROLLER","");
    }
}
