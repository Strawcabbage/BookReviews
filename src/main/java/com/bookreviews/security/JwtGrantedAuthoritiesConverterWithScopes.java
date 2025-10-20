package com.bookreviews.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JwtGrantedAuthoritiesConverterWithScopes /*implements Converter<Jwt, AbstractAuthenticationToken>*/ {
    /*public AbstractAuthenticationToken convert(OAuth2ResourceServerProperties.Jwt jwt) {
        var scopes = ((String) jwt.getClaims().getOrDefault("scope","")).split(" ");
        var roles  = ((Map<?,?>) jwt.getClaims().getOrDefault("https://bookreviews/claims", Map.of()))
                .getOrDefault("roles", List.of());
        var auths = new ArrayList<GrantedAuthority>();
        for (var s : scopes) if (!s.isBlank()) auths.add(new SimpleGrantedAuthority("SCOPE_" + s));
        if (roles instanceof Collection<?> r) r.forEach(x -> auths.add(new SimpleGrantedAuthority("ROLE_" + x)));
        return new JwtAuthenticationToken(jwt, auths);
    }*/
}