package com.asliutkarsh.springbootsecurityimpl.v1.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Spring Boot Security Implementation",
        version = "1.0",
        description = "Spring Boot Security Implementation"
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local Server"
        )
    },
    security = {
        @SecurityRequirement(name = "bearerAuth")
    }

)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
    
}
