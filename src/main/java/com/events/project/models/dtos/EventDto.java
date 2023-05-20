package com.events.project.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventDto {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private VenueDto venue;
    private List<TagDto> tags;
    private UserInfoDto user;
}
