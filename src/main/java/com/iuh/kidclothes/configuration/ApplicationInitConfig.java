package com.iuh.kidclothes.configuration;

import com.iuh.kidclothes.enums.Role;
import com.iuh.kidclothes.repository.UserRepository;
import com.iuh.kidclothes.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(!userRepository.existsByEmail("admin@gmail.com")){
                User user= User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("12345678"))
                        .role(Role.STAFF)
                        .fullName("admin")
                        .build();
                log.warn("ADMIN account create Succesfully with default password:12345678, Please change password for the security");
            }
        };
    }
}
