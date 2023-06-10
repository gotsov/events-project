package com.events.project.controllers;

import com.events.project.models.dtos.ReportDto;
import com.events.project.models.dtos.SectorDto;
import com.events.project.models.entities.User;
import com.events.project.services.ReportService;
import com.events.project.services.SectorService;
import com.events.project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> fileReport(@RequestParam("reportedUserId") Long reportedUserId,
                                             @RequestBody String comment) {
        User repotingUser = userService.getLoggedUser();
        reportService.fileReport(repotingUser, reportedUserId, comment);

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully reported user: " + reportedUserId);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReportDto>> getUserReports(@PathVariable Long id) {
        List<ReportDto> reportDtos = reportService.getUserReports(id);

        return ResponseEntity.status(HttpStatus.OK).body(reportDtos);
    }
}
