package com.note.backend.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApiConfigs() {
        return new OpenAPI().info(
                new Info()
                        .title("Notes API")
                        .description("REST API for managing notes")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Aashish Moktan")
                                .email("aashishmoktan715@gmail.com")
                        )
        );
    }
}
