package com.events.project.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private Date insertTime;

    @Column
    private String description;
}
