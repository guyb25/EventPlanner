package com.eventPlanner.models.schemas;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long hostId;
    private String description;
    private String location;
    private LocalDateTime time;
    private LocalDateTime creationTime;

    public Event(String name, Long hostId, String description, String location, LocalDateTime time, LocalDateTime creationTime) {
        this.name = name;
        this.hostId = hostId;
        this.description = description;
        this.location = location;
        this.time = time;
        this.creationTime = creationTime;
    }

    public Event() {

    }

    public Long getId() {
        return id;
    }
}
