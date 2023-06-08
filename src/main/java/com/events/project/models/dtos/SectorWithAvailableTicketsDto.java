package com.events.project.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorWithAvailableTicketsDto {
    private Long id;
    private String name;
    private Double price;
    private int numberOfTickets;
    private int numberOfAvailableTickets;
}
