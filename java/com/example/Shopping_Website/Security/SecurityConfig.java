package com.example.Shopping_Website.Security;
import com.example.Shopping_Website.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider provider;
    private final JwtAuthenticationFilter filter;
    private final JwtService service;
    private final UserRepository repository;
    private final LogoutHandler handler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("*"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                }))
                    .authorizeHttpRequests(request -> request
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/delete/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(provider)
                    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                    .logout(logout -> logout.logoutUrl("/api/v1/auth/logout").addLogoutHandler(handler).logoutSuccessHandler((request, response, authentication) -> {
                        SecurityContextHolder.clearContext();   // so tells spring user is no longer authenticated and has no permission for secure endpoints
                    }));
        return http.build();
    }
}



/*
TO DO
- Logout Service
 */


