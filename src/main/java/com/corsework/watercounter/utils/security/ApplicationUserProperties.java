package com.corsework.watercounter.utils.security;

import com.corsework.watercounter.utils.UserRole;
import com.corsework.watercounter.utils.jwt.dto.UserBasic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.EnumMap;
import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "application.security.prop")
public class ApplicationUserProperties {

    @NotEmpty
    private Map<UserRole, UserBasic> users = new EnumMap<>(UserRole.class);

}
