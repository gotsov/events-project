package com.events.project.controllers;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.dtos.UserDto;
import com.events.project.models.entities.User;
import com.events.project.services.EventService;
import com.events.project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin
@AllArgsConstructor
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody EventDto eventDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> user = userService.findByEmail(email);

        user.ifPresent(value -> eventService.add(eventDto, value));

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added event: " + eventDto.getName());
    }
}
