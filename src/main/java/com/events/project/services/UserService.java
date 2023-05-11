package com.events.project.services;

import com.events.project.models.dtos.UserDto;
import com.events.project.models.entities.User;
import com.events.project.models.enums.Role;
import com.events.project.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public void add(UserDto userDto) {
        User user = objectMapper.convertValue(userDto, User.class);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}
