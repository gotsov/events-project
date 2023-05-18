package com.events.project.services;

import com.events.project.models.dtos.EventDto;
import com.events.project.models.entities.User;

public interface EventService {
    void add(EventDto eventDto, User user);
}
