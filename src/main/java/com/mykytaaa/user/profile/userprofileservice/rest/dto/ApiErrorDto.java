package com.mykytaaa.user.profile.userprofileservice.rest.dto;

import java.util.List;

public record ApiErrorDto(int status, String message, List<String> details) {
}
