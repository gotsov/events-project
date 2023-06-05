package com.events.project.controllers;

import com.events.project.models.dtos.UserDto;
import com.events.project.models.dtos.UserInfoDto;
import com.events.project.models.entities.User;
import com.events.project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/current")
    public ResponseEntity<UserInfoDto> getCurrentLoggedUser() {
        UserInfoDto userDto = userService.getLoggedUserDto();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/role")
    public ResponseEntity<String> getCurrentUserRole() {
        User user = userService.getLoggedUser();
        return ResponseEntity.ok(user.getRole().toString());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Boolean> isEventOrganizer(@PathVariable Integer eventId) {
        User user = userService.getLoggedUser();
        Boolean isOrganizer = userService.isEventOrganizer(user, eventId.longValue());
        return ResponseEntity.ok(isOrganizer);
    }
}
