package com.iprody.user.profile.userprofileservice.service;

import com.iprody.user.profile.userprofileservice.entity.User;
import com.iprody.user.profile.userprofileservice.exception.ResourceNotFoundException;
import com.iprody.user.profile.userprofileservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    /**
     * Uer domain name.
     */
    public static final String USER_DOMAIN_NAME = "User";

    /**
     * Injection of UserRepository.
     */
    private final UserRepository userRepository;

    /**
     * Create a new User or update an existing record in database.
     *
     * @param user to be saved in database.
     * @return User The saved User entity.
     */
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Find an existent User record in database.
     *
     * @param id refers to a record to be found.
     * @return User The found User entity.
     */
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResourceNotFoundException.ErrorMessage.ofId(USER_DOMAIN_NAME, id)));
    }

    /**
     * Find an existent User record in database.
     *
     * @param email refers to a record to be found.
     * @return User The found User entity.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResourceNotFoundException.ErrorMessage.ofMail(USER_DOMAIN_NAME, email)));
    }

    /**
     * Find all existent User records in database.
     *
     * @return List<User> The found List of User entities.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Find all users in the database with pagination.
     *
     * @param pageable the parameter that contains the capacity and page number
     * @return Page<User> a page of Users
     */
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
