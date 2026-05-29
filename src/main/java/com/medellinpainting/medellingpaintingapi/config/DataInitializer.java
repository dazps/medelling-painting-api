package com.medellinpainting.medellingpaintingapi.config;

import com.medellinpainting.medellingpaintingapi.entities.Role;
import com.medellinpainting.medellingpaintingapi.entities.User;
import com.medellinpainting.medellingpaintingapi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            admin.setRoles(new ArrayList<>());
            User savedAdmin = userRepository.save(admin);

            Role role = new Role();
            role.setRol("ROLE_ADMIN");
            role.setUser(savedAdmin);
            savedAdmin.getRoles().add(role);
            userRepository.save(savedAdmin);

            System.out.println(">>> Default admin user created: admin / admin123");
        }
    }
}
