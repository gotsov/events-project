package com.events.project.services;

import com.events.project.models.dtos.ReportDto;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.entities.User;

import java.util.List;

public interface ReportService {

    void fileReport(User reportingUser, Long reportedUserId, String comment);

    List<ReportDto> getUserReports(Long id);
}
