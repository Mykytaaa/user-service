package com.mykytaaa.user.profile.service.e2e.util;

import com.mykytaaa.user.profile.service.e2e.generated.model.ApiErrorDto;
import com.mykytaaa.user.profile.service.e2e.generated.model.UserDetailsResponseDto;
import com.mykytaaa.user.profile.service.e2e.generated.model.UserDetailsUpdateDto;
import com.mykytaaa.user.profile.service.e2e.generated.model.UserResponseDto;
import com.mykytaaa.user.profile.service.e2e.generated.model.UserUpdateRequestDto;
import io.cucumber.datatable.DataTable;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ModelMapper {
    private static final String ERROR_DETAILS_SEPARATOR = "\\s*;\\s*";

    public UserResponseDto toResponseDto(DataTable dataTable) {
        final var entry = dataTable.asMap();

        return UserResponseDto.builder()
                .id(Long.valueOf(entry.get("id")))
                .firstName(entry.get("first_name"))
                .lastName(entry.get("last_name"))
                .email(entry.get("email"))
                .userDetailsResponseDto(
                        UserDetailsResponseDto.builder()
                                .id(Long.valueOf(entry.get("userDetails.id")))
                                .phoneNumber(entry.get("userDetails.phone_number"))
                                .telegramId(entry.get("userDetails.telegram_id"))
                                .build()
                ).build();
    }

    public ApiErrorDto toApiErrorDto(DataTable dataTable) {
        final var entry = dataTable.asMap();

        return ApiErrorDto.builder()
                .status(Integer.valueOf(entry.get("status")))
                .message(entry.get("message"))
                .details(toErrorDetailsList(entry))
                .build();
    }

    public List<String> toErrorDetailsList(Map<String, String> map) {
        return Arrays.stream(map.get("details").split(ERROR_DETAILS_SEPARATOR)).toList();
    }

    public UserUpdateRequestDto toUserUpdateRequestDto(DataTable dataTable){
        final var entry = dataTable.asMap();

        return UserUpdateRequestDto.builder()
                .id(Long.valueOf(entry.get("id")))
                .firstName(entry.get("first_name"))
                .lastName(entry.get("last_name"))
                .email(entry.get("email"))
                .userDetailsUpdateDto(UserDetailsUpdateDto.builder()
                        .id(Long.valueOf(entry.get("userDetails.id")))
                        .phoneNumber(entry.get("userDetails.phone_number"))
                        .telegramId(entry.get("userDetails.telegram_id"))
                        .build())
                .build();
    }
}
