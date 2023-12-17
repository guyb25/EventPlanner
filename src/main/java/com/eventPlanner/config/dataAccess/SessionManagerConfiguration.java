package com.eventPlanner.config.dataAccess;

import com.eventPlanner.dataAccess.sessions.RedisSessionManager;
import com.eventPlanner.dataAccess.sessions.SessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class SessionManagerConfiguration {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.sessionExpirationTime}")
    private int sessionExpirationTime;

    @Bean
    public SessionManager sessionManager() {
        return new RedisSessionManager(new Jedis(redisHost, redisPort), sessionExpirationTime);
    }
}
