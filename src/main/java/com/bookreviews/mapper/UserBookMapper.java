package com.bookreviews.mapper;

import com.bookreviews.dto.UserBookDTO;
import com.bookreviews.entity.UserBook;
import org.springframework.stereotype.Component;

@Component
public class UserBookMapper {

    public UserBookDTO toDto(UserBook userBook) {

        return new UserBookDTO(
                userBook.getReadStatus(),
                userBook.getPercentRead(),
                userBook.getBook(),
                userBook.getUser()
        );

    }

}
