package com.events.project.repositories;

import com.events.project.models.entities.User;
import com.events.project.models.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByUser(User user);

    Optional<Venue> findByName(String name);
}
