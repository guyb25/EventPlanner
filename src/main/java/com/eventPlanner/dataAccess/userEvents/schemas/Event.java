package com.eventPlanner.dataAccess.userEvents.schemas;

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

    public Event(Long id, String name, Long hostId, String description, String location,
                 LocalDateTime time, LocalDateTime creationTime) {
        this.id = id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
