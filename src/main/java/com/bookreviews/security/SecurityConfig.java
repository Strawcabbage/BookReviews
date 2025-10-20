package com.bookreviews.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                // allow the H2 console without auth
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated() // adjust to your needs
                )
                // H2 console posts to its own endpoints; skip CSRF for it
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**").disable())
                // H2 UI uses frames; disable frame blocking for the console
                .headers(h -> h.frameOptions(frame -> frame.disable()))
                // (optional) simple login for the rest of the app
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    /*
    @Bean
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())             // stateless API; use CSRF only if cookie sessions
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt
                        .jwtAuthenticationConverter(new JwtGrantedAuthoritiesConverterWithScopes())
                ));
        return http.build();
    }*/

}