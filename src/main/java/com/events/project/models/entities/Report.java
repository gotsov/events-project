package com.events.project.models.entities;

import com.events.project.models.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reports")
public class Report extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "reported_user_id", nullable = false)
    private User reportedUser;

    @ManyToOne
    @JoinColumn(name = "reporting_user_id", nullable = false)
    private User reportingUser;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime date;
}
