package com.events.project.controllers;

import com.events.project.models.dtos.UserDto;
import com.events.project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> add(@RequestBody UserDto userDto) {
        userService.add(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added user: " + userDto.getEmail());
    }
}
