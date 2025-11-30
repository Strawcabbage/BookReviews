package com.bookreviews.repository;

import com.bookreviews.entity.User;
import com.bookreviews.entity.UserSummaryList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> findByUsername(String username);

    Optional<User> findByAuth0Id(String auth0Id);

    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);

    @Query(value = """
        select u.id as id,
               u.email as email,
               u.username as username,
               size(u.userGenres) as genreCount,
               size(u.userBooks) as userBookCount
        from User u
    """)
    Page<UserSummaryList> ListWithSummary(Pageable pageable);



}
