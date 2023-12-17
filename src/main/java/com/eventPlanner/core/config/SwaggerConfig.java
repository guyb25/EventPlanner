package com.eventPlanner.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI eventPlannerApi() {
        return new OpenAPI()
                .info(new Info().title("EventPlanner API")
                        .description("API for planning events and sending event notifications")
                        .version("v1.0.0"));
    }
}
