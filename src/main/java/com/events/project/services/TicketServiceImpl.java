package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.dtos.TicketDto;
import com.events.project.models.dtos.VenueDto;
import com.events.project.models.entities.*;
import com.events.project.repositories.EventRepository;
import com.events.project.repositories.SectorRepository;
import com.events.project.repositories.TicketRepository;
import com.events.project.repositories.VenueRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final SectorRepository sectorRepository;

    private final ModelMapper modelMapper;
    @Override
    public void add(TicketDto ticketDto, User user) {
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);

        ticket.setUser(user);
        ticketRepository.save(ticket);
    }

    @Override
    public List<TicketDto> generateTickets(List<SectorDto> sectorDtos, Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        List<Sector> sectors = new ArrayList<>();

        if (event.isPresent()) {
            for (SectorDto sectorDto : sectorDtos) {
                Optional<Sector> sector = sectorRepository.findById(sectorDto.getId());

                sector.ifPresent(sectors::add);
            }

            for (Sector sector : sectors) {
                List<Ticket> tickets = new ArrayList<>();
                for (int i = 0; i < sector.getNumberOfTickets(); i++) {
                    Ticket newTicket = new Ticket();

                    newTicket.setEvent(event.get());
                    newTicket.setSector(sector);

                    tickets.add(newTicket);
                }
                ticketRepository.saveAll(tickets);
                sectorRepository.save(sector);
                eventRepository.save(event.get());
            }
        }

        return null;
    }
}
