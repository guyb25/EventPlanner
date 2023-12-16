package com.eventPlanner.models.schemas;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_participants")
public class Participant {
    @EmbeddedId
    private EventParticipantId id;

    public Participant(Long eventId, Long userId) {
        this.id = new EventParticipantId(eventId, userId);
    }

    public Participant() {

    }
}
