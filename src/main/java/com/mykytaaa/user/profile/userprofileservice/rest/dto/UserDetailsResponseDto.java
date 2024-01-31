package com.mykytaaa.user.profile.userprofileservice.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


/**
 * The UserDetailsResponseDto class represents the Data Transfer Object (DTO)
 * for presenting user details in the API responses.
 * It includes information such as the user details identifier,
 * phone number, Telegram ID, creation timestamp, and update timestamp.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponseDto {

    /**
     * The unique identifier of the user details.
     */
    @Schema(description = "The unique identifier of the user details.")
    private long id;

    /**
     * The phone number of the user. Should start with a '+' followed by 1 to 14 digits.
     */
    @Schema(description = "The phone number of the user. Should start with a '+' followed by 1 to 14 digits.")
    private String phoneNumber;

    /**
     * The Telegram ID of the user. Should start with '@' and contain 5 to 32 alphanumeric characters or underscores.
     */
    @Schema(description = "The Telegram ID of the user. Should start with '@' "
            + "and contain 5 to 32 alphanumeric characters or underscores.")
    private String telegramId;

    /**
     * Timestamp indicating the date and time when the user details was created. The format follows the ISO8601.
     */
    @Schema(description = "Timestamp indicating the date and time "
            + "when the user details was created. The format follows the ISO 8601 standard.",
            example = "1970-01-01T00:00:00")
    private Instant createdAt;

    /**
     * Timestamp indicating the date and time when the user details was updated.
     * The format follows the ISO 8601 standard.
     */
    @Schema(description = "Timestamp indicating the date and time "
            + "when the user details was updated. The format follows the ISO 8601 standard.",
            example = "1970-01-01T00:00:00")
    private Instant updatedAt;

    /**
     * Constructor to initialize  with the specified identifier, phone number, and telegramID.
     *
     * @param id          The unique identifier of the user details.
     * @param phoneNumber The phone number of the user.
     * @param telegramId   The Telegram ID of the user.
     */
    public UserDetailsResponseDto(final long id, final String phoneNumber, final String telegramId) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.telegramId = telegramId;
    }
}
