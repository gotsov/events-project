package com.events.project.services;

import com.events.project.models.dtos.SectorDto;
import com.events.project.models.dtos.TicketDto;
import com.events.project.models.entities.User;

public interface TicketService {
    void add(TicketDto ticketDto, User user);
}
