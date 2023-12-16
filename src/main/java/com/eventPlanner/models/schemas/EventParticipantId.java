package com.eventPlanner.models.schemas;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EventParticipantId implements Serializable {
    private Long eventId;
    private Long userId;

    public EventParticipantId(Long eventId, Long userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    public EventParticipantId() {

    }
}
