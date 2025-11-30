package com.bookreviews.controller;

import com.bookreviews.dto.CreateReviewRequest;
import com.bookreviews.dto.ReviewDTO;
import com.bookreviews.dto.ReviewPatchDTO;
import com.bookreviews.entity.Book;
import com.bookreviews.entity.Review;
import com.bookreviews.service.ReviewService;
import com.bookreviews.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<ReviewDTO> create(@Valid @RequestBody CreateReviewRequest review) {

        Review created = reviewService.create(review);
        return ResponseEntity.ok(reviewService.getOneDto(created.getId()));

    }

    @PatchMapping("/{id}")
    public ReviewDTO patch(@PathVariable Long id, @RequestBody ReviewPatchDTO dto) {
        return reviewService.updatePartial(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == principal.userId or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*

    @Get for admin review (page/list view and single entry view)

    @Post for creating

    @Patch for updating

    @Get for user's reviews view (page/list view and single entry view)

    @Get for book's reviews view (page/list view and single entry view)

     */

}
