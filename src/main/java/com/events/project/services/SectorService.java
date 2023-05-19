package com.events.project.services;

import com.events.project.models.dtos.SectorDto;

import java.util.List;
import java.util.Optional;

public interface SectorService {
    void add(SectorDto sectorDto, Long venueId);

    List<SectorDto> getAll();

    Optional<SectorDto> getById(Long id);

    SectorDto update(Long id, SectorDto sectorDto);

    void delete(Long id);
}
