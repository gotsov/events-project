package com.events.project.controllers;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.VenueDto;
import com.events.project.models.entities.User;
import com.events.project.services.EventService;
import com.events.project.services.UserService;
import com.events.project.services.VenueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin
@AllArgsConstructor
public class VenueController {
    private final VenueService venueService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody VenueDto venueDto) {
        Optional<User> user = userService.getLoggedUser();
        user.ifPresent(value -> venueService.add(venueDto, value));

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added venue: " + venueDto.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> getById(@PathVariable Long id) {
        Optional<VenueDto> venueDto = venueService.getById(id);

        return venueDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<VenueDto>> getAll() {
        List<VenueDto> venueDtos = venueService.getAll();

        return new ResponseEntity<>(venueDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueDto> update(@PathVariable Long id, @RequestBody VenueDto updatedVenue) {
        VenueDto venueDto = venueService.update(id, updatedVenue);

        if (venueDto != null) {
            return new ResponseEntity<>(venueDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        venueService.delete(id);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }
}
