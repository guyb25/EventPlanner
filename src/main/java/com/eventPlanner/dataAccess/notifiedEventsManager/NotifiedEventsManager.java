package com.eventPlanner.dataAccess.notifiedEventsManager;

public interface NotifiedEventsManager {
    void logScheduledNotification(Long eventId);
    void logNotification(Long eventId);
    boolean wasEventNotified(Long eventId);
    boolean wasEventNotificationScheduled(Long eventId);
}
