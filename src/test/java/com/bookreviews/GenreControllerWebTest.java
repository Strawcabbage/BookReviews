package com.bookreviews;

import com.bookreviews.controller.GenreController;
import com.bookreviews.dto.CreateGenreRequest;
import com.bookreviews.entity.Genre;
import com.bookreviews.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
class GenreControllerWebTest {

    @Autowired MockMvc mvc;
    @MockBean GenreService genreService;

    @Test
    @WithMockUser
    void list_genres_200() throws Exception {
        when(genreService.getAll()).thenReturn(List.of(new Genre()));
        mvc.perform(get("/api/genres"))
                .andExpect(status().isOk());
        verify(genreService).getAll();
    }

    @Test
    @WithMockUser
    void get_genre_by_id_200() throws Exception {
        when(genreService.getOne(1L)).thenReturn(new Genre());
        mvc.perform(get("/api/genres/{id}", 1))
                .andExpect(status().isOk());
        verify(genreService).getOne(1L);
    }

    @Test
    @WithMockUser
    void create_genre_201_with_location() throws Exception {
        Genre created = new Genre();
        var idField = Genre.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(created, 42L);

        when(genreService.create("Fantasy")).thenReturn(created);

        String body = """
        { "name": "Fantasy" }
        """;
        mvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/genres/42")));

        verify(genreService).create("Fantasy");
    }
}
