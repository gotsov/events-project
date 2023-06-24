package com.events.project.services;

import com.events.project.models.dtos.SectorDto;
import com.events.project.models.dtos.TicketFullInfoDto;
import com.events.project.models.entities.User;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

public interface TicketService {
    void generateTickets(List<SectorDto> sectorDtos, Long eventId);

    void generateFreeTickets(Long eventId, Integer numberOfTickets);

    void buy(User user, Long eventId, Long sectorId, Integer numberOfTickets);

    List<TicketFullInfoDto> getMyTickets(User user);

    byte[] generateTicketQRCode(Long ticketId) throws IOException, WriterException;
}
