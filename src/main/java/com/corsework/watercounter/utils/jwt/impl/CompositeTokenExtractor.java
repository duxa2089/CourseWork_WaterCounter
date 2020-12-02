package com.corsework.watercounter.utils.jwt.impl;

import com.corsework.watercounter.utils.jwt.service.TokenExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
public class CompositeTokenExtractor implements TokenExtractor {

    private final TokenExtractor[] extractors;

    public CompositeTokenExtractor(TokenExtractor... extractors) {
        this.extractors = extractors;
    }

    @Override
    public Optional<String> extract(HttpServletRequest request) throws AccessDeniedException {

        for (TokenExtractor extractor : extractors) {

            Optional<String> token = extractor.extract(request);
            if (token.isPresent()) {
                log.info("Composite token");
                return token;
            }
        }

        throw new AccessDeniedException("Access denied");
    }
}
