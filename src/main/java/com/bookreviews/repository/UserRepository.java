package com.bookreviews.repository;

import com.bookreviews.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> findByUsername(String username);

    Optional<User> findByAuth0Id(String auth0Id);

    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);

}
