package com.househunting.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

        private final JwtAuthenticationFilter JwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf
                                .disable())
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers("/api/v1/auth/**",
                                                                "/api/v1/houses/create",
                                                                "/api/v1/wishlist/**",
                                                                "/api/v1/test/**",
                                                                "/api/v1/getAllHouses/**",
                                                                "/api/v1/forgot-password",
                                                                "/v2/api-docs",
                                                                "/v3/api-docs",
                                                                "/v3/api-docs/**",
                                                                "/swagger-resources",
                                                                "/swagger-resources/**",
                                                                "/configuration/ui",
                                                                "/configuration/security",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(
                                                                SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(JwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
