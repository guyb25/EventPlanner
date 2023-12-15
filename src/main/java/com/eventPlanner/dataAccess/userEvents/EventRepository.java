package com.eventPlanner.dataAccess.userEvents;

import com.eventPlanner.models.schemas.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByHostId(Long hostId);
    Event findEventById(Long id);

    @Query("SELECT event FROM Participant participant " +
            "INNER JOIN Event event ON event.id = participant.id.eventId " +
            "WHERE participant.id.userId = :participantId")
    List<Event> findAllEventsByParticipantId(Long participantId);

    @Query("SELECT event FROM Participant participant " +
            "INNER JOIN Event event ON event.id = participant.id.eventId " +
            "WHERE participant.id.userId = :participantId " +
            "ORDER BY event.time")
    List<Event> findAllEventsByParticipantIdOrderByTime(Long participantId);

    @Query("SELECT event FROM Participant participant " +
            "INNER JOIN Event event ON event.id = participant.id.eventId " +
            "WHERE participant.id.userId = :participantId " +
            "ORDER BY event.creationTime")
    List<Event> findAllEventsByParticipantIdOrderByCreationTime(Long participantId);

    @Query("SELECT event " +
            "FROM Event event " +
            "LEFT JOIN Participant participant " +
            "ON participant.id.eventId = event.id AND participant.id.userId = :participantId " +
            "GROUP BY event.id " +
            "ORDER BY COUNT(participant) DESC")
    List<Event> findAllEventsByParticipantIdOrderByPopularity(Long participantId);

    @Query("SELECT event FROM Event event " +
            "INNER JOIN Participant participant ON event.id = participant.id.eventId " +
            "INNER JOIN User user ON participant.id.userId = user.id " +
            "WHERE user.id = :userId AND event.location = :location")
    List<Event> findAllEventsByUserIdAndLocation(Long userId, String location);
}
