package com.events.project.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venue")
public class Venue extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column
    private String address;

    @Column
    private String description;
}
