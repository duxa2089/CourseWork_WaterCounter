package com.corsework.watercounter.utils.jwt;

import com.corsework.watercounter.utils.jwt.service.TokenExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtHeaderAuthenticationFilter extends OncePerRequestFilter {

    private final TokenExtractor tokenExtractor;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtDeniedEntryPoint jwtDeniedEntryPoint;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        Optional<String> token;
        try {
            token = tokenExtractor.extract(request);

        } catch (AccessDeniedException e) {
            log.error("Auth failed", e);
            SecurityContextHolder.clearContext();
            jwtDeniedEntryPoint.handle(request, response, e);
            return;
        }

        if (token.isPresent()) {
            try {
                Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken(token.get()));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (AuthenticationException e) {
                log.error("Auth failed", e);
                SecurityContextHolder.clearContext();
                authenticationEntryPoint.commence(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
