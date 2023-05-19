package com.events.project.models.dtos;

import com.events.project.models.enums.Role;
import com.events.project.security.PasswordConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class UserEventDto {
    private String email;
    private String firstName;
    private String lastName;
}
