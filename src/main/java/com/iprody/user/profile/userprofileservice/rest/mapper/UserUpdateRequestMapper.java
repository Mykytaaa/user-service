package com.iprody.user.profile.userprofileservice.rest.mapper;

import com.iprody.user.profile.userprofileservice.entity.User;
import com.iprody.user.profile.userprofileservice.rest.dto.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserUpdateRequestMapper {

    /**
     * Converts a UserUpdateRequestDto object to a User object, applying specified mappings.
     *
     * @param userUpdateRequestDto The UserUpdateRequestDto to be converted to User.
     * @return The User object created from the provided UserUpdateRequestDto.
     */
    @Mapping(target = "userDetails", source = "userDetailsUpdateDto")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(UserUpdateRequestDto userUpdateRequestDto);
}
