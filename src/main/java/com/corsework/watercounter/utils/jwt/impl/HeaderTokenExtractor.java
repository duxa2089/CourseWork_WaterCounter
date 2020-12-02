package com.corsework.watercounter.utils.jwt.impl;

import com.corsework.watercounter.utils.jwt.service.TokenExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class HeaderTokenExtractor implements TokenExtractor {

    private final String headerName;

    private final String prefix;

    private static final String BEARER_AUTH_PREFIX = "bearer ";

    @Override
    public Optional<String> extract(HttpServletRequest request) {

        String tmpHeader;
        String tmpPrefix;

        if (headerName.isEmpty()) {
            tmpHeader = HttpHeaders.AUTHORIZATION;
            tmpPrefix = BEARER_AUTH_PREFIX;
        } else {
            tmpHeader = headerName;
            tmpPrefix = prefix;
        }

        Optional<String> authHeader = Optional.ofNullable(request.getHeader(tmpHeader)).or(Optional::empty);
        log.info("Bearer auth");

        return authHeader.isPresent() && authHeader.get().startsWith(tmpPrefix) ?
                Optional.of(authHeader.get().substring(7)) :
                Optional.empty();
    }
}
