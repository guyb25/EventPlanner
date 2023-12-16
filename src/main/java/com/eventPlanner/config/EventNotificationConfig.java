package com.eventPlanner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventNotificationConfig {
    @Value("${spring.data.notificationTimeBeforeEvent}")
    private int notificationTimeBeforeEvent;

    @Value("${spring.data.notificationSampleRate}")
    private int notificationSampleRate;

    public int getNotificationTimeBeforeEvent() {
        return notificationTimeBeforeEvent;
    }

    public int getNotificationSampleRate() {
        return notificationSampleRate;
    }
}
