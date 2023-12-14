package com.eventPlanner.dataAccess.userEvents;

import com.eventPlanner.models.schemas.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByHostId(Long hostId);
    Event findEventById(Long id);
}
