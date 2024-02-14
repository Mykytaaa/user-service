package com.mykytaaa.user.profile.userprofileservice.repository;

import com.mykytaaa.user.profile.userprofileservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find an existent User record in database.
     *
     * @param email refers to a record to be found.
     * @return User The found User entity.
     */
    Optional<User> findByEmail(String email);
}
