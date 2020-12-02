package com.corsework.watercounter.utils.jwt;

import com.corsework.watercounter.utils.exceptions.TokenParsingException;
import com.corsework.watercounter.utils.jwt.dto.UserDetailsDto;
import com.corsework.watercounter.utils.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationTokenProvider implements AuthenticationProvider {

    private final static String ROLE_PREFIX = "ROLE_";

    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = authentication.getCredentials().toString();
        final Optional<UserDetailsDto> tokenUser;

        try {
            if (jwtService.verifyJwtToken(token)) {
                tokenUser = jwtService.getClaimsUserDetailsJwtToken(token);

            } else {
                throw new AuthenticationServiceException("Invalid token");
            }

        } catch (TokenParsingException e) {
            log.error("Token is incorrect", e);
            throw new AuthenticationServiceException("Access is denied");
        }

        return new JwtAuthenticationToken(List.of(new SimpleGrantedAuthority(ROLE_PREFIX + tokenUser.get().getRole().name())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
