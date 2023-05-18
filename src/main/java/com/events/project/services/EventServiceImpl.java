package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.EventDto;
import com.events.project.models.entities.Event;
import com.events.project.models.entities.User;
import com.events.project.repositories.EventRepository;
import com.events.project.repositories.TagRepository;
import com.events.project.repositories.VenueRepository;
import com.events.project.util.EventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.mapstruct.BeanMapping;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final ObjectMapper objectMapper;
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final TagRepository tagRepository;
    private final EventMapper eventMapper;

    @Override
    public void add(EventDto eventDto, User user) {
        Event event = objectMapper.convertValue(eventDto, Event.class);
        event.setUser(user);
        event.setInsertTime(LocalDateTime.now());

        venueRepository.save(event.getVenue());
        tagRepository.saveAll(event.getTags());
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> getAll() {
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();

        events.forEach(event -> eventDtos.add(objectMapper.convertValue(event, EventDto.class)));

        return eventDtos;
    }

    @Override
    public Optional<EventDto> getById(Long id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            EventDto eventDto = objectMapper.convertValue(event.get(), EventDto.class);
            return Optional.of(eventDto);
        }

        return Optional.empty();
    }

    @Override
    public EventDto update(Long id, EventDto eventDto) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            eventMapper.updateEntityFromDto(eventDto, event.get());

            venueRepository.save(event.get().getVenue());
            tagRepository.saveAll(event.get().getTags());
            eventRepository.save(event.get());
        } else {
            throw new ItemNotFoundException("Event with id=" + id + " doesn't exist");
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            eventRepository.delete(event.get());
        } else {
            throw new ItemNotFoundException("Event with id=" + id + " doesn't exist");
        }
    }
}
