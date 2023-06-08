package com.events.project.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return Objects.equals(name, sector.name) && Objects.equals(price, sector.price) && Objects.equals(venue, sector.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, venue);
    }
}
