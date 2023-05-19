package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.entities.Sector;
import com.events.project.models.entities.Venue;
import com.events.project.repositories.SectorRepository;
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

    @Override
    public List<SectorDto> getAll() {
        List<Sector> sectors = sectorRepository.findAll();
        List<SectorDto> sectorDtos = new ArrayList<>();

        sectors.forEach(sector -> sectorDtos.add(modelMapper.map(sector, SectorDto.class)));

        return sectorDtos;
    }

    @Override
    public Optional<SectorDto> getById(Long id) {
        Optional<Sector> sector = sectorRepository.findById(id);

        if (sector.isPresent()) {
            SectorDto sectorDto = modelMapper.map(sector.get(), SectorDto.class);
            return Optional.of(sectorDto);
        }

        return Optional.empty();
    }

    @Override
    public SectorDto update(Long id, SectorDto venueDto) {
        Optional<Sector> sector = sectorRepository.findById(id);
        Venue updatedVenue = modelMapper.map(venueDto, Venue.class);

        if (sector.isPresent()) {
            BeanUtils.copyProperties(sector, updatedVenue);

            sectorRepository.save(sector.get());

            return modelMapper.map(sector, SectorDto.class);
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Sector> sector = sectorRepository.findById(id);

        if (sector.isPresent()) {
            sectorRepository.delete(sector.get());
        } else {
            throw new ItemNotFoundException("Venue with id=" + id + " doesn't exist");
        }
    }
}
