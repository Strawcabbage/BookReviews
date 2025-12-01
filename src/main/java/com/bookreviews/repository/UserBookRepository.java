package com.bookreviews.repository;

import com.bookreviews.entity.Book;
import com.bookreviews.entity.User;
import com.bookreviews.entity.UserBook;
import org.springframework.data.repository.CrudRepository;

public interface UserBookRepository extends CrudRepository<UserBook, Long> {

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

}
