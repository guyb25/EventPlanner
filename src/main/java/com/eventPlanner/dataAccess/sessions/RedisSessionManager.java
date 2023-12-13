package com.eventPlanner.dataAccess.sessions;

import redis.clients.jedis.Jedis;

import java.util.UUID;

public class RedisSessionManager implements SessionManager {
    private final Jedis jedis;
    private static final String SESSION_PREFIX = "session:";
    private final int expirationTimeSeconds;

    public RedisSessionManager(Jedis jedis, int expirationTimeSeconds) {
        this.jedis = jedis;
        this.expirationTimeSeconds = expirationTimeSeconds;
    }


    @Override
    public String createSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        jedis.set(SESSION_PREFIX + sessionId, userId);
        jedis.expire(SESSION_PREFIX + sessionId, expirationTimeSeconds); // 30 * 60 seconds
        return sessionId;
    }

    @Override
    public String getUserIdFromSession(String sessionId) {
        return jedis.get(SESSION_PREFIX + sessionId);
    }

    @Override
    public void endSession(String sessionId) {
        jedis.del(SESSION_PREFIX + sessionId);
    }

    @Override
    public boolean exists(String sessionId) {
        return jedis.exists(SESSION_PREFIX + sessionId);
    }
}
