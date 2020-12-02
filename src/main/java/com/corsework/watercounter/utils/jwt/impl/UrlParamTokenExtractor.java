package com.corsework.watercounter.utils.jwt.impl;

import com.corsework.watercounter.utils.jwt.service.TokenExtractor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
public class UrlParamTokenExtractor implements TokenExtractor {

    @Override
    public Optional<String> extract(HttpServletRequest request) {

        Optional<String> token = Optional
                .ofNullable(request.getParameter("token"))
                .or(Optional::empty);
        log.info("Url auth token");

        return token.isPresent() ? token : Optional.empty();
    }
}
