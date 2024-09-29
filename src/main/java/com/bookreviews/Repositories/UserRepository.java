package com.bookreviews.Repositories;

import com.bookreviews.Models.Genre;
import com.bookreviews.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    public List<User> findByDisplayName(String displayName);

}
