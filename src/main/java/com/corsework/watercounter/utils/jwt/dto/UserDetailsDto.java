package com.corsework.watercounter.utils.jwt.dto;

import com.corsework.watercounter.utils.jwt.jwt_enum.JwtRole;
import com.corsework.watercounter.utils.jwt.jwt_enum.JwtTokenStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserDetailsDto {

    private String username;

    private OffsetDateTime creationDate;

    private OffsetDateTime expiredDate;

    private JwtRole role;

    private JwtTokenStatus status;

    public UserDetailsDto(String username, OffsetDateTime creationDate, JwtRole role, JwtTokenStatus status) {
        this.username = username;
        this.creationDate = creationDate;
        this.status = status;
        this.role = role;
    }

    public UserDetailsDto(String username, OffsetDateTime creationDate, OffsetDateTime expiredDate, JwtRole role) {
        this.username = username;
        this.creationDate = creationDate;
        this.expiredDate = expiredDate;
        this.role = role;
    }
}
