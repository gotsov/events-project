package com.events.project.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private String email;
    private String firstName;
    private String lastName;
}
