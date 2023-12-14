package com.eventPlanner.dataAccess.userEvents;

import com.eventPlanner.models.schemas.EventParticipantId;
import com.eventPlanner.models.schemas.Participant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantsRepository extends JpaRepository<Participant, EventParticipantId> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Participant participant WHERE participant.id.userId = :userId")
    void deleteAllByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Participant participant WHERE participant.id.eventId = :eventId")
    void deleteAllByEventId(Long eventId);
}
