package com.bookreviews;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class SecurityIntegrationTest {

    @Autowired MockMvc mvc;

    @Test
    void anonymous_is_401_everywhere() throws Exception {
        mvc.perform(get("/api/genres")).andExpect(status().isUnauthorized());
        mvc.perform(get("/api/books")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser // simulates an authenticated user for httpBasic-protected endpoints
    void authenticated_is_200_on_public_endpoints() throws Exception {
        mvc.perform(get("/api/genres")).andExpect(status().isOk());
        mvc.perform(get("/api/books")).andExpect(status().isOk());
    }
}
