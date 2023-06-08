package com.events.project.models.dtos;

import com.events.project.models.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
