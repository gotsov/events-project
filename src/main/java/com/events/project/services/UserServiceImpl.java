package com.events.project.services;

import com.events.project.models.dtos.UserDto;
import com.events.project.models.entities.User;
import com.events.project.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void add(UserDto userDto) {
        User user = objectMapper.convertValue(userDto, User.class);
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}
