package com.bookreviews.service;

import com.bookreviews.dto.CreateUserRequest;
import com.bookreviews.dto.UserDTO;
import com.bookreviews.entity.User;
import com.bookreviews.mapper.UserMapper;
import com.bookreviews.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper um;

    public UserService(UserRepository userRepository, UserMapper um) {
        this.userRepository = userRepository;
        this.um = um;
    }

    @Transactional
    public UserDTO getOneDto(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found"));

        return um.toDTO(user);

    }




}
