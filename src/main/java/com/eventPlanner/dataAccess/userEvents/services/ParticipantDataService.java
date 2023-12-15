package com.eventPlanner.dataAccess.userEvents.services;

import com.eventPlanner.dataAccess.userEvents.repositories.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.repositories.UserRepository;
import com.eventPlanner.models.schemas.Participant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantDataService {
    private final UserRepository userRepository;
    private final ParticipantsRepository participantsRepository;

    @Autowired
    public ParticipantDataService(UserRepository userRepository, ParticipantsRepository participantsRepository) {
        this.userRepository = userRepository;
        this.participantsRepository = participantsRepository;
    }

    @Transactional
    public void inviteParticipantsToEvent(Long eventId, List<String> users) {
        var participants = users
                .stream()
                .map(user -> new Participant(eventId, userRepository.findUserByName(user).getId()))
                .toList();

        participantsRepository.saveAll(participants);
    }

    public List<String> findEventParticipantsNames(Long eventId) {
        List<Long> participantsIds = participantsRepository.findAllByEventId(eventId);
        return participantsIds.stream().map(id -> userRepository.findUserById(id).getName()).toList();
    }

    public List<Long> findEventParticipantsIds(Long eventId) {
        return participantsRepository.findAllByEventId(eventId);
    }

    public void deleteAllEventParticipants(Long eventId) {
        participantsRepository.deleteAllByEventId(eventId);
    }

    public void deleteAllByUserId(Long userId) {
        participantsRepository.deleteAllByUserId(userId);
    }
}
