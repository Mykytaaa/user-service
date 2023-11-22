package com.iprody.user.profile.userprofileservice.service.rest.controller;

import com.iprody.user.profile.userprofileservice.rest.dto.UserCreateRequestDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserDetailsCreateRequestDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserDetailsResponseDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserDetailsUpdateDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserDetailsUpdateRequestDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserResponseDto;
import com.iprody.user.profile.userprofileservice.rest.dto.UserUpdateRequestDto;
import com.iprody.user.profile.userprofileservice.service.AbstractIntegrationTestBaseController;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.test.StepVerifier;

import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
@ExtendWith(SoftAssertionsExtension.class)
@AutoConfigureWebTestClient
public class UserControllerIntegrationTest extends AbstractIntegrationTestBaseController {

    public static final String URL_USER_CONTROLLER = "/api/v1/users";
    public static final String URL_ID = "/{id}";
    public static final String URL_CONTACTS_ID = "/user-contacts/{id}";

    private static final String TEST_CREATE_USER_NAME = "testCreateUserName";
    private static final String TEST_CREATE_USER_LAST_NAME = "testCreateLastName";
    private static final String TEST_CREATE_USER_EMAIL = "testCreateUserRequest@example.com";
    private static final String TEST_CREATE_USER_DETAILS_NUMBER = "+14256774445";
    private static final String TEST_CREATE_USER_DETAILS_TELEGRAM = "@create_UserRequest";

    private static final String WACKY_FIRST_NAME = "Wacky";
    private static final String WACKY_WALSH_EMAIL = "wackywalsh@example.com";

    private static final String USER_LAST_NAME_WALSH = "Walsh";
    private static final String USER_PHONE_NUMBER = "+919542348755";
    private static final String USER_WACKY_TELEGRAM_ID = "@secondTelegram_id";

    private static final String TEST_UPDATE_USER_NAME = "JohnUpdated";
    private static final String TEST_UPDATE_USER_LAST_NAME = "WalshUpdated";
    private static final String TEST_UPDATE_USER_EMAIL = "jphnupdated.walshupdated@example.com";
    private static final String TEST_UPDATE_USER_DETAILS_PHONE_NUMBER = "+919367788755";
    private static final String TEST_UPDATE_USER_DETAILS_TELEGRAM_ID = "@firstTelegram_id";

    private static final String USER_DETAILS_TO_UPDATE_PHONE_NUMBER = "+17202334324";
    private static final String USER_DETAILS_TO_UPDATE_TELEGRAM_ID = "@max_updated_telegramId";

    private final WebTestClient webTestClient;

    @Order(1)
    @Test
    void shouldSuccessfullyCreateUser_withUserDetails(SoftAssertions softly) {

        final UserCreateRequestDto testUserCreateRequestDto = new UserCreateRequestDto(
                TEST_CREATE_USER_NAME,
                TEST_CREATE_USER_LAST_NAME,
                TEST_CREATE_USER_EMAIL,
                new UserDetailsCreateRequestDto(
                        TEST_CREATE_USER_DETAILS_NUMBER,
                        TEST_CREATE_USER_DETAILS_TELEGRAM
                )
        );

        StepVerifier.create(webTestClient.post()
                        .uri(URL_USER_CONTROLLER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(testUserCreateRequestDto))
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .returnResult(UserResponseDto.class)
                        .getResponseBody())
                .assertNext(actualUserResponseDto -> {
                    softly.assertThat(actualUserResponseDto.getId()).isNotNull();
                    softly.assertThat(actualUserResponseDto.getFirstName()).isEqualTo(TEST_CREATE_USER_NAME);
                    softly.assertThat(actualUserResponseDto.getLastName()).isEqualTo(TEST_CREATE_USER_LAST_NAME);
                    softly.assertThat(actualUserResponseDto.getEmail()).isEqualTo(TEST_CREATE_USER_EMAIL);
                    softly.assertThat(actualUserResponseDto.getCreatedAt()).isNotNull();
                    softly.assertThat(actualUserResponseDto.getUpdatedAt()).isNotNull();
                    softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto().getId()).isNotNull();
                    softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto()
                            .getPhoneNumber()).isEqualTo(TEST_CREATE_USER_DETAILS_NUMBER);
                    softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto()
                            .getTelegramId()).isEqualTo(TEST_CREATE_USER_DETAILS_TELEGRAM);
                    softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto().getCreatedAt()).isNotNull();
                    softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto().getUpdatedAt()).isNotNull();
                }).verifyComplete();
    }

    @Order(2)
    @Test
    void shouldSuccessfullyUpdateUser(SoftAssertions softly) {

        final UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(
                1, TEST_UPDATE_USER_NAME, TEST_UPDATE_USER_LAST_NAME, TEST_UPDATE_USER_EMAIL,
                new UserDetailsUpdateDto(
                        1,
                        TEST_UPDATE_USER_DETAILS_PHONE_NUMBER,
                        TEST_UPDATE_USER_DETAILS_TELEGRAM_ID
                ));

        StepVerifier.create(webTestClient.put()
                .uri(URL_USER_CONTROLLER + URL_ID, userUpdateRequestDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userUpdateRequestDto))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .returnResult(UserResponseDto.class)
                .getResponseBody()
        ).assertNext(actualUserResponseDto -> {
                    softly.assertThat(actualUserResponseDto.getId()).isEqualTo(1);
                    softly.assertThat(actualUserResponseDto.getFirstName()).isEqualTo(TEST_UPDATE_USER_NAME);
                    softly.assertThat(actualUserResponseDto.getLastName()).isEqualTo(TEST_UPDATE_USER_LAST_NAME);
                    softly.assertThat(actualUserResponseDto.getEmail()).isEqualTo(TEST_UPDATE_USER_EMAIL);
                    softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto().getId()).isEqualTo(1);
                    softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto()
                            .getTelegramId()).isEqualTo(TEST_UPDATE_USER_DETAILS_TELEGRAM_ID);
                    softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto()
                            .getPhoneNumber()).isEqualTo(TEST_UPDATE_USER_DETAILS_PHONE_NUMBER);
                }
        ).verifyComplete();
    }

    @Order(3)
    @Test
    void shouldSuccessfullyFindUserById(SoftAssertions softly) {

        StepVerifier.create(webTestClient.get()
                .uri(URL_USER_CONTROLLER + URL_ID, 2)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .returnResult(UserResponseDto.class)
                .getResponseBody()
        ).assertNext(actualUserResponseDto -> {
            softly.assertThat(actualUserResponseDto.getId()).isEqualTo(2);
            softly.assertThat(actualUserResponseDto.getFirstName()).isEqualTo(WACKY_FIRST_NAME);
            softly.assertThat(actualUserResponseDto.getLastName()).isEqualTo(USER_LAST_NAME_WALSH);
            softly.assertThat(actualUserResponseDto.getEmail()).isEqualTo(WACKY_WALSH_EMAIL);
            softly.assertThat(actualUserResponseDto.getCreatedAt()).isNotNull();
            softly.assertThat(actualUserResponseDto.getUpdatedAt()).isNotNull();
            softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto().getId()).isEqualTo(2);
            softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto()
                    .getPhoneNumber()).isEqualTo(USER_PHONE_NUMBER);
            softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto()
                    .getTelegramId()).isEqualTo(USER_WACKY_TELEGRAM_ID);
            softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto().getCreatedAt()).isNotNull();
            softly.assertThat(actualUserResponseDto.getUserDetailsResponseDto().getUpdatedAt()).isNotNull();
        }).verifyComplete();
    }

    @Order(4)
    @Test
    void shouldSuccessfullyFindUsers_withPageable(SoftAssertions softly) {

        final UserResponseDto userWackyWalshToCompare = new UserResponseDto(
                2, WACKY_FIRST_NAME, USER_LAST_NAME_WALSH, WACKY_WALSH_EMAIL,
                new UserDetailsResponseDto(2, USER_PHONE_NUMBER, USER_WACKY_TELEGRAM_ID)
        );

        final WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(URL_USER_CONTROLLER)
                        .queryParam("pageSize", 1)
                        .queryParam("pageNumber", 2)
                        .queryParam("sortOrder", "asc")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON);

        final List<UserResponseDto> actualUserListResponseDto =
                responseSpec.returnResult(new ParameterizedTypeReference<UserResponseDto>() {
                }).getResponseBody().collectList().block();

        final RecursiveComparisonConfiguration comparisonConfiguration = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("createdAt", "updatedAt",
                        "userDetailsResponseDto.createdAt", "userDetailsResponseDto.updatedAt")
                .build();


        softly.assertThat(actualUserListResponseDto)
                .usingRecursiveFieldByFieldElementComparator(comparisonConfiguration)
                .containsOnly(userWackyWalshToCompare);
    }

    @Order(5)
    @Test
    void shouldSuccessfullyUpdateUserDetails(SoftAssertions softly) {

        final UserDetailsUpdateRequestDto userDetailsUpdateRequestDto = new UserDetailsUpdateRequestDto(
                5,
                USER_DETAILS_TO_UPDATE_PHONE_NUMBER,
                USER_DETAILS_TO_UPDATE_TELEGRAM_ID
        );

        StepVerifier.create(webTestClient.put()
                        .uri(URL_USER_CONTROLLER + URL_CONTACTS_ID, userDetailsUpdateRequestDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(userDetailsUpdateRequestDto))
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .returnResult(UserDetailsResponseDto.class)
                        .getResponseBody())
                .assertNext(actualUserDetailsResponseDto -> {
                    softly.assertThat(actualUserDetailsResponseDto.getId()).isEqualTo(5);
                    softly.assertThat(actualUserDetailsResponseDto.getTelegramId())
                            .isEqualTo(USER_DETAILS_TO_UPDATE_TELEGRAM_ID);
                    softly.assertThat(actualUserDetailsResponseDto.getPhoneNumber())
                            .isEqualTo(USER_DETAILS_TO_UPDATE_PHONE_NUMBER);
                }).verifyComplete();
    }

    @Order(6)
    @Test
    void shouldSuccessfullyFindUserDetailsById(SoftAssertions softly) {
        StepVerifier.create(webTestClient.get()
                        .uri(URL_USER_CONTROLLER + URL_CONTACTS_ID, 2)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectHeader()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .returnResult(UserDetailsResponseDto.class)
                        .getResponseBody())
                .assertNext(actualUserDetailsResponseDto -> {
                    softly.assertThat(actualUserDetailsResponseDto.getId()).isEqualTo(2L);
                    softly.assertThat(actualUserDetailsResponseDto.getPhoneNumber()).isEqualTo(USER_PHONE_NUMBER);
                    softly.assertThat(actualUserDetailsResponseDto.getTelegramId()).isEqualTo(USER_WACKY_TELEGRAM_ID);
                    softly.assertThat(actualUserDetailsResponseDto.getCreatedAt()).isNotNull();
                    softly.assertThat(actualUserDetailsResponseDto.getUpdatedAt()).isNotNull();
                }).verifyComplete();
    }

    @Order(7)
    @Test
    void shouldSuccessfullyFindUserDetailsByUserId(SoftAssertions softly) {
        StepVerifier.create(
                webTestClient.get()
                        .uri(URL_USER_CONTROLLER + "/user-contacts/by-user-id/{id}", 2)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .returnResult(UserDetailsResponseDto.class)
                        .getResponseBody()
        ).assertNext(actualUserDetailsResponseDto -> {
            softly.assertThat(actualUserDetailsResponseDto.getId()).isEqualTo(2L);
            softly.assertThat(actualUserDetailsResponseDto.getPhoneNumber()).isEqualTo(USER_PHONE_NUMBER);
            softly.assertThat(actualUserDetailsResponseDto.getTelegramId()).isEqualTo(USER_WACKY_TELEGRAM_ID);
            softly.assertThat(actualUserDetailsResponseDto.getCreatedAt()).isNotNull();
            softly.assertThat(actualUserDetailsResponseDto.getUpdatedAt()).isNotNull();
        }).verifyComplete();
    }
}
