package com.bookreviews.controller;

import com.bookreviews.dto.BookDTO;
import com.bookreviews.dto.CreateReviewRequest;
import com.bookreviews.dto.UserDTO;
import com.bookreviews.dto.UserPatchDTO;
import com.bookreviews.entity.AppUserPrincipal;
import com.bookreviews.entity.User;
import com.bookreviews.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    @GetMapping()
    public Page<UserDTO> list(
            Pageable pageable,

    ) {

    }
     */

    @GetMapping("/me")
    public UserDTO getOne(@AuthenticationPrincipal AppUserPrincipal me) {
        return userService.getOneDto(me.getUserId());
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getOne(@PathVariable Long id) {
        return userService.getOneDto(id);
    }

    @PatchMapping("/me")
    public UserDTO updatePartial(@AuthenticationPrincipal AppUserPrincipal me, @RequestBody UserPatchDTO patchDTO) {
        return userService.updatePartial(me.getUserId(), patchDTO);
    }

    @PatchMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO adminUpdatePartial(@PathVariable Long id, @RequestBody UserPatchDTO patchDTO) {
        return userService.updatePartial(id, patchDTO);
    }

    @GetMapping("/users")
    public Page<UserDTO>

    /*

    @Post for creating

    @Post for creating admin

    @Patch for protected updating

    @Get for list of users

    @Get for sing User with detailed info

    @Get for protected list of admins

     */

}
