package com.eventPlanner.dataAccess.userEvents;

import com.eventPlanner.models.schemas.Event;
import com.eventPlanner.models.schemas.EventParticipantId;
import com.eventPlanner.models.schemas.Participant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participant, EventParticipantId> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Participant participant WHERE participant.id.userId = :userId")
    void deleteAllByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Participant participant WHERE participant.id.eventId = :eventId")
    void deleteAllByEventId(Long eventId);

    @Query("SELECT participant.id.userId FROM Participant participant WHERE participant.id.eventId = :eventId")
    List<Long> findAllByEventId(Long eventId);

    @Query("SELECT participant.id.eventId FROM Participant participant WHERE participant.id.userId = :userId")
    List<Event> findAllEventsByParticipantId(Long userId);

    @Query("SELECT event FROM Event event " +
            "INNER JOIN Participant participant ON event.id = participant.id.eventId " +
            "INNER JOIN User user ON participant.id.userId = user.id " +
            "WHERE user.id = :userId AND event.location = :location")
    List<Event> findAllEventsByUserIdAndLocation(Long userId, String location);
}
