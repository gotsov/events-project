package com.events.project.services;

import com.events.project.models.dtos.UserDto;
import com.events.project.models.dtos.UserInfoDto;
import com.events.project.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(UserDto userDto);

    Optional<User> findByEmail(String email);

    User getLoggedUser();

    UserInfoDto getLoggedUserDto();

    Boolean isEventOrganizer(User user, Long eventId);

    List<UserInfoDto> getAll();

    UserInfoDto promoteUser(Long userId, String decision);

    UserInfoDto demoteUser(Long userId);

    UserInfoDto requestOrganizer(User user);
}
