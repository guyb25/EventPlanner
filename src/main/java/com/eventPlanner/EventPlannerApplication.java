package com.eventPlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.eventPlanner.dataAccess")
public class EventPlannerApplication {
    public static void main(String[] args) {

        SpringApplication.run(EventPlannerApplication.class, args);
    }
}
