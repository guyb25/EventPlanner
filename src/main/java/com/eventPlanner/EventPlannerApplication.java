package com.eventPlanner;

import com.eventPlanner.notifications.EventNotifierBootstrapper;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.eventPlanner.dataAccess")
public class EventPlannerApplication {
    private final EventNotifierBootstrapper eventNotifierBootstrapper;

    public EventPlannerApplication(EventNotifierBootstrapper eventNotifierBootstrapper) {
        this.eventNotifierBootstrapper = eventNotifierBootstrapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(EventPlannerApplication.class, args);

    }

    @PostConstruct
    public void init() {
        eventNotifierBootstrapper.bootstrap();
    }
}
