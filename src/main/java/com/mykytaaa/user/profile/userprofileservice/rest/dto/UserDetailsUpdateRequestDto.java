package com.mykytaaa.user.profile.userprofileservice.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The UserDetailsUpdateRequestDto class represents the Data Transfer Object (DTO) for updating user details.
 * It includes fields for the user details identifier, phone number, and Telegram ID.
 */
@Data
@AllArgsConstructor
public class UserDetailsUpdateRequestDto {

    /**
     * The unique identifier of the user details.
     */
    @NotNull
    private long id;

    /**
     * The phone number of the user. Should start with a '+' followed by 1 to 14 digits.
     */
    @NotNull
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number does not match pattern")
    private String phoneNumber;

    /**
     * The Telegram ID of the user. Should start with '@' and contain 5 to 32 alphanumeric characters or underscores.
     */
    @NotNull
    @Pattern(regexp = "^@[a-zA-Z0-9_]{5,32}$", message = "Telegram id does not match pattern")
    private String telegramId;
}
