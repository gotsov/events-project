package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.TagDto;
import com.events.project.models.entities.Event;
import com.events.project.models.entities.Tag;
import com.events.project.models.entities.User;
import com.events.project.repositories.EventRepository;
import com.events.project.repositories.TagRepository;
import com.events.project.repositories.VenueRepository;
import com.events.project.util.EventMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final TagRepository tagRepository;

    private final EventMapper eventMapper;
    private final ModelMapper modelMapper;

    @Override
    public void add(EventDto eventDto, User user) {
        Event event = modelMapper.map(eventDto, Event.class);
        event.setUser(user);
        event.setInsertTime(LocalDateTime.now());
        event.setTags(new ArrayList<>());

        List<Tag> tags = tagRepository.findAll();
        List<TagDto> tagDtos = new ArrayList<>();
        tags.forEach(tag -> tagDtos.add(modelMapper.map(tag, TagDto.class)));

        for (TagDto tagDto : eventDto.getTags()) {
            if (!tagDtos.contains(tagDto)) {
                Tag newTag = modelMapper.map(tagDto, Tag.class);
                tagRepository.save(newTag);
                event.getTags().add(newTag);
            } else {
                Optional<Tag> tag = tagRepository.findByName(tagDto.getName());
                event.getTags().add(tag.get());
            }
        }

        venueRepository.save(event.getVenue());
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> getAll() {
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();

        events.forEach(event -> eventDtos.add(modelMapper.map(event, EventDto.class)));

        return eventDtos;
    }

    @Override
    public Optional<EventDto> getById(Long id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            EventDto eventDto = modelMapper.map(event.get(), EventDto.class);
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
