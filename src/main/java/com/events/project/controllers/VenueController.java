package com.events.project.controllers;

import com.events.project.models.dtos.VenueDto;
import com.events.project.models.entities.User;
import com.events.project.services.UserService;
import com.events.project.services.VenueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        User user = userService.getLoggedUser();
        venueService.add(venueDto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added venue: " + venueDto.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> getById(@PathVariable Long id) {
        Optional<VenueDto> venueDto = venueService.getById(id);

        return venueDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<VenueDto> getByName(@PathVariable String name) {
        Optional<VenueDto> venueDto = venueService.getByName(name);

        return venueDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/current-user")
    public ResponseEntity<List<VenueDto>> getMyVenues() {
        List<VenueDto> venueDtos = venueService.getMyVenues();

        return new ResponseEntity<>(venueDtos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<VenueDto>> getAll() {
        List<VenueDto> venueDtos = venueService.getAll();

        return new ResponseEntity<>(venueDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody VenueDto updatedVenue) {
        venueService.update(id, updatedVenue);

        return new ResponseEntity<>("Updated venue: " + id, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        venueService.delete(id);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }
}
