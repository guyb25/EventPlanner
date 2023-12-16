package com.eventPlanner.dataAccess.notifiedEventsManager;

public interface NotifiedEventsManager {
    public void logNotification(Long eventId);
    public boolean wasEventNotified(Long eventId);
}
