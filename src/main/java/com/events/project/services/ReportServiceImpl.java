package com.events.project.services;

import com.events.project.exceptions.ItemNotFoundException;
import com.events.project.models.dtos.ReportDto;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.entities.Report;
import com.events.project.models.entities.Sector;
import com.events.project.models.entities.User;
import com.events.project.models.entities.Venue;
import com.events.project.repositories.ReportRepository;
import com.events.project.repositories.SectorRepository;
import com.events.project.repositories.UserRepository;
import com.events.project.repositories.VenueRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void fileReport(User reportingUser, Long reportedUserId, String comment) {
        Optional<User> reportedUser = userRepository.findById(reportedUserId);

        if (reportedUser.isPresent()) {
            Report report = Report.builder()
                    .reportingUser(reportingUser)
                    .reportedUser(reportedUser.get())
                    .comment(comment)
                    .date(LocalDateTime.now())
                    .build();

            reportRepository.save(report);
        } else {
            throw new ItemNotFoundException("User " + reportedUserId + " does not exist");
        }
    }

    @Override
    public List<ReportDto> getUserReports(Long id) {
        Optional<User> reportedUser = userRepository.findById(id);
        List<ReportDto> result = new ArrayList<>();

        if (reportedUser.isPresent()) {
            List<Report> reports = reportRepository.findAllByReportedUser(reportedUser.get());

            reports.forEach(report -> result.add(modelMapper.map(report, ReportDto.class)));

            return result;
        }
        else {
            throw new ItemNotFoundException("User " + id + "not found");
        }
    }
}
