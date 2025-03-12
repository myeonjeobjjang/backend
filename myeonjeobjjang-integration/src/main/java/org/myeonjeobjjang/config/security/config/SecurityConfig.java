package org.myeonjeobjjang.config.security.config;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.config.security.jwt.JwtFilter;
import org.myeonjeobjjang.config.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .csrf(CsrfConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(FormLoginConfigurer::disable)
            .httpBasic(HttpBasicConfigurer::disable)
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(
                    "/api/members/login",
                    "/api/members/sign-up"
                ).permitAll()
                .requestMatchers("/api/members/deny").denyAll()
                .requestMatchers("/api/company", "/api/company/**").hasAnyRole("COMPANY", "ADMIN")
                .requestMatchers("/api/admin", "/api/admin/**").hasAnyRole("ADMIN")
                .requestMatchers("**").authenticated()
            );
        return http.build();

    }
}
