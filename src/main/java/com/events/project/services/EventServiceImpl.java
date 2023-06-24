package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.SectorWithAvailableTicketsDto;
import com.events.project.models.dtos.TagDto;
import com.events.project.models.entities.*;
import com.events.project.models.enums.TicketStatus;
import com.events.project.repositories.*;
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
    private final TicketRepository ticketRepository;

    private final ModelMapper modelMapper;
    private final SectorRepository sectorRepository;

    @Override
    public EventDto add(EventDto eventDto, User user) {
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

        Optional<Venue> venueFromDb = venueRepository.findByName(event.getVenue().getName());
        if (!venueFromDb.isPresent()) {
            event.getVenue().setEvents(List.of(event));
            venueRepository.save(event.getVenue());
        } else {
            event.setVenue(venueFromDb.get());
        }

        sectorRepository.saveAll(event.getVenue().getSectors());
        Event addedEvent = eventRepository.save(event);

        return modelMapper.map(addedEvent, EventDto.class);
    }

    @Override
    public List<EventDto> getAll() {
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = new ArrayList<>();

        events.forEach(event -> eventDtos.add(modelMapper.map(event, EventDto.class)));

        return eventDtos;
    }

    @Override
    public List<String> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<String> result = new ArrayList<>();

        tags.forEach(tag -> result.add(tag.getName()));
        return result;
    }

    @Override
    public EventDto getById(Long id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            return modelMapper.map(event.get(), EventDto.class);
        } else {
            throw new ItemNotFoundException("Event not found id=" + id);
        }
    }

    @Override
    public EventDto update(EventDto eventDto) {
        Optional<Event> event = eventRepository.findById(eventDto.getId());

        if (event.isPresent()) {
            event.get().setName(eventDto.getName());
            event.get().setStartDate(eventDto.getStartDate());
            event.get().setEndDate(eventDto.getEndDate());
            event.get().setDescription(eventDto.getDescription());

            Optional<Venue> venueFromDb = venueRepository.findByName(eventDto.getVenue().getName());

            updateVenue(eventDto, event, venueFromDb);

            updateTags(eventDto, event);

            tagRepository.saveAll(event.get().getTags());
            eventRepository.save(event.get());
        }

        return modelMapper.map(event, EventDto.class);
    }

    private void updateVenue(EventDto eventDto, Optional<Event> event, Optional<Venue> venueFromDb) {
        if (venueFromDb.isPresent()) {
            event.get().setVenue(venueFromDb.get());

            if (venueFromDb.get().getEvents() == null) {
                venueFromDb.get().setEvents(List.of(event.get()));
            } else {
                venueFromDb.get().getEvents().add(event.get());
            }

            venueFromDb.get().setDescription(eventDto.getVenue().getDescription());

            venueRepository.save(venueFromDb.get());

            event.get().setVenue(venueFromDb.get());
        }
    }

    private void updateTags(EventDto eventDto, Optional<Event> event) {
        for (TagDto tagDto : eventDto.getTags()) {
            Tag tagToAdd = modelMapper.map(tagDto, Tag.class);
            if (!event.get().getTags().contains(tagToAdd)) {
                Optional<Tag> fromDb = tagRepository.findByName(tagToAdd.getName());

                if (fromDb.isPresent()) {
                    event.get().getTags().add(fromDb.get());
                } else {
                    event.get().getTags().add(tagToAdd);
                    tagToAdd.setEvents(List.of(event.get()));
                }

            }
        }

        List<Tag> tagsToRemove = new ArrayList<>();
        for (Tag tag : event.get().getTags()) {
            TagDto tagToCompare = modelMapper.map(tag, TagDto.class);
            if (!eventDto.getTags().contains(tagToCompare)) {
                tagsToRemove.add(tag);
                tag.getEvents().remove(event.get());
            }
        }
        event.get().getTags().removeAll(tagsToRemove);

        for (Tag tag : tagsToRemove) {
            if(tag.getEvents().size() == 0) {
                tagRepository.delete(tag);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            List<Ticket> ticketsToDelete = ticketRepository.findAllByEvent(event.get());
            ticketRepository.deleteAll(ticketsToDelete);
            eventRepository.delete(event.get());
        } else {
            throw new ItemNotFoundException("Event with id=" + id + " doesn't exist");
        }
    }

    @Override
    public List<EventDto> getCurrentUserEvents(User user) {
        List<Event> events = eventRepository.findAllByUser(user);
        List<EventDto> eventDtos = new ArrayList<>();

        events.forEach(event -> eventDtos.add(modelMapper.map(event, EventDto.class)));
        return eventDtos;
    }

    @Override
    public List<SectorWithAvailableTicketsDto> getEventSectors(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        List<Sector> sectors = new ArrayList<>();

        if (event.isPresent()) {
            for (Ticket ticket : event.get().getTickets()) {
                if (!sectors.contains(ticket.getSector())) {
                    sectors.add(ticket.getSector());
                }
            }
        }

        List<SectorWithAvailableTicketsDto> result = new ArrayList<>();

        for (Sector sector : sectors) {
            int availableTickets = 0;
            for (Ticket ticket : sector.getTickets()) {
                if (ticket.getStatus() == TicketStatus.AVAILABLE) {
                    availableTickets++;
                }
            }

            SectorWithAvailableTicketsDto sectorDto = modelMapper.map(sector, SectorWithAvailableTicketsDto.class);
            sectorDto.setNumberOfAvailableTickets(availableTickets);
            result.add(sectorDto);
        }

        return result;
    }
}
