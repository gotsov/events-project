package com.events.project.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private LocalDateTime insertTime;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToMany
    private List<Tag> tags;

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
