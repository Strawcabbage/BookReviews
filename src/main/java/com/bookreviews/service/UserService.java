package com.bookreviews.service;

import com.bookreviews.dto.AdminPatchDTO;
import com.bookreviews.dto.UserDTO;
import com.bookreviews.dto.UserPatchDTO;
import com.bookreviews.entity.Genre;
import com.bookreviews.entity.User;
import com.bookreviews.entity.UserSummaryList;
import com.bookreviews.mapper.UserMapper;
import com.bookreviews.repository.GenreRepository;
import com.bookreviews.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<UserSummaryList> listSummaryPage(Pageable pageable) {
        return userRepository.ListWithSummary(pageable);
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
        if (patch.username() != null && userRepository.existsByUsername(patch.username())) {
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

    @Transactional
    public UserDTO adminUpdatePartial(Long id, AdminPatchDTO adminPatchDTO) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

        if (adminPatchDTO.email() != null && userRepository.existsByEmailIgnoreCase(adminPatchDTO.email())) {
            throw new IllegalArgumentException("A user already exists by this email");
        } else {
            existing.setEmail(adminPatchDTO.email());
        }
        if (adminPatchDTO.username() != null && userRepository.existsByUsername(adminPatchDTO.username())) {
            throw new IllegalArgumentException("A user already exists by this username");
        } else {
            existing.setUsername(adminPatchDTO.username());
        }

        if (adminPatchDTO.realName() != null && !adminPatchDTO.realName().isBlank()) {
            existing.setRealName(adminPatchDTO.realName());
        }
        if (adminPatchDTO.birthDate() != null && !adminPatchDTO.birthDate().isBlank()) {
            existing.setBirthDate(adminPatchDTO.birthDate());
        }

        if (adminPatchDTO.userGenreIds() != null) {
            Set<Genre> genres = genreRepository.findByIdIn(adminPatchDTO.userGenreIds());
            if (genres.size() != adminPatchDTO.userGenreIds().size()) {
                throw new EntityNotFoundException("One or more genreIds do not exist.");
            } else {
                existing.setUserGenres(genres);
            }
        }

        if (adminPatchDTO.admin() != null) {
            existing.setAdmin(adminPatchDTO.admin());
        }

        return um.toDTO(userRepository.save(existing));

    }

    @Transactional
    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

        user.getUserBooks().clear();
        user.getUserGenres().clear();

        userRepository.delete(user);

    }


}
