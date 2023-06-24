package com.events.project.services;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.SectorWithAvailableTicketsDto;
import com.events.project.models.entities.User;

import java.util.List;


public interface EventService {
    EventDto add(EventDto eventDto, User user);

    List<EventDto> getAll();

    List<String> getAllTags();

    EventDto getById(Long id);

    EventDto update(EventDto eventDto);

    void delete(Long id);

    List<EventDto> getCurrentUserEvents(User user);

    List<SectorWithAvailableTicketsDto> getEventSectors(Long id);
}

