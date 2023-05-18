package com.events.project.controllers;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.entities.User;
import com.events.project.services.EventService;
import com.events.project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin
@AllArgsConstructor
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addEvent(@RequestBody EventDto eventDto) {
        Optional<User> user = userService.getLoggedUser();
        user.ifPresent(value -> eventService.add(eventDto, value));

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added event: " + eventDto.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable Long id) {
        Optional<EventDto> eventDto = eventService.getById(id);

        if (eventDto.isPresent()) {
            return new ResponseEntity<>(eventDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAll() {
        List<EventDto> eventDtos = eventService.getAll();

        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable Long id, @RequestBody EventDto updatedEvent) {
        EventDto eventDto = eventService.update(id, updatedEvent);

        if (eventDto != null) {
            return new ResponseEntity<>(eventDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        eventService.delete(id);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }
}
