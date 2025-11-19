package com.bookreviews.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MeController {

    @GetMapping("/me")
    public Object me(@AuthenticationPrincipal OidcUser principal) {
        return principal != null ? principal.getClaims() : "No user";
    }
}
