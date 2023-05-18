package com.events.project.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venues")
public class Venue extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column
    private String address;

    @Column
    private String description;

    @OneToMany(mappedBy = "venue")
    private List<Event> events;

    @OneToMany(mappedBy = "venue")
    private List<Sector> sectors;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
