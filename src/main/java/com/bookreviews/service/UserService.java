package com.bookreviews.service;

import com.bookreviews.dto.UserDTO;
import com.bookreviews.dto.UserPatchDTO;
import com.bookreviews.entity.Genre;
import com.bookreviews.entity.User;
import com.bookreviews.mapper.UserMapper;
import com.bookreviews.repository.GenreRepository;
import com.bookreviews.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final UserMapper um;

    public UserService(UserRepository userRepository, GenreRepository genreRepository, UserMapper um) {
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.um = um;
    }

    public UserDTO getOneDto(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

        return um.toDTO(user);

    }

    @Transactional
    public UserDTO updatePartial(Long id, UserPatchDTO patch) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

        if (patch.email() != null && userRepository.existsByEmailIgnoreCase(patch.email())) {
            throw new IllegalArgumentException("A user already exists by this email");
        } else {
            existing.setEmail(patch.email());
        }
        if (patch.username() != null && userRepository.existsByUsernameIgnoreCase(patch.username())) {
            throw new IllegalArgumentException("A user already exists by this username");
        } else {
            existing.setUsername(patch.username());
        }

        if (patch.realName() != null && !patch.realName().isBlank()) {
            existing.setRealName(patch.realName());
        }
        if (patch.birthDate() != null && !patch.birthDate().isBlank()) {
            existing.setBirthDate(patch.birthDate());
        }

        if (patch.userGenreIds() != null) {
            Set<Genre> genres = genreRepository.findByIdIn(patch.userGenreIds());
            if (genres.size() != patch.userGenreIds().size()) {
                throw new EntityNotFoundException("One or more genreIds do not exist.");
            } else {
                existing.setUserGenres(genres);
            }
        }

        return um.toDTO(userRepository.save(existing));

    }

    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

        user.getUserBooks().clear();
        user.getUserGenres().clear();

        userRepository.delete(user);

    }


}
