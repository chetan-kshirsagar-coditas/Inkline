package com.mukesh.inkLine.config;

import com.mukesh.inkLine.enums.Roles;
import com.mukesh.inkLine.filter.JwtFilter;
import com.mukesh.inkLine.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    protected static final String[] PUBLIC_URLS = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter) {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(http ->
                http.requestMatchers("/api/v1/author/**").hasRole(Roles.AUTHOR.name())
                        .requestMatchers("/api/v1/admin/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers("/api/v1/editor/**").hasAnyRole(Roles.EDITOR.name(), Roles.ADMIN.name())
                        .requestMatchers("/v1/api/users/**", "/api/v1/common/**").permitAll()
                        .requestMatchers(PUBLIC_URLS).permitAll()
        );
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService userDetailsService) {
        return new ProviderManager(new DaoAuthenticationProvider(userDetailsService));
    }
}
