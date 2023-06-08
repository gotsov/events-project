package com.events.project.repositories;

import com.events.project.models.entities.Event;
import com.events.project.models.entities.Sector;
import com.events.project.models.entities.Ticket;
import com.events.project.models.entities.User;
import com.events.project.models.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByEvent(Event event);

    List<Ticket> findAllByEventAndSectorAndStatus(Event event, Sector sector, TicketStatus status);

    List<Ticket> findAllByUser(User user);
}
