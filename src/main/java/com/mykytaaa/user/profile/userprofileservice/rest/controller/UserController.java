package com.mykytaaa.user.profile.userprofileservice.rest.controller;

import com.mykytaaa.user.profile.userprofileservice.rest.adapter.UserServiceAdapter;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserCreateRequestDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsResponseDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsUpdateRequestDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserResponseDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserUpdateRequestDto;
import com.mykytaaa.user.profile.userprofileservice.rest.validation.annotation.ValidateStrings;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The UserController class represents the RESTful API endpoints for user-related operations.
 * It serves as a controller for creating, updating, and retrieving user information, as well as handling user details.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    /**
     * The UserServiceAdapter instance used to delegate user-related operations.
     */
    private final UserServiceAdapter userServiceAdapter;

    /**
     * Creates a new user along with its related user contact.
     *
     * @param userCreateRequestDto The DTO containing user creation information.
     * @return The created UserResponseDto.
     */
    @Operation(summary = "Create a new user with its related user contact")
    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        return userServiceAdapter.createUser(userCreateRequestDto);
    }

    /**
     * Updates an existing user based on the provided user identifier.
     *
     * @param id                    The unique identifier of the user to be updated.
     * @param userUpdateRequestDto The DTO containing user update information.
     * @return The updated UserResponseDto.
     */
    @Operation(summary = "Update a user")
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable long id,
                                      @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userUpdateRequestDto.setId(id);
        return userServiceAdapter.updateUser(userUpdateRequestDto);
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The UserResponseDto corresponding to the found user.
     */
    @Operation(summary = "Find a user by id")
    @GetMapping("/{id}")
    public UserResponseDto findUserById(@PathVariable long id) {
        return userServiceAdapter.findUserById(id);
    }

    /**
     * Retrieves a list of users with pagination and sorting support.
     *
     * @param pageSize   The number of users per page.
     * @param pageNumber The page number.
     * @param sortBy     The field by which the results should be sorted.
     * @param sortOrder  The sorting order (asc or desc).
     * @return A list of UserResponseDto objects representing the users on the requested page.
     */
    @Operation(summary = "Find users with pagination and sorting")
    @GetMapping
    public List<UserResponseDto> findUsersWithPageable(
            @RequestParam(name = "pageSize", defaultValue = "1") int pageSize,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @ValidateStrings(value = {"id", "firstName", "lastName", "email"})
            @RequestParam(name = "sortBy", defaultValue = "id")
            String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder
    ) {
        return userServiceAdapter.findUsersWithPageable(pageSize, pageNumber, sortBy, sortOrder);
    }

    /**
     * Updates user details based on the provided user contact identifier.
     *
     * @param id                            The unique identifier of the user details to be updated.
     * @param requestDto The DTO containing user details update information.
     * @return The updated UserDetailsResponseDto.
     */
    @Operation(summary = "Update user details")
    @PutMapping("/user-contacts/{id}")
    public UserDetailsResponseDto updateUserDetails(@PathVariable long id,
                                                    @Valid @RequestBody UserDetailsUpdateRequestDto requestDto) {
        requestDto.setId(id);
        return userServiceAdapter.updateUserDetails(requestDto);
    }

    /**
     * Retrieves user details by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The UserDetailsResponseDto corresponding to the found user details.
     */
    @Operation(summary = "Find user details by id")
    @GetMapping("/user-contacts/{id}")
    public UserDetailsResponseDto findUserDetailsById(@PathVariable long id) {
        return userServiceAdapter.findUserDetailsById(id);
    }

    /**
     * Retrieves user details by their user identifier.
     *
     * @param id The user identifier.
     * @return The UserDetailsResponseDto corresponding to the found user details.
     */
    @Operation(summary = "Find user details by user id")
    @GetMapping("/user-contacts/by-user-id/{id}")
    public UserDetailsResponseDto findUserDetailsByUserId(@PathVariable long id) {
        return userServiceAdapter.findUserDetailsByUserId(id);
    }
}
