package com.mykytaaa.user.profile.userprofileservice.rest.mapper;

import com.mykytaaa.user.profile.userprofileservice.entity.UserDetails;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDetailsResponseMapper {

    /**
     * Converts a UserDetails object to a UserDetailsResponseDto object.
     *
     * @param userDetails The UserDetails object to be converted to UserDetailsResponseDto.
     * @return The UserDetailsResponseDto object created from the provided UserDetails.
     */
    UserDetailsResponseDto toUserDetailsResponseDto(UserDetails userDetails);
}
