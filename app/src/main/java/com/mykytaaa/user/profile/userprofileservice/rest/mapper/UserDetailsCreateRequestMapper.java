package com.mykytaaa.user.profile.userprofileservice.rest.mapper;

import com.mykytaaa.user.profile.userprofileservice.entity.UserDetails;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsCreateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailsCreateRequestMapper {

    /**
     * Converts a UserDetailsCreateRequestDto object to a UserDetails object, applying specified mappings.
     *
     * @param userDetailsCreateRequestDto The UserDetailsCreateRequestDto to be converted to UserDetails.
     * @return The UserDetails object created from the provided UserDetailsCreateRequestDto.
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDetails toUserDetails(UserDetailsCreateRequestDto userDetailsCreateRequestDto);
}
