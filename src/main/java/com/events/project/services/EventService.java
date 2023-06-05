package com.events.project.services;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.VenueDto;
import com.events.project.models.entities.Event;
import com.events.project.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface EventService {
    EventDto add(EventDto eventDto, User user);

    List<EventDto> getAll();

    Optional<EventDto> getById(Long id);

    EventDto update(EventDto eventDto);

    void delete(Long id);
}
