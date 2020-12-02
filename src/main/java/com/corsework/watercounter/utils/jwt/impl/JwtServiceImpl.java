package com.corsework.watercounter.utils.jwt.impl;

import com.corsework.watercounter.utils.exceptions.TokenCreationException;
import com.corsework.watercounter.utils.exceptions.TokenParsingException;
import com.corsework.watercounter.utils.jwt.dto.UserDetailsDto;
import com.corsework.watercounter.utils.jwt.dto.VerifyTokenDto;
import com.corsework.watercounter.utils.jwt.jwt_enum.JwtRole;
import com.corsework.watercounter.utils.jwt.jwt_enum.JwtTokenStatus;
import com.corsework.watercounter.utils.jwt.service.JwtService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    public static final String USERNAME = "USERNAME";

    public static final String CREATION_DATE = "CREATION_DATE";

    public static final String EXPIRED_DATE = "EXPIRED_DATE";

    public static final String ROLE = "ROLE";

    public static final String STATUS = "STATUS";

    @Value("${jwt.secret}")
    private String secret;;

    @Override
    public Optional<String> generateJwtToken(UserDetailsDto userDetails) throws TokenCreationException {

        Optional<String> token;

        try {
            Map<String, Object> claims = new HashMap();

            claims.put(USERNAME, userDetails.getUsername());
            claims.put(CREATION_DATE, userDetails.getCreationDate().toString());
            if (userDetails.getExpiredDate() == null) {
                claims.put(STATUS, JwtTokenStatus.NON_EXPIRE);
            } else {
                claims.put(EXPIRED_DATE, userDetails.getExpiredDate().toString());
                claims.put(STATUS, JwtTokenStatus.WITH_EXPIRE);
            }
            claims.put(ROLE, userDetails.getRole());

            log.info("START CREATION TOKEN!");
            token = Optional.of(Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact());

        } catch (NullPointerException e) {

            log.error("ERROR CREATION TOKEN!", e);
            throw new TokenCreationException(e);
        }

        return token;
    }

    @Override
    public boolean verifyJwtToken(String token) throws TokenParsingException {

        Optional<UserDetailsDto> userDetails = getClaimsUserDetailsJwtToken(token);

        if (userDetails.isPresent()) {

            switch (userDetails.get().getStatus()) {
                case NON_EXPIRE:
                    return true;
                case WITH_EXPIRE:
                    return userDetails.get().getExpiredDate().isAfter(OffsetDateTime.now());
            }
        }

        return false;
    }

    @Override
    public Optional<UserDetailsDto> getClaimsUserDetailsJwtToken(String token) throws TokenParsingException {

        Optional<UserDetailsDto> userDetailsDto;

        try {
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            userDetailsDto = Optional.of(
                    new UserDetailsDto(
                        claims.get(USERNAME).toString(),
                        OffsetDateTime.parse(claims.get(CREATION_DATE).toString(), DateTimeFormatter.ISO_DATE_TIME),
                        JwtRole.valueOf(claims.get(ROLE).toString()),
                        JwtTokenStatus.valueOf(claims.get(STATUS).toString())
                    )
            );

            if (claims.get(EXPIRED_DATE) != null) {
                userDetailsDto
                        .get()
                        .setExpiredDate(
                                OffsetDateTime.parse(claims.get(EXPIRED_DATE).toString(), DateTimeFormatter.ISO_DATE_TIME)
                        );
            }

        } catch (UnsupportedJwtException |
                MalformedJwtException |
                SignatureException |
                IllegalArgumentException |
                DateTimeParseException |
                NullPointerException e) {

            log.error("ERROR PARSE TOKEN!", e);
            throw new TokenParsingException(e);
        }

        return userDetailsDto;
    }

    @Override
    public OffsetDateTime getTokenCreationDate(String token) throws TokenParsingException {
        return getClaimsUserDetailsJwtToken(token).orElseThrow(() -> {throw new TokenParsingException("Error parsing token creation date");}).getCreationDate();
    }

    @Override
    public OffsetDateTime getTokenExpiredDate(String token) throws TokenParsingException {
        return getClaimsUserDetailsJwtToken(token).orElseThrow(() -> {throw new TokenParsingException("Error parsing token expired date");}).getExpiredDate();
    }

    @Override
    public String getUserNameToken(String token) throws TokenParsingException {
        return getClaimsUserDetailsJwtToken(token).orElseThrow(() -> {throw new TokenParsingException("Error parsing token username");}).getUsername();
    }

    @Override
    public JwtTokenStatus getTokenStatus(String token) throws TokenParsingException {
        return getClaimsUserDetailsJwtToken(token).orElseThrow(() -> {throw new TokenParsingException("Error parsing token status");}).getStatus();
    }

    @Override
    public JwtRole getTokenRole(String token) throws TokenParsingException {
        return getClaimsUserDetailsJwtToken(token).orElseThrow(() -> {throw new TokenParsingException("Error parsing token role");}).getRole();
    }

    @Override
    public VerifyTokenDto getTokenVerifyDto(String token) throws TokenParsingException {

        VerifyTokenDto dto = new VerifyTokenDto(
                token,
                getTokenCreationDate(token).toString(),
                getTokenStatus(token),
                verifyJwtToken(token)
        );

        if (getTokenStatus(token) == JwtTokenStatus.NON_EXPIRE) {
            dto.setExpiredDate(null);

        } else {
            dto.setExpiredDate(getTokenExpiredDate(token).toString());
        }

        return dto;
    }
}
