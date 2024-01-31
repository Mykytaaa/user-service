package com.mykytaaa.user.profile.userprofileservice.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserDetailsCreateRequestDto(
        @Schema(description = "The phone number of the user. Should start with a '+' followed by 1 to 14 digits.",
        example = "+14253211115")
        @NotNull
        @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number does not match pattern")
        String phoneNumber,
        @Schema(description = "The Telegram ID of the user. "
                + "Should start with '@' and contain 5 to 32 alphanumeric characters or underscores.",
        example = "@example_User_telegram_id")
        @NotNull
        @Pattern(regexp = "^@[a-zA-Z0-9_]{5,32}$", message = "Telegram id does not match pattern")
        String telegramId
) {
}
