package com.corsework.watercounter.utils.jwt.service;

import com.corsework.watercounter.utils.exceptions.TokenCreationException;
import com.corsework.watercounter.utils.exceptions.TokenParsingException;
import com.corsework.watercounter.utils.jwt.dto.UserDetailsDto;
import com.corsework.watercounter.utils.jwt.dto.VerifyTokenDto;
import com.corsework.watercounter.utils.jwt.jwt_enum.JwtRole;
import com.corsework.watercounter.utils.jwt.jwt_enum.JwtTokenStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public interface JwtService {

    Optional<String> generateJwtToken(UserDetailsDto userDetails) throws TokenCreationException;

    boolean verifyJwtToken(String token) throws TokenParsingException;

    Optional<UserDetailsDto> getClaimsUserDetailsJwtToken(String token) throws TokenParsingException;

    OffsetDateTime getTokenCreationDate(String token) throws TokenParsingException;

    OffsetDateTime getTokenExpiredDate(String token) throws TokenParsingException;

    String getUserNameToken(String token) throws TokenParsingException;

    JwtTokenStatus getTokenStatus(String token) throws TokenParsingException;

    JwtRole getTokenRole(String token) throws TokenParsingException;

    VerifyTokenDto getTokenVerifyDto(String token) throws TokenParsingException;
}