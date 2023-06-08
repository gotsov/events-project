package com.events.project.services;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.dtos.TicketDto;
import com.events.project.models.dtos.TicketFullInfoDto;
import com.events.project.models.entities.User;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

public interface TicketService {
    List<TicketDto> generateTickets(List<SectorDto> sectorDtos, Long eventId);

    List<TicketDto> generateFreeTickets(Long eventId, Integer numberOfTickets);

    List<TicketDto> buy(User user, Long eventId, Long sectorId, Integer numberOfTickets);

    List<TicketFullInfoDto> getMyTickets(User user);

    byte[] generateTicketQRCode(Long ticketId) throws IOException, WriterException;
}
