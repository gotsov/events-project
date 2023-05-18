package com.events.project.services;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.entities.Event;
import com.events.project.models.entities.User;
import com.events.project.repositories.EventRepository;
import com.events.project.repositories.TagRepository;
import com.events.project.repositories.VenueRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final ObjectMapper objectMapper;
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final TagRepository tagRepository;

    @Override
    public void add(EventDto eventDto, User user) {
        Event event = objectMapper.convertValue(eventDto, Event.class);
        event.setUser(user);
        event.setInsertTime(LocalDateTime.now());

        venueRepository.save(event.getVenue());
        tagRepository.saveAll(event.getTags());
        eventRepository.save(event);
    }
}
