package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.*;
import com.events.project.models.entities.*;
import com.events.project.models.enums.TicketStatus;
import com.events.project.repositories.EventRepository;
import com.events.project.repositories.SectorRepository;
import com.events.project.repositories.TicketRepository;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final SectorRepository sectorRepository;
    private final QRCodeService qrCodeService;

    private final ModelMapper modelMapper;

    @Override
    public void generateTickets(List<SectorDto> sectorDtos, Long eventId) {
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
                    newTicket.setStatus(TicketStatus.AVAILABLE);

                    tickets.add(newTicket);
                }
                ticketRepository.saveAll(tickets);
                sectorRepository.save(sector);
                eventRepository.save(event.get());
            }
        }
    }

    @Override
    public void generateFreeTickets(Long eventId, Integer numberOfTickets) {
        Optional<Event> event = eventRepository.findById(eventId);

        if (event.isPresent()) {
            Sector freeSector = Sector.builder()
                    .name("free")
                    .numberOfTickets(numberOfTickets)
                    .price(0.0)
                    .venue(event.get().getVenue())
                    .build();

            if (event.get().getVenue().getSectors() != null) {
                event.get().getVenue().getSectors().add(freeSector);
            } else {
                event.get().getVenue().setSectors(List.of(freeSector));
            }
            sectorRepository.save(freeSector);

            List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < numberOfTickets; i++) {
                Ticket newTicket = new Ticket();

                newTicket.setEvent(event.get());
                newTicket.setSector(freeSector);
                newTicket.setStatus(TicketStatus.AVAILABLE);

                tickets.add(newTicket);
            }

            ticketRepository.saveAll(tickets);
            eventRepository.save(event.get());
        }
    }

    @Override
    public void buy(User user, Long eventId, Long sectorId, Integer numberOfTickets) {
        Optional<Event> event = eventRepository.findById(eventId);
        Optional<Sector> sector = sectorRepository.findById(sectorId);

        if (event.isPresent() && sector.isPresent()) {
            List<Ticket> ticketsForEvent = ticketRepository.findAllByEventAndSectorAndStatus(event.get(),
                    sector.get(),
                    TicketStatus.AVAILABLE);

            if (numberOfTickets > ticketsForEvent.size()) {
                throw new IndexOutOfBoundsException("Exceeding the amount of tickets you can buy");
            }

            List<Ticket> ticketsToBuy = ticketsForEvent.subList(0, numberOfTickets);

            for (Ticket ticket : ticketsToBuy) {
                ticket.setUser(user);
                ticket.setTimeBought(LocalDateTime.now());
                ticket.setStatus(TicketStatus.BOUGHT);
            }

            ticketRepository.saveAll(ticketsToBuy);
        } else {
            throw new ItemNotFoundException("Event or Sector does not exist");
        }
    }

    @Override
    public List<TicketFullInfoDto> getMyTickets(User user) {
        List<Ticket> myTickets = ticketRepository.findAllByUser(user);

        List<TicketFullInfoDto> result = new ArrayList<>();

        myTickets.forEach(ticket -> result.add(modelMapper.map(ticket, TicketFullInfoDto.class)));

        return result;
    }

    @Override
    public byte[] generateTicketQRCode(Long ticketId) throws IOException, WriterException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ItemNotFoundException("Ticket not found with ID: " + ticketId));

        String qrCodeData = generateQRCodeData(ticket);

        return qrCodeService.generateQRCodeImage(qrCodeData);
    }

    private String generateQRCodeData(Ticket ticket) {
        return ticket.getId().toString();
    }
}
