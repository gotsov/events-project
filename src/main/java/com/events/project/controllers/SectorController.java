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

//    @GetMapping("/{id}")
//    public ResponseEntity<VenueDto> getById(@PathVariable Long id) {
//        Optional<VenueDto> venueDto = venueService.getById(id);
//
//        if (venueDto.isPresent()) {
//            return new ResponseEntity<>(venueDto.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<VenueDto>> getAll() {
//        List<VenueDto> venueDtos = venueService.getAll();
//
//        return new ResponseEntity<>(venueDtos, HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<VenueDto> update(@PathVariable Long id, @RequestBody VenueDto updatedVenue) {
//        VenueDto venueDto = venueService.update(id, updatedVenue);
//
//        if (venueDto != null) {
//            return new ResponseEntity<>(venueDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
//        venueService.delete(id);
//        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
//    }
}
