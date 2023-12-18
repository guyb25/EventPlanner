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
    public String createSession(Long userId) {
        String sessionId = UUID.randomUUID().toString();
        jedis.set(SESSION_PREFIX + sessionId, userId.toString());
        jedis.expire(SESSION_PREFIX + sessionId, expirationTimeSeconds);
        return sessionId;
    }

    @Override
    public Long getUserIdFromSession(String sessionId) {
        return Long.parseLong(jedis.get(SESSION_PREFIX + sessionId));
    }

    @Override
    public void endSession(String sessionId) {
        jedis.del(SESSION_PREFIX + sessionId);
    }

    @Override
    public boolean missing(String sessionId) {
        return !jedis.exists(SESSION_PREFIX + sessionId);
    }
}
