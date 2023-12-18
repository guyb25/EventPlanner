package com.eventPlanner.dataAccess.userEvents.repositories;

import com.eventPlanner.dataAccess.userEvents.schemas.EventParticipantId;
import com.eventPlanner.dataAccess.userEvents.schemas.Participant;
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
}
