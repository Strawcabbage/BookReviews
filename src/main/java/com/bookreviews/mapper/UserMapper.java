package com.bookreviews.mapper;

import com.bookreviews.dto.UserDTO;
import com.bookreviews.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User u) {
        return new UserDTO(
                u.getEmail(),
                u.getUsername(),
                u.getUserGenres(),
                u.getUserBooks(),
                u.getAdmin()
        );
    }

}
