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
    private String description;
    private String location;
    private LocalDateTime time;
    private LocalDateTime creationTime;

    public Event(String name, String description, String location, LocalDateTime time, LocalDateTime creationTime) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.time = time;
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }
}
