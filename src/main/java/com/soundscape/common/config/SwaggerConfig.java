package com.soundscape.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "로컬 서버"),
        },
        info = @Info(
                title = "SoundScape API",
                description = "SoundScape API",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
}
