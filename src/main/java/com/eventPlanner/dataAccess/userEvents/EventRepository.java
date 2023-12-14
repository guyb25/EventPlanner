package com.eventPlanner.dataAccess.userEvents;

import com.eventPlanner.models.schemas.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
