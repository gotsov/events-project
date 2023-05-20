package com.events.project.controllers;

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

import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestBody TicketDto ticketDto) {
        User user = userService.getLoggedUser();

        ticketService.add(ticketDto, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User: " + user + " successfully bought a ticket");
    }
}