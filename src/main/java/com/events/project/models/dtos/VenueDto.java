package com.events.project.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VenueDto {
    private String name;
    private String city;
    private String address;
    private String description;
    private List<SectorDto> sectors;
}
