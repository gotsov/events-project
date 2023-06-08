package com.events.project.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TicketFullInfoDto {
    private Long id;
    private EventTicketDto event;
    private SectorDto sector;
    private LocalDateTime timeBought;
    private UserInfoDto user;
}
