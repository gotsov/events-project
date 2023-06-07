package com.events.project.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sectors")
public class Sector extends BaseEntity {
    @Column
    private String name;

    @Column
    private Double price;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(mappedBy = "sector")
    private List<Ticket> tickets;

    @Column
    private int numberOfTickets;
}
