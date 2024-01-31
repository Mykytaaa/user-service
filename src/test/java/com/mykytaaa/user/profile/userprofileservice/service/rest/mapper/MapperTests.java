package com.mykytaaa.user.profile.userprofileservice.service.rest.mapper;

import com.mykytaaa.user.profile.userprofileservice.entity.User;
import com.mykytaaa.user.profile.userprofileservice.entity.UserDetails;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserCreateRequestDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsCreateRequestDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsResponseDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsUpdateDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserDetailsUpdateRequestDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserResponseDto;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.UserUpdateRequestDto;
import com.mykytaaa.user.profile.userprofileservice.rest.mapper.UserCreateRequestMapper;
import com.mykytaaa.user.profile.userprofileservice.rest.mapper.UserDetailsCreateRequestMapper;
import com.mykytaaa.user.profile.userprofileservice.rest.mapper.UserDetailsResponseMapper;
import com.mykytaaa.user.profile.userprofileservice.rest.mapper.UserDetailsUpdateMapper;
import com.mykytaaa.user.profile.userprofileservice.rest.mapper.UserDetailsUpdateRequestMapper;
import com.mykytaaa.user.profile.userprofileservice.rest.mapper.UserResponseMapper;
import com.mykytaaa.user.profile.userprofileservice.rest.mapper.UserUpdateRequestMapper;
import com.mykytaaa.user.profile.userprofileservice.service.AbstractIntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;

@ExtendWith(SoftAssertionsExtension.class)
@RequiredArgsConstructor
public class MapperTests extends AbstractIntegrationTestBase {

    /**
     * Mapper for creating User objects from UserCreateRequestDto.
     */
    private final UserCreateRequestMapper userCreateRequestMapper;

    /**
     * Mapper for creating UserDetails objects from UserDetailsCreateRequestDto.
     */
    private final UserDetailsCreateRequestMapper userDetailsCreateRequestMapper;

    /**
     * Mapper for creating UserDetailsResponseDto objects from UserDetails.
     */
    private final UserDetailsResponseMapper userDetailsResponseMapper;

    /**
     * Mapper for creating UserDetails objects from UserDetailsUpdateDto.
     */
    private final UserDetailsUpdateMapper userDetailsUpdateMapper;

    /**
     * Mapper for creating UserDetails objects from UserDetailsUpdateRequestDto.
     */
    private final UserDetailsUpdateRequestMapper userDetailsUpdateRequestMapper;

    /**
     * Mapper for creating UserResponseDto objects from User.
     */
    private final UserResponseMapper userResponseMapper;

    /**
     * Mapper for creating User objects from userUpdateRequestDto.
     */
    private final UserUpdateRequestMapper mapper;

    /**
     * A Telegram ID used for testing requests.
     */
    private final String telegramId = "@request_Id";

    /**
     * An example Telegram ID used for testing purposes.
     */
    private final String telegramIdExample = "@example_Id";

    /**
     * A test phone number used for testing purposes.
     */
    private final String testPhoneNumber = "+12063217125";


    @Nested
    class UserCreateRequestMapperTest {
        @Test
        void mapFromUserCreateRequestDtoToUserSuccessfully(SoftAssertions softly) {

            final UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto(
                    "Kaz",
                    "Zam",
                    "kazzam@gmail.com",
                    new UserDetailsCreateRequestDto(
                            testPhoneNumber,
                            telegramId)
            );

            final User userToCheck = userCreateRequestMapper.toUser(userCreateRequestDto);
            softly.assertThat(userToCheck.getFirstName()).isEqualTo(userCreateRequestDto.firstName());
            softly.assertThat(userToCheck.getLastName()).isEqualTo(userCreateRequestDto.lastName());
            softly.assertThat(userToCheck.getEmail()).isEqualTo(userCreateRequestDto.email());
            softly.assertThat(userToCheck.getUserDetails()).isNotNull();
            softly.assertThat(userToCheck.getUserDetails().getPhoneNumber())
                    .isEqualTo(userCreateRequestDto.userDetailsCreateRequestDto().phoneNumber());
            softly.assertThat(userToCheck.getUserDetails().getTelegramId())
                    .isEqualTo(userCreateRequestDto.userDetailsCreateRequestDto().telegramId());
        }
    }

    @Nested
    class UserDetailsCreateRequestMapperTest {

        @Test
        void successfullyMapUserDetailsCreateRequestDtoToUserDetails(SoftAssertions softly) {
            final UserDetailsCreateRequestDto userDetailsCreateRequestDto =
                    new UserDetailsCreateRequestDto(testPhoneNumber,
                            telegramId);

            final UserDetails userDetailsToCheck = userDetailsCreateRequestMapper
                    .toUserDetails(userDetailsCreateRequestDto);

            softly.assertThat(userDetailsToCheck.getPhoneNumber()).isEqualTo(userDetailsCreateRequestDto.phoneNumber());
            softly.assertThat(userDetailsToCheck.getTelegramId()).isEqualTo(userDetailsCreateRequestDto.telegramId());
        }
    }

    @Nested
    class UserDetailsResponseMapperTest {

        @Test
        void shouldSuccessfullyMapUserDetailsToUserDetailsResponseDto(SoftAssertions softly) {

            final UserDetails userDetails = new UserDetails();
            userDetails.setId(1L);
            userDetails.setPhoneNumber(testPhoneNumber);
            userDetails.setTelegramId(telegramId);
            userDetails.setCreatedAt(Instant.now());
            userDetails.setUpdatedAt(Instant.now());

            final UserDetailsResponseDto userResponseDto =
                    userDetailsResponseMapper.toUserDetailsResponseDto(userDetails);
            softly.assertThat(userResponseDto.getId()).isEqualTo(userDetails.getId());
            softly.assertThat(userResponseDto.getPhoneNumber()).isEqualTo(userDetails.getPhoneNumber());
            softly.assertThat(userResponseDto.getTelegramId()).isEqualTo(userDetails.getTelegramId());
            softly.assertThat(userResponseDto.getCreatedAt()).isNotNull();
            softly.assertThat(userResponseDto.getUpdatedAt()).isNotNull();

        }
    }

    @Nested
    class UserDetailsUpdateMapperTest {

        @Test
        void successfullyMapUserDetailsUpdateDtoToUserDetails(SoftAssertions softly) {

            final String userDetailsPhoneNumber = testPhoneNumber;
            final String userDetailsTelegramId = telegramId;

            final UserDetailsUpdateDto userDetailsUpdateDto = new UserDetailsUpdateDto(
                    1,
                    userDetailsPhoneNumber,
                    userDetailsTelegramId);

            final UserDetails userDetailsToCheck = userDetailsUpdateMapper.toUserDetails(userDetailsUpdateDto);
            softly.assertThat(userDetailsToCheck.getId()).isEqualTo(1);
            softly.assertThat(userDetailsToCheck.getPhoneNumber()).isEqualTo(userDetailsPhoneNumber);
            softly.assertThat(userDetailsToCheck.getTelegramId()).isEqualTo(userDetailsTelegramId);
        }
    }

    @Nested
    class UserDetailsUpdateRequestMapperTest {

        @Test
        void successfullyMapUserDetailsUpdateRequestDtoToUserDetails(SoftAssertions softly) {

            final String userDetailsPhoneNumber = testPhoneNumber;
            final String userDetailsTelegramId = telegramId;


            final UserDetailsUpdateRequestDto dto = new UserDetailsUpdateRequestDto(
                    1, userDetailsPhoneNumber, userDetailsTelegramId
            );

            final UserDetails userDetailsToCheck = userDetailsUpdateRequestMapper.toUserDetails(dto);

            softly.assertThat(userDetailsToCheck.getId()).isEqualTo(1);
            softly.assertThat(userDetailsToCheck.getPhoneNumber()).isEqualTo(userDetailsPhoneNumber);
            softly.assertThat(userDetailsToCheck.getTelegramId()).isEqualTo(userDetailsTelegramId);
        }
    }

    @Nested
    class UserResponseMapperTest {

        @Test
        void successfullyMapUserToUserResponseDto(SoftAssertions softly) {

            final UserDetails userDetails = new UserDetails();
            userDetails.setPhoneNumber("+2064538995");
            userDetails.setTelegramId(telegramIdExample);
            userDetails.setCreatedAt(Instant.now());
            userDetails.setUpdatedAt(Instant.now());

            final User user = new User();
            user.setId(1);
            user.setEmail("user@example.com");
            user.setFirstName("Jack");
            user.setLastName("Willson");
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(Instant.now());
            user.setUserDetails(userDetails);

            final UserResponseDto dto = userResponseMapper.toUserResponseDto(user);

            softly.assertThat(dto.getId()).isEqualTo(user.getId());
            softly.assertThat(dto.getFirstName()).isEqualTo(user.getFirstName());
            softly.assertThat(dto.getLastName()).isEqualTo(user.getLastName());
            softly.assertThat(dto.getEmail()).isEqualTo(user.getEmail());
            softly.assertThat(dto.getCreatedAt()).isNotNull();
            softly.assertThat(dto.getUpdatedAt()).isNotNull();
            softly.assertThat(dto.getUserDetailsResponseDto().getId()).isEqualTo(userDetails.getId());
            softly.assertThat(dto.getUserDetailsResponseDto().getPhoneNumber()).isEqualTo(userDetails.getPhoneNumber());
            softly.assertThat(dto.getUserDetailsResponseDto().getTelegramId()).isEqualTo(userDetails.getTelegramId());
            softly.assertThat(dto.getUserDetailsResponseDto().getCreatedAt()).isNotNull();
            softly.assertThat(dto.getUserDetailsResponseDto().getUpdatedAt()).isNotNull();
        }
    }

    @Nested
    class UserUpdateRequestMapperTest {
        @Test
        void successfullyMapUserUpdateRequestDtoToUserDetails(SoftAssertions softly) {

            final UserDetailsUpdateDto userDetailsUpdateDto = new UserDetailsUpdateDto(
                    1, "+3067853941", telegramIdExample
            );

            final UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(
                    1, "Lam", "Watson", "lam.watson@example.com", userDetailsUpdateDto
            );

            final User userToCheck = mapper.toUser(userUpdateRequestDto);

            softly.assertThat(userToCheck.getId()).isEqualTo(userUpdateRequestDto.getId());
            softly.assertThat(userToCheck.getFirstName()).isEqualTo(userUpdateRequestDto.getFirstName());
            softly.assertThat(userToCheck.getLastName()).isEqualTo(userUpdateRequestDto.getLastName());
            softly.assertThat(userToCheck.getEmail()).isEqualTo(userUpdateRequestDto.getEmail());
            softly.assertThat(userToCheck.getCreatedAt()).isNull();
            softly.assertThat(userToCheck.getUpdatedAt()).isNull();
            softly.assertThat(userToCheck.getUserDetails().getId()).isEqualTo(userDetailsUpdateDto.id());
            softly.assertThat(userToCheck.getUserDetails().getPhoneNumber())
                    .isEqualTo(userDetailsUpdateDto.phoneNumber());
            softly.assertThat(userToCheck.getUserDetails().getTelegramId())
                    .isEqualTo(userDetailsUpdateDto.telegramId());
            softly.assertThat(userToCheck.getUserDetails().getCreatedAt()).isNull();
            softly.assertThat(userToCheck.getUserDetails().getUpdatedAt()).isNull();
        }
    }
}
