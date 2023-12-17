package com.eventPlanner.integration;

import com.eventPlanner.dataAccess.sessions.RedisSessionManager;
import com.eventPlanner.dataAccess.userEvents.repositories.EventRepository;
import com.eventPlanner.dataAccess.userEvents.repositories.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ParticipantsRepository participantsRepository;

    @Autowired
    protected EventRepository eventRepository;
}
