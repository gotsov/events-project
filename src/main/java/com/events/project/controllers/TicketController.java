package com.events.project.controllers;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.dtos.TicketDto;
import com.events.project.models.dtos.TicketFullInfoDto;
import com.events.project.models.entities.User;
import com.events.project.services.SectorService;
import com.events.project.services.TicketService;
import com.events.project.services.UserService;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;

    @PostMapping("/generate/event")
    public ResponseEntity<String> generateTickets(@RequestBody List<SectorDto> sectorDtos,
                                                           @RequestParam("eventId") Long eventId) {
        ticketService.generateTickets(sectorDtos, eventId);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully generated tickets");
    }

    @PostMapping("/generate/event/free")
    public ResponseEntity<String> generateFreeTickets(@RequestParam("eventId") Long eventId,
                                                               @RequestParam("numberOfTickets") Integer numberOfTickets) {
        ticketService.generateFreeTickets(eventId, numberOfTickets);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully generated tickets");
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestParam("eventId") Long eventId,
                                               @RequestParam("sectorId") Long sectorId,
                                               @RequestParam("numberOfTickets") Integer numberOfTickets) {
        User user = userService.getLoggedUser();

        ticketService.buy(user, eventId, sectorId, numberOfTickets);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully bought ticket for event: " + eventId);
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<List<TicketFullInfoDto>> getMyTickets() {
        User user = userService.getLoggedUser();

        List<TicketFullInfoDto> result = ticketService.getMyTickets(user);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{ticketId}/qrcode")
    public ResponseEntity<byte[]> getTicketQRCode(@PathVariable String ticketId) throws IOException, WriterException {
        byte[] qrCodeImage = ticketService.generateTicketQRCode(Long.valueOf(ticketId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
    }
}
