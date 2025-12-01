package com.bookreviews.service;

import com.bookreviews.entity.AppUserPrincipal;
import com.bookreviews.entity.User;
import com.bookreviews.repository.UserRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final OidcUserService delegate = new OidcUserService();
    private final UserRepository userRepository;

    public CustomOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = delegate.loadUser(userRequest);

        String auth0Id = oidcUser.getSubject();
        User user = userRepository
                .findByAuth0Id(auth0Id)
                .orElseGet(() -> createNewUserFrom(oidcUser));

        return new AppUserPrincipal(oidcUser, user.getId(), user.getAdmin());

    }

    private User createNewUserFrom(OidcUser oidcUser) {
        User u = new User();
        u.setAuth0Id(oidcUser.getSubject());
        u.setEmail(oidcUser.getEmail());
        u.setRealName(oidcUser.getFullName());
        u.setAdmin(false);

        String username = oidcUser.getPreferredUsername();

        if (username == null || username.isBlank()) {
            Object nickname = oidcUser.getClaims().get("nickname");
            if (nickname != null && !nickname.toString().isBlank()) {
                username = nickname.toString();
            } else if (u.getEmail() != null && u.getEmail().contains("@")) {
                username = u.getEmail().substring(0, u.getEmail().indexOf('@'));
            } else {
                username = "user-" + oidcUser.getSubject();
            }
        }

        int suffix = 1;
        String base = username;
        while (userRepository.existsByUsername(username)) {
            username = base + "_" + suffix++;
        }

        u.setUsername(username);

        return userRepository.save(u);
    }

}
