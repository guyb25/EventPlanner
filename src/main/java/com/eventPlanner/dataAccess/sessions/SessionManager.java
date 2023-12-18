package com.eventPlanner.dataAccess.sessions;

public interface SessionManager {
    String createSession(Long userId);
    Long getUserIdFromSession(String sessionId);
    void endSession(String sessionId);
    boolean missing(String sessionId);
}
