package com.events.project.services;

import com.events.project.models.dtos.UserDto;
import com.events.project.models.entities.User;

import java.util.Optional;

public interface UserService {
    void add(UserDto userDto);

    Optional<User> findByEmail(String email);

    User getLoggedUser();
}
