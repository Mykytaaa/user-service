package com.iprody.user.profile.userprofileservice.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The UserResponseDto class represents the Data Transfer Object for presenting user information in the API responses.
 * It includes information such as the user identifier, first name, last name, email,
 * creation timestamp, update timestamp, and details for creating a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    /**
     * The unique identifier of the user.
     */
    @Schema(description = "The unique identifier of the user.")
    private long id;

    /**
     * The first name of the user.
     */
    @Schema(description = "The first name of the user")
    private String firstName;

    /**
     * The last name of the user.
     */
    @Schema(description = "The last name of the user")
    private String lastName;

    /**
     * The email of the user.
     */
    @Schema(description = "The email of the user")
    private String email;

    /**
     * Timestamp indicating the date and time when the user was created. The format follows the ISO 8601 standard.
     * Example: "1970-01-01T00:00:00"
     */
    @Schema(description = "Timestamp indicating the date and time when the user was created. "
            + "The format follows the ISO 8601 standard.",
            example = "1970-01-01T00:00:00")
    private Instant createdAt;

    /**
     * Timestamp indicating the date and time when the user was updated.
     * The format follows the ISO 8601 standard.
     */
    @Schema(description = "Timestamp indicating the date and time when the user was updated. "
            + "The format follows the ISO 8601 standard.",
            example = "1970-01-01T00:00:00")
    private Instant updatedAt;

    /**
     * Details for creating a user.
     */
    @Schema(description = "Details for creating a user")
    private UserDetailsResponseDto userDetailsResponseDto;

    /**
     * Constructor to initialize the UserResponseDto with the specified identifier,
     * first name, last name, email, and user details.
     *
     * @param id                       The unique identifier of the user.
     * @param firstName                The first name of the user.
     * @param lastName                 The last name of the user.
     * @param email                    The email of the user.
     * @param userDetailsResponseDto   Details for creating a user.
     */
    public UserResponseDto(final long id, final String firstName, final String lastName, final String email,
                           final UserDetailsResponseDto userDetailsResponseDto) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userDetailsResponseDto = userDetailsResponseDto;
    }
}
