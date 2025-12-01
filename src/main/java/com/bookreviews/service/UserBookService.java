package com.bookreviews.service;

import com.bookreviews.dto.CreateUserBookRequest;
import com.bookreviews.dto.UserBookDTO;
import com.bookreviews.dto.UserBookPatchDTO;
import com.bookreviews.dto.UserPatchDTO;
import com.bookreviews.entity.Book;
import com.bookreviews.entity.ReadStatus;
import com.bookreviews.entity.User;
import com.bookreviews.entity.UserBook;
import com.bookreviews.mapper.UserBookMapper;
import com.bookreviews.repository.BookRepository;
import com.bookreviews.repository.UserBookRepository;
import com.bookreviews.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserBookMapper userBookMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserBookService(UserBookRepository userBookRepository,
                           UserBookMapper userBookMapper, UserRepository userRepository,
                           BookRepository bookRepository) {
        this.userBookRepository = userBookRepository;
        this.userBookMapper = userBookMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public UserBookDTO getOneDTO(Long id) {
        return userBookMapper.toDto(userBookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserBook " + id + " not found")));
    }

    @Transactional
    public UserBookDTO create(CreateUserBookRequest request, Long userId) {

        if (!userBookRepository.existsByUserIdAndBookId(request.bookId(), userId)) {
            throw new IllegalArgumentException("This book is already in the user's library");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        UserBook created = new UserBook();
        created.setPercentRead(request.percentRead());
        created.setReadStatus(request.readStatus());
        created.setUser(user);
        created.setBook(book);

        return userBookMapper.toDto(userBookRepository.save(created));

    }

    @Transactional
    public UserBookDTO updatePartial(Long id, UserBookPatchDTO patchDTO) {

        UserBook existing = userBookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserBook not found"));

        if (patchDTO.percentRead() < 0 || patchDTO.percentRead() > 100) {
            throw new IllegalArgumentException("Percent read of user book must be between 0 and 100%");
        }

        existing.setReadStatus(patchDTO.readStatus());
        existing.setPercentRead(patchDTO.percentRead());

        return userBookMapper.toDto(userBookRepository.save(existing));

    }

}
