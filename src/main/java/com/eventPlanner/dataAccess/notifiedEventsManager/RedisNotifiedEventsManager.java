package com.eventPlanner.dataAccess.notifiedEventsManager;

import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;

public class RedisNotifiedEventsManager implements NotifiedEventsManager {
    private final Jedis jedis;
    private static final String NOTIFICATION_PREFIX = "notification: ";
    private final int expirationTimeSeconds;

    public RedisNotifiedEventsManager(Jedis jedis, int expirationTimeSeconds) {
        this.jedis = jedis;
        this.expirationTimeSeconds = expirationTimeSeconds;
    }


    @Override
    public void logNotification(Long eventId) {
        jedis.set(NOTIFICATION_PREFIX + eventId, LocalDateTime.now().toString());
        jedis.expire(NOTIFICATION_PREFIX + eventId, expirationTimeSeconds);
    }

    @Override
    public boolean wasEventNotified(Long eventId) {
        return jedis.exists(NOTIFICATION_PREFIX + eventId);
    }
}
