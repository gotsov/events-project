package com.events.project.services;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.dtos.TicketDto;
import com.events.project.models.entities.User;

import java.util.List;

public interface TicketService {
    void add(TicketDto ticketDto, User user);

    List<TicketDto> generateTickets(List<SectorDto> sectorDtos, Long eventId);

    List<TicketDto> generateFreeTickets(Long eventId, Integer numberOfTickets);
}
