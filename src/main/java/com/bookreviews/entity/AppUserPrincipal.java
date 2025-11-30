package com.bookreviews.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class AppUserPrincipal implements OidcUser {

    private final OidcUser delegate;

    @Getter
    private final Long userId;

    private final Boolean admin;

    public AppUserPrincipal(OidcUser delegate, Long userId, Boolean admin) {
        this.delegate = delegate;
        this.userId = userId;
        this.admin = Boolean.TRUE.equals(admin);
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> base = new ArrayList<>(delegate.getAuthorities());

        base.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (admin) {
            base.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return base;
    }

    @Override
    public Map<String, Object> getClaims() {
        return delegate.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return delegate.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return delegate.getIdToken();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return delegate.getAttributes();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
