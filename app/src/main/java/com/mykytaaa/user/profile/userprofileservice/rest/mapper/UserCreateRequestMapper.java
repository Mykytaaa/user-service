package com.mykytaaa.user.profile.userprofileservice.rest.mapper;

import com.mykytaaa.user.profile.userprofileservice.entity.User;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserCreateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserDetailsCreateRequestMapper.class)
public interface UserCreateRequestMapper {

    /**
     * Converts a UserCreateRequestDto object to a User object, applying specified mappings.
     *
     * @param userCreateRequestDto The UserCreateRequestDto to be converted to a User.
     * @return The User object created from the provided UserCreateRequestDto.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "userDetailsCreateRequestDto", target = "userDetails")
    User toUser(UserCreateRequestDto userCreateRequestDto);
}
