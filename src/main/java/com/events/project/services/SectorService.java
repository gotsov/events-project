package com.events.project.services;

import com.events.project.models.dtos.SectorDto;

import java.util.List;
import java.util.Optional;

public interface SectorService {
    void add(SectorDto sectorDto, Long venueId);
}
