package com.iprody.user.profile.userprofileservice.service.rest.adapter;

import com.iprody.user.profile.userprofileservice.entity.User;
import com.iprody.user.profile.userprofileservice.entity.UserDetails;
import com.iprody.user.profile.userprofileservice.rest.adapter.UserServiceAdapter;
import com.iprody.user.profile.userprofileservice.rest.dto.UserCreateRequestDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserDetailsResponseDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserDetailsUpdateRequestDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserResponseDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserUpdateRequestDto;
import com.iprody.user.profile.userprofileservice.rest.mapper.UserCreateRequestMapper;
import com.iprody.user.profile.userprofileservice.rest.mapper.UserDetailsResponseMapper;
import com.iprody.user.profile.userprofileservice.rest.mapper.UserDetailsUpdateRequestMapper;
import com.iprody.user.profile.userprofileservice.rest.mapper.UserResponseMapper;
import com.iprody.user.profile.userprofileservice.rest.mapper.UserUpdateRequestMapper;
import com.iprody.user.profile.userprofileservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceAdapterTest {

    @Mock
    private UserService userService;

    @Mock
    private UserCreateRequestMapper userCreateRequestMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Mock
    private UserDetailsResponseMapper userDetailsResponseMapper;

    @Mock
    private UserUpdateRequestMapper userUpdateRequestMapper;

    @Mock
    private UserDetailsUpdateRequestMapper userDetailsUpdateRequestMapper;

    @InjectMocks
    private UserServiceAdapter userServiceAdapter;

    @Test
    void shouldCreateUser() {
        final UserCreateRequestDto userCreateRequestDtoMock = mock(UserCreateRequestDto.class);
        final UserResponseDto userResponseDtoMock = mock(UserResponseDto.class);
        final User userMock = mock(User.class);

        when(userCreateRequestMapper.toUser(userCreateRequestDtoMock)).thenReturn(userMock);
        when(userService.save(userMock)).thenReturn(userMock);
        when(userResponseMapper.toUserResponseDto(userMock)).thenReturn(userResponseDtoMock);

        final UserResponseDto actualUserResponseDto = userServiceAdapter.createUser(userCreateRequestDtoMock);

        assertThat(actualUserResponseDto).isEqualTo(userResponseDtoMock);

        verify(userCreateRequestMapper, times(1)).toUser(userCreateRequestDtoMock);
        verify(userService, times(1)).save(userMock);
        verify(userResponseMapper, times(1)).toUserResponseDto(userMock);
    }

    @Test
    void shouldUpdateUser() {
        final UserUpdateRequestDto userUpdateRequestDtoMock = mock(UserUpdateRequestDto.class);
        final UserResponseDto userResponseDtoMock = mock(UserResponseDto.class);
        final User userMock = mock(User.class);
        final long id = 1L;
        userUpdateRequestDtoMock.setId(id);


        when(userUpdateRequestMapper.toUser(userUpdateRequestDtoMock)).thenReturn(userMock);
        when(userService.save(userMock)).thenReturn(userMock);
        when(userResponseMapper.toUserResponseDto(userMock)).thenReturn(userResponseDtoMock);

        final UserResponseDto actualUserResponseDto = userServiceAdapter.updateUser(userUpdateRequestDtoMock);

        assertThat(actualUserResponseDto).isEqualTo(userResponseDtoMock);

        verify(userUpdateRequestMapper, times(1)).toUser(userUpdateRequestDtoMock);
        verify(userService, times(1)).save(userMock);
        verify(userResponseMapper, times(1)).toUserResponseDto(userMock);
    }

    @Test
    void shouldFindUserById() {
        final long id = 1L;
        final UserResponseDto userResponseDtoMock = mock(UserResponseDto.class);
        final User userMock = mock(User.class);

        when(userService.findUserById(id)).thenReturn(userMock);
        when(userResponseMapper.toUserResponseDto(userMock)).thenReturn(userResponseDtoMock);

        final UserResponseDto actualUserResponseDto = userServiceAdapter.findUserById(id);

        assertThat(actualUserResponseDto).isEqualTo(userResponseDtoMock);

        verify(userService, times(1)).findUserById(id);
        verify(userResponseMapper, times(1)).toUserResponseDto(userMock);
    }

    @Test
    void shouldFindUsersWithPageable() {
        final int pageSize = 10;
        final int pageNumber = 1;
        final String sortBy = "id";
        final String sortOrder = "ASC";

        final Page<User> userPageMock = mock(Page.class);
        final List<User> usersMock = List.of(mock(User.class), mock(User.class));
        final List<UserResponseDto> userResponseDtosMock =
                List.of(mock(UserResponseDto.class), mock(UserResponseDto.class));

        when(userService.findAll(any(PageRequest.class))).thenReturn(userPageMock);
        when(userPageMock.getContent()).thenReturn(usersMock);
        when(userResponseMapper.toUserResponseDto(any(User.class)))
                .thenReturn(userResponseDtosMock.get(0), userResponseDtosMock.get(1));

        final List<UserResponseDto> actualUserResponseDtos =
                userServiceAdapter.findUsersWithPageable(pageSize, pageNumber, sortBy, sortOrder);

        assertThat(actualUserResponseDtos).hasSize(usersMock.size()).isEqualTo(userResponseDtosMock);

        verify(userService, times(1)).findAll(any(PageRequest.class));
        verify(userResponseMapper, times(usersMock.size())).toUserResponseDto(any(User.class));
    }

    @Test
    void shouldFindUserDetailsById() {
        final long id = 1L;
        final UserDetails userDetailsMock = mock(UserDetails.class);
        final UserDetailsResponseDto userDetailsResponseDtoMock = mock(UserDetailsResponseDto.class);

        when(userService.findUserDetailsById(id)).thenReturn(userDetailsMock);
        when(userDetailsResponseMapper
                .toUserDetailsResponseDto(userDetailsMock)).thenReturn(userDetailsResponseDtoMock);

        final UserDetailsResponseDto actualUserDetailsResponseDto = userServiceAdapter.findUserDetailsById(id);

        assertThat(actualUserDetailsResponseDto).isEqualTo(userDetailsResponseDtoMock);

        verify(userService, times(1)).findUserDetailsById(id);
        verify(userDetailsResponseMapper, times(1)).toUserDetailsResponseDto(userDetailsMock);
    }

    @Test
    void shouldFindUserDetailsByUserId() {
        final long userId = 1L;
        final UserDetails userDetailsMock = mock(UserDetails.class);
        final UserDetailsResponseDto userDetailsResponseDtoMock = mock(UserDetailsResponseDto.class);

        when(userService.findUserDetailsByUserId(userId)).thenReturn(userDetailsMock);
        when(userDetailsResponseMapper
                .toUserDetailsResponseDto(userDetailsMock)).thenReturn(userDetailsResponseDtoMock);

        final UserDetailsResponseDto actualUserDetailsResponseDto = userServiceAdapter.findUserDetailsByUserId(userId);

        assertThat(actualUserDetailsResponseDto).isEqualTo(userDetailsResponseDtoMock);

        verify(userService, times(1)).findUserDetailsByUserId(userId);
        verify(userDetailsResponseMapper, times(1)).toUserDetailsResponseDto(userDetailsMock);
    }

    @Test
    void shouldUpdateUserDetails() {
        final UserDetailsUpdateRequestDto userDetailsUpdateRequestDtoMock = mock(UserDetailsUpdateRequestDto.class);
        final UserDetails userDetailsMock = mock(UserDetails.class);
        final UserDetailsResponseDto userDetailsResponseDtoMock = mock(UserDetailsResponseDto.class);

        when(userDetailsUpdateRequestMapper
                .toUserDetails(userDetailsUpdateRequestDtoMock)).thenReturn(userDetailsMock);
        when(userService.save(userDetailsMock)).thenReturn(userDetailsMock);
        when(userDetailsResponseMapper
                .toUserDetailsResponseDto(userDetailsMock)).thenReturn(userDetailsResponseDtoMock);

        final UserDetailsResponseDto actualUserDetailsResponseDto =
                userServiceAdapter.updateUserDetails(userDetailsUpdateRequestDtoMock);

        assertThat(actualUserDetailsResponseDto).isEqualTo(userDetailsResponseDtoMock);

        verify(userDetailsUpdateRequestMapper, times(1)).toUserDetails(userDetailsUpdateRequestDtoMock);
        verify(userService, times(1)).save(userDetailsMock);
        verify(userDetailsResponseMapper, times(1)).toUserDetailsResponseDto(userDetailsMock);
    }

    @Test
    void shouldThrowExceptionWhenUserServiceFails() {
        final UserCreateRequestDto userCreateRequestDtoMock = mock(UserCreateRequestDto.class);
        final User userMock = mock(User.class);

        when(userCreateRequestMapper.toUser(userCreateRequestDtoMock)).thenReturn(userMock);
        when(userService.save(userMock)).thenThrow(new RuntimeException());

        assertThatThrownBy(() -> userServiceAdapter.createUser(userCreateRequestDtoMock))
                .isInstanceOf(RuntimeException.class);    }
}
