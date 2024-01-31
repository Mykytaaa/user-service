package com.mykytaaa.user.profile.userprofileservice.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequestDto(
        @Schema(description = "The first name of the user")
        @NotNull
        @Size(min = 1, max = 50)
        String firstName,
        @Schema(description = "The last name of the user")
        @NotNull
        @Size(min = 1, max = 50)
        String lastName,
        @Schema(description = "The email of the user")
        @NotNull
        @Email(message = "Email should be valid")
        @Size(max = 255)
        String email,
        @Schema(description = "Details for creating a user")
        @NotNull
        UserDetailsCreateRequestDto userDetailsCreateRequestDto
) {
}
