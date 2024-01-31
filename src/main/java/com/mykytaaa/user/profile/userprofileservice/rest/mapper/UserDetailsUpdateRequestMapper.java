package com.mykytaaa.user.profile.userprofileservice.rest.mapper;

import com.mykytaaa.user.profile.userprofileservice.entity.UserDetails;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailsUpdateRequestMapper {

    /**
     * Converts a UserDetailsUpdateRequestDto object to a UserDetails object, applying specified mappings.
     *
     * @param userDetailsUpdateRequestDto The UserDetailsUpdateRequestDto to be converted to UserDetails.
     * @return The UserDetails object created from the provided UserDetailsUpdateRequestDto.
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDetails toUserDetails(UserDetailsUpdateRequestDto userDetailsUpdateRequestDto);
}
