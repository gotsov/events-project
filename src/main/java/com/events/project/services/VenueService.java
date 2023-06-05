package com.events.project.services;

import com.events.project.models.dtos.VenueDto;
import com.events.project.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface VenueService {
    void add(VenueDto venueDto, User user);

    List<VenueDto> getAll();

    Optional<VenueDto> getById(Long id);

    Optional<VenueDto> getByName(String name);

    VenueDto update(Long id, VenueDto venueDto);

    void delete(Long id);

    List<VenueDto> getMyVenues();
}
