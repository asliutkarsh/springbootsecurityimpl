package com.asliutkarsh.springbootsecurityimpl;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.asliutkarsh.springbootsecurityimpl.v1.dto.SignupRequest;
import com.asliutkarsh.springbootsecurityimpl.v1.service.AuthService;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootSecurityImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurityImplApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthService authService) {
        return args -> {
            try {
                var superAdmin = SignupRequest.builder()
                        .username("superadmin")
                        .email("superadmin@mail.com")
                        .password("admin")
                        .role("SUPER_ADMIN")
                        .build();

                System.out.println("Admin created: " + authService.register(superAdmin));
            } catch (Exception e) {
                System.out.println("Admin already exists");
            }
        };
    }
}
