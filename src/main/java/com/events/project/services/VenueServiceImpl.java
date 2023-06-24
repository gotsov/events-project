package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.VenueDto;
import com.events.project.models.entities.User;
import com.events.project.models.entities.Venue;
import com.events.project.repositories.VenueRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final UserService userService;

    private final ModelMapper modelMapper;
    private final VenueRepository venueRepository;

    @Override
    public void add(VenueDto venueDto, User user) {
        Venue venue = modelMapper.map(venueDto, Venue.class);

        venue.setUser(user);
        venueRepository.save(venue);
    }

    @Override
    public List<VenueDto> getAll() {
        List<Venue> venues = venueRepository.findAll();
        List<VenueDto> venueDtos = new ArrayList<>();

        venues.forEach(venue -> venueDtos.add(modelMapper.map(venue, VenueDto.class)));

        return venueDtos;
    }

    @Override
    public Optional<VenueDto> getById(Long id) {
        Optional<Venue> venue = venueRepository.findById(id);

        if (venue.isPresent()) {
            VenueDto eventDto = modelMapper.map(venue.get(), VenueDto.class);
            return Optional.of(eventDto);
        }

        return Optional.empty();
    }

    @Override
    public Optional<VenueDto> getByName(String name) {
        Optional<Venue> venue = venueRepository.findByName(name);

        if (venue.isPresent()) {
            VenueDto eventDto = modelMapper.map(venue.get(), VenueDto.class);
            return Optional.of(eventDto);
        }

        return Optional.empty();
    }

    @Override
    public void update(Long id, VenueDto venueDto) {
        Optional<Venue> venue = venueRepository.findById(id);
        Venue updatedVenue = modelMapper.map(venueDto, Venue.class);

        if (venue.isPresent()) {
            BeanUtils.copyProperties(venue, updatedVenue);
            venueRepository.save(venue.get());
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Venue> venue = venueRepository.findById(id);

        if (venue.isPresent()) {
            venueRepository.delete(venue.get());
        } else {
            throw new ItemNotFoundException("Venue with id=" + id + " doesn't exist");
        }
    }

    @Override
    public List<VenueDto> getMyVenues() {
        User user = userService.getLoggedUser();
        List<Venue> venues = venueRepository.findByUser(user);

        List<VenueDto> venueDtos = new ArrayList<>();

        venues.forEach(venue -> venueDtos.add(modelMapper.map(venue, VenueDto.class)));

        return venueDtos;
    }
}
