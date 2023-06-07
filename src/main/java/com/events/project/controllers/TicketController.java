package com.events.project.controllers;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.dtos.TicketDto;
import com.events.project.models.entities.User;
import com.events.project.services.SectorService;
import com.events.project.services.TicketService;
import com.events.project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;

    @PostMapping("/generate/event")
    public ResponseEntity<List<TicketDto>> generateTickets(@RequestBody List<SectorDto> sectorDtos,
                                                           @RequestParam("eventId") Long eventId) {
        List<TicketDto> ticketDtos = ticketService.generateTickets(sectorDtos, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(ticketDtos);
    }

    @PostMapping("/generate/event/free")
    public ResponseEntity<List<TicketDto>> generateFreeTickets(@RequestParam("eventId") Long eventId,
                                                               @RequestParam("numberOfTickets") Integer numberOfTickets) {
        List<TicketDto> ticketDtos = ticketService.generateFreeTickets(eventId, numberOfTickets);
        return ResponseEntity.status(HttpStatus.OK).body(ticketDtos);
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestBody TicketDto ticketDto) {
        User user = userService.getLoggedUser();

        ticketService.add(ticketDto, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User: " + user + " successfully bought a ticket");
    }
}
