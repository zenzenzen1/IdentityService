package org.example.identityservice.configuration;

import java.util.HashSet;

import org.example.identityservice.entity.Role;
import org.example.identityservice.entity.User;
import org.example.identityservice.repository.RoleRepository;
import org.example.identityservice.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findAll().isEmpty()) {
                var adminRole = roleRepository.save(Role.builder()
                        .name(org.example.identityservice.enums.Role.ADMIN.name())
                        .description("Admin role")
                        .build());
                var userRole = roleRepository.save(Role.builder()
                        .name(org.example.identityservice.enums.Role.USER.name())
                        .description("User role")
                        .build());

                if (userRepository.findByUsername("admin").isEmpty()) {
                    var roles = new HashSet<Role>();
                    roles.add(adminRole);
                    roles.add(userRole);
                    User user = User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .roles(roles)
                            .build();
                    userRepository.save(user);
                    log.warn("Admin user has been created with default password: admin");
                }
                if (userRepository.findByUsername("user1").isEmpty()) {
                    var roles = new HashSet<Role>();
                    roles.add(userRole);
                    User user = User.builder()
                            .username("user1")
                            .password(passwordEncoder.encode("123"))
                            .roles(roles)
                            .build();
                    userRepository.save(user);
                }
            }
        };
    }
    ;
}
