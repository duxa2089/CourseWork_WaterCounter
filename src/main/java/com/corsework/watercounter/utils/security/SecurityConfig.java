package com.corsework.watercounter.utils.security;

import com.corsework.watercounter.utils.jwt.JwtAuthenticationEntryPoint;
import com.corsework.watercounter.utils.jwt.JwtAuthenticationTokenProvider;
import com.corsework.watercounter.utils.jwt.JwtDeniedEntryPoint;
import com.corsework.watercounter.utils.jwt.JwtHeaderAuthenticationFilter;
import com.corsework.watercounter.utils.jwt.impl.CompositeTokenExtractor;
import com.corsework.watercounter.utils.jwt.impl.HeaderTokenExtractor;
import com.corsework.watercounter.utils.jwt.impl.UrlParamTokenExtractor;
import com.corsework.watercounter.utils.jwt.jwt_enum.JwtRole;
import com.corsework.watercounter.utils.jwt.service.JwtService;
import com.corsework.watercounter.utils.jwt.service.TokenExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(ApplicationUserProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTH_PROVIDE_ADMIN_API = "/api/jwt/admin/*";
    private static final String AUTH_PROVIDE_ADMIN_API_PATH = AUTH_PROVIDE_ADMIN_API + "*";
    private static final String AUTH_PROVIDE_USER_API = "/api/jwt/user/*";
    private static final String AUTH_PROVIDE_USER_API_PATH = AUTH_PROVIDE_USER_API + "*";
    private static final String AUTH_PROVIDE_MESSAGE_API = "/api/message/*";
    private static final String AUTH_PROVIDE_MESSAGE_API_PATH = AUTH_PROVIDE_MESSAGE_API + "*";
    private static final String AUTH_PROVIDE_PUSH_API = "/api/push/*";
    private static final String AUTH_PROVIDE_PUSH_API_PATH = AUTH_PROVIDE_PUSH_API + "*";
    private static final String EMPTY_PASSWORD = "";

    private final JwtService jwtService;
    private final JwtDeniedEntryPoint jwtDeniedEntryPoint;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final ApplicationUserProperties applicationUserProperties;

    @Value("${jwt.header}")
    private String headerName;

    @Value("${jwt.prefix}")
    private String prefix;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(jwtAuthenticationTokenProvider())
            .userDetailsService(userDetailsManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .httpBasic()
        .and()
            .authorizeRequests()
            .antMatchers(AUTH_PROVIDE_ADMIN_API_PATH,
                    AUTH_PROVIDE_MESSAGE_API_PATH,
                    AUTH_PROVIDE_PUSH_API_PATH).hasRole(JwtRole.ADMIN.name())
            .antMatchers(AUTH_PROVIDE_USER_API_PATH,
                    AUTH_PROVIDE_MESSAGE_API_PATH,
                    AUTH_PROVIDE_PUSH_API_PATH).hasRole(JwtRole.USER.name())
            .anyRequest().permitAll()
        .and()
            .addFilterBefore(jwtHeaderTokenAuthenticationFilter(), BasicAuthenticationFilter.class)
            .exceptionHandling()
            .accessDeniedHandler(jwtDeniedEntryPoint)
            .authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }

    @Override
    public void configure(WebSecurity web) {

        web.ignoring().requestMatchers(
                new AndRequestMatcher(
                        new NegatedRequestMatcher(new AntPathRequestMatcher(AUTH_PROVIDE_ADMIN_API_PATH)),
                        new NegatedRequestMatcher(new AntPathRequestMatcher(AUTH_PROVIDE_USER_API_PATH))
                )
        );
    }

    @Bean
    public UserDetailsManager userDetailsManager() {

        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        applicationUserProperties.getUsers().forEach((role, user) -> userDetailsManager
                .createUser(User.builder()
                        .username(user.getUsername())
                        .password(EMPTY_PASSWORD).disabled(true)
                        .roles(role.name())
                        .build()));

        return userDetailsManager;
    }

    @Bean
    public TokenExtractor compositeTokenExtractor() {
        return new CompositeTokenExtractor(
                new HeaderTokenExtractor(headerName, prefix),
                new UrlParamTokenExtractor()
        );
    }

    public JwtHeaderAuthenticationFilter jwtHeaderTokenAuthenticationFilter() throws Exception {
        return new JwtHeaderAuthenticationFilter(
                compositeTokenExtractor(),
                authenticationManager(),
                jwtAuthenticationEntryPoint,
                jwtDeniedEntryPoint
        );
    }

    @Bean
    public AuthenticationProvider jwtAuthenticationTokenProvider() {
        return new JwtAuthenticationTokenProvider(jwtService);
    }
}
