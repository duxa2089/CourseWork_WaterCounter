package com.corsework.watercounter.utils.jwt.service;

import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface TokenExtractor {

    Optional<String> extract(HttpServletRequest request) throws AccessDeniedException;
}
