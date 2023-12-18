package com.eventPlanner.notifications;

import com.eventPlanner.core.config.dataAccess.EventNotificationConfig;
import com.eventPlanner.dataAccess.notifiedEventsManager.NotifiedEventsManager;
import com.eventPlanner.dataAccess.userEvents.services.EventDataService;
import com.eventPlanner.dataAccess.userEvents.services.ParticipantDataService;
import com.eventPlanner.dataAccess.userEvents.schemas.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class EventNotifier {
    private final EventDataService eventDataService;
    private final ParticipantDataService participantDataService;
    private final NotifiedEventsManager notifiedEventsManager;
    private final ScheduledExecutorService executorService;
    private final EventNotificationConfig eventNotificationConfig;

    @Autowired
    public EventNotifier(EventDataService eventDataService, ParticipantDataService participantDataService,
                         NotifiedEventsManager notifiedEventsManager, EventNotificationConfig eventNotificationConfig) {
        this.eventDataService = eventDataService;
        this.participantDataService = participantDataService;
        this.notifiedEventsManager = notifiedEventsManager;
        this.eventNotificationConfig = eventNotificationConfig;

        executorService = Executors.newScheduledThreadPool(1);
    }

    public void scheduleUpcomingNotifications() {
        LocalDateTime sampleTime = LocalDateTime.now().plusHours(eventNotificationConfig.getNotificationSampleRate());
        List<Event> events = eventDataService.findAllEventsThatOccurBefore(sampleTime);

        for (var event : events) {
            if (!notifiedEventsManager.wasEventNotified(event.getId())) {
                var notificationTimeBeforeEvent = event.getTime().minusSeconds(eventNotificationConfig.getNotificationTimeBeforeEvent());
                var delay = Duration.between(notificationTimeBeforeEvent, LocalDateTime.now()).toSeconds();
                var eventId = event.getId();
                var participants = participantDataService.findEventParticipantsIds(eventId);

                if (notificationTimeBeforeEvent.isBefore(LocalDateTime.now())) {
                    notifyEvent(eventId, participants);
                }

                else if (!notifiedEventsManager.wasEventNotificationScheduled(eventId)) {
                    notifiedEventsManager.logScheduledNotification(eventId);
                    executorService.schedule(() -> notifyEvent(eventId, participants), delay, TimeUnit.SECONDS);
                }
            }
        }
    }

    public void notifyEvent(Long eventId, List<Long> participantIds) {
        notifiedEventsManager.logNotification(eventId);
        for (var participantId : participantIds) {
            System.out.println("User ID's: " + participantId + ", The event " + eventId + " is about to take place.");
        }
    }
}
