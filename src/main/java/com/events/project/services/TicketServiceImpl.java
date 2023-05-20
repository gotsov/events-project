package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.TicketDto;
import com.events.project.models.dtos.VenueDto;
import com.events.project.models.entities.Ticket;
import com.events.project.models.entities.User;
import com.events.project.models.entities.Venue;
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

    private final ModelMapper modelMapper;
    @Override
    public void add(TicketDto ticketDto, User user) {
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);

        ticket.setUser(user);
        ticketRepository.save(ticket);
    }
}
