package com.mykytaaa.user.profile.userprofileservice.rest.mapper;

import com.mykytaaa.user.profile.userprofileservice.entity.UserDetails;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailsUpdateMapper {

    /**
     * Converts a UserDetailsUpdateDto object to a UserDetails object, applying specified mappings.
     *
     * @param userDetailsUpdateDto The UserDetailsUpdateDto to be converted to UserDetails.
     * @return The UserDetails object created from the provided UserDetailsUpdateDto.
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDetails toUserDetails(UserDetailsUpdateDto userDetailsUpdateDto);
}
