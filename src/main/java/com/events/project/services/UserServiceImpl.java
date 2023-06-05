package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.UserDto;
import com.events.project.models.dtos.UserInfoDto;
import com.events.project.models.entities.Event;
import com.events.project.models.entities.User;
import com.events.project.repositories.EventRepository;
import com.events.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void add(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> user = findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ItemNotFoundException("User not found");
        }
    }

    @Override
    public UserInfoDto getLoggedUserDto() {
        User user = getLoggedUser();
        return modelMapper.map(user, UserInfoDto.class);
    }

    @Override
    public Boolean isEventOrganizer(User user, Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);

        if (event.isPresent()) {
            if (event.get().getUser().getId() == user.getId()) {
                return true;
            }
        }
        return false;
    }
}
