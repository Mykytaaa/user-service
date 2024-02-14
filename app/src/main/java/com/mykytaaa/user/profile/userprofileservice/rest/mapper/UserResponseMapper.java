package com.mykytaaa.user.profile.userprofileservice.rest.mapper;

import com.mykytaaa.user.profile.userprofileservice.entity.User;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    /**
     * Converts a User object to a UserResponseDto object, applying specified mappings.
     *
     * @param user The User object to be converted to UserResponseDto.
     * @return The UserResponseDto object created from the provided User.
     */
    @Mapping(target = "userDetailsResponseDto", source = "userDetails")
    UserResponseDto toUserResponseDto(User user);
}
