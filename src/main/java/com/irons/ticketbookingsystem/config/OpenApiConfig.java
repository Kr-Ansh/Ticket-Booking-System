package com.irons.ticketbookingsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ticket Booking Architecture Engine API")
                        .version("1.0.0")
                        .description("Production-grade localized backend engine managing seat allocation and transactional consistency. Built with Spring Boot, JPA, and H2.")
                );
    }
}
