package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.entities.Sector;
import com.events.project.models.entities.Venue;
import com.events.project.repositories.SectorRepository;
import com.events.project.repositories.VenueRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SectorServiceImpl implements SectorService {
    private final SectorRepository sectorRepository;
    private final VenueRepository venueRepository;

    private final ModelMapper modelMapper;

    @Override
    public void add(SectorDto sectorDto, Long venueId) {
        Sector sector = modelMapper.map(sectorDto, Sector.class);
        Optional<Venue> venue = venueRepository.findById(venueId);

        if (venue.isPresent()) {
            sector.setVenue(venue.get());
            venueRepository.save(venue.get());
        } else {
            throw new ItemNotFoundException("Venue with id=" + venueId + " not found");
        }

        sectorRepository.save(sector);
    }
}
