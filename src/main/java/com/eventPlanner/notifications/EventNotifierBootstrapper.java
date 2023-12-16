package com.eventPlanner.notifications;

import com.eventPlanner.config.EventNotificationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class EventNotifierBootstrapper {
    private final EventNotificationConfig eventNotificationConfig;
    private final EventNotifier eventNotifier;

    @Autowired
    public EventNotifierBootstrapper(EventNotificationConfig eventNotificationConfig, EventNotifier eventNotifier) {
        this.eventNotificationConfig = eventNotificationConfig;
        this.eventNotifier = eventNotifier;
    }

    public void bootstrap() {
        var executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(
                eventNotifier::scheduleUpcomingNotifications,
                0,
                eventNotificationConfig.getNotificationTimeBeforeEvent(),
                TimeUnit.SECONDS
        );
    }
}
