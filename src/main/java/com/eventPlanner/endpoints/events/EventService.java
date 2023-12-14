package com.eventPlanner.endpoints.events;

import com.eventPlanner.dataAccess.userEvents.EventRepository;
import com.eventPlanner.dataAccess.userEvents.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.UserRepository;
import com.eventPlanner.models.schemas.Event;
import com.eventPlanner.models.schemas.Participant;
import com.eventPlanner.models.serviceResult.ServiceResult;
import com.eventPlanner.models.serviceResult.ServiceResultFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ParticipantsRepository participantsRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, ParticipantsRepository participantsRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.participantsRepository = participantsRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ServiceResult CreateEvent(String name, String description, String location, LocalDateTime time, List<String> participants) {
        Event event = new Event(name, description, location, time, LocalDateTime.now());
        List<Long> participantsIds = new ArrayList<>();

        for (String participant : participants) {
            if (!userRepository.existsUserByName(participant)) {
                throw new IllegalArgumentException("user not found: " + participant);
            }
        }

        // Make sure to grab the newly created event entity, that has the auto generated ID
        event = this.eventRepository.save(event);

        for (String participant : participants) {
            Long participantId = userRepository.getUserByName(participant).getId();
            Participant eventParticipant = new Participant(event.getId(), participantId);
            this.participantsRepository.save(eventParticipant);
        }

        return ServiceResultFactory.eventCreatedSuccessfully(event.getId());
    }
}
