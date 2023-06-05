package com.events.project.controllers;

import com.events.project.models.dtos.SectorDto;
import com.events.project.services.SectorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sectors")
@CrossOrigin
@AllArgsConstructor
public class SectorController {
    private final SectorService sectorService;

    @PostMapping
    public ResponseEntity<String> add(@RequestParam("venueId") Long venueId, @RequestBody SectorDto sectorDto) {
        sectorService.add(sectorDto, venueId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added sector: " + sectorDto.getName());
    }
}
