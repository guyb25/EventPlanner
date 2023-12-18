package com.eventPlanner.core.config.dataAccess;

import com.eventPlanner.dataAccess.notifiedEventsManager.NotifiedEventsManager;
import com.eventPlanner.dataAccess.notifiedEventsManager.RedisNotifiedEventsManager;
import com.eventPlanner.dataAccess.sessions.RedisSessionManager;
import com.eventPlanner.dataAccess.sessions.SessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class NotifiedEventManagerConfiguration {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.notifiedEventsExpirationTime}")
    private int notifiedEventsExpirationTime;

    @Bean
    public RedisNotifiedEventsManager NotifiedEventManager() {
        return new RedisNotifiedEventsManager(new Jedis(redisHost, redisPort), notifiedEventsExpirationTime);
    }
}
