package com.events.project.repositories;

import com.events.project.models.entities.Report;

import com.events.project.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByReportedUser(User user);
}
