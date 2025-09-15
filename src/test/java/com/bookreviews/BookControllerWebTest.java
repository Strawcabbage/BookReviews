package com.bookreviews;


import com.bookreviews.controller.BookController;
import com.bookreviews.dto.BookDTO;
import com.bookreviews.dto.BookPatchDTO;
import com.bookreviews.dto.CreateBookRequest;
import com.bookreviews.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerWebTest {

    @Autowired MockMvc mvc;

    @MockBean BookService bookService;

    @Test
    @WithMockUser // Security requires auth for any request
    void list_books_uses_summary_view_by_default() throws Exception {
        var page = new PageImpl<BookDTO>(List.of(), PageRequest.of(0, 20), 0);
        when(bookService.listWithAggregatesAsDto(any())).thenReturn(page);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk());

        verify(bookService).listWithAggregatesAsDto(any());
        verify(bookService, never()).listRawAsDto(any());
    }

    @Test
    @WithMockUser
    void list_books_raw_view_when_requested() throws Exception {
        var page = new PageImpl<BookDTO>(List.of(), PageRequest.of(0, 20), 0);
        when(bookService.listRawAsDto(any())).thenReturn(page);

        mvc.perform(get("/api/books").param("view", "raw"))
                .andExpect(status().isOk());

        verify(bookService).listRawAsDto(any());
        verify(bookService, never()).listWithAggregatesAsDto(any());
    }

    @Test
    @WithMockUser
    void get_one_book_calls_service() throws Exception {
        when(bookService.getOneDto(1L)).thenReturn(mock(BookDTO.class));

        mvc.perform(get("/api/books/{id}", 1))
                .andExpect(status().isOk());

        verify(bookService).getOneDto(1L);
    }

    @Test
    @WithMockUser
    void create_book_calls_service_and_returns_ok() throws Exception {
        when(bookService.create(any(CreateBookRequest.class))).thenAnswer(inv -> {
            return new com.bookreviews.entity.Book() {{ setId(7L); }};
        });
        when(bookService.getOneDto(7L)).thenReturn(mock(BookDTO.class));

        String body = """
        {
          "name": "Pragmatic Programmer",
          "author": "Hunt & Thomas",
          "genreIds": [1,2]
        }
        """;

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(bookService).create(any(CreateBookRequest.class));
        verify(bookService).getOneDto(7L);
    }

    @Test
    @WithMockUser
    void patch_book_calls_service() throws Exception {
        when(bookService.updatePartial(eq(5L), any(BookPatchDTO.class)))
                .thenReturn(new com.bookreviews.entity.Book() {{ setId(5L); }});
        when(bookService.getOneDto(5L)).thenReturn(mock(BookDTO.class));

        String body = """
        { "name": "Refactoring", "author": "Martin Fowler" }
        """;

        mvc.perform(patch("/api/books/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(bookService).updatePartial(eq(5L), any(BookPatchDTO.class));
        verify(bookService).getOneDto(5L);
    }

    @Test
    @WithMockUser
    void delete_book_returns_204() throws Exception {
        mvc.perform(delete("/api/books/{id}", 9))
                .andExpect(status().isNoContent());

        verify(bookService).delete(9L);
    }
}