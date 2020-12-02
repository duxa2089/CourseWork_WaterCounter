package com.corsework.watercounter.utils.jwt.dto;

import com.corsework.watercounter.utils.jwt.jwt_enum.JwtTokenStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyTokenDto {

    private String token;

    private String creationDate;

    private String expiredDate;

    private JwtTokenStatus status;

    private boolean validity;

    public VerifyTokenDto(String token, String creationDate, JwtTokenStatus status, boolean verify) {
        this.token = token;
        this.creationDate = creationDate;
        this.status = status;
        this.validity = verify;
    }
}
