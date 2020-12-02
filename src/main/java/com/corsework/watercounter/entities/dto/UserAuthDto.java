package com.corsework.watercounter.entities.dto;

import com.corsework.watercounter.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserAuthDto {

    private String username;

    private String password;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private UserRole role;
}
