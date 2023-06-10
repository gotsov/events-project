package com.events.project.models.dtos;

import com.events.project.models.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReportDto {
    private Long id;
    private UserInfoDto reportedUser;
    private UserInfoDto reportingUser;
    private LocalDateTime date;
    private String comment;
}
