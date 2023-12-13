package com.eventPlanner.dataAccess.sessions;

public interface SessionManager {
    String createSession(String userId);
    String getUserIdFromSession(String sessionId);
    void endSession(String sessionId);
}
