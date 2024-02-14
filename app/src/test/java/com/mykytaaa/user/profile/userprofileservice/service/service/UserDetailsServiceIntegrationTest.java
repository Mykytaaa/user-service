package com.mykytaaa.user.profile.userprofileservice.service.service;

import com.mykytaaa.user.profile.userprofileservice.entity.User;
import com.mykytaaa.user.profile.userprofileservice.entity.UserDetails;
import com.mykytaaa.user.profile.userprofileservice.exception.ResourceNotFoundException;
import com.mykytaaa.user.profile.userprofileservice.service.AbstractIntegrationTestBase;
import com.mykytaaa.user.profile.userprofileservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
@ExtendWith(SoftAssertionsExtension.class)
public class UserDetailsServiceIntegrationTest extends AbstractIntegrationTestBase {

    private static final String NEW_TELEGRAM_ID = "@newTelegram_id";
    private static final String PHONE_NUMBER = "+16308520397";
    private static final String SECOND_TELEGRAM_ID = "@secondTelegram_id";
    private static final String FIRST_TELEGRAM_ID_UPDATED = "@firstTelegram_id_updated";
    private static final String SECOND_TELEGRAM_ID_PHONE_NUMBER = "+919542348755";
    private static final String FIRST_TELEGRAM_ID_PHONE_NUMBER_UPDATED = "+919367788756";

    private static final String FIRST_NAME_JOHN = "John";
    private static final String FIRST_NAME_LEWIS = "Lewis";
    private static final String LAST_NAME_WALSH = "Walsh";
    private static final String EMAIL_JOHN_WALSH = "johnwalsh@example.com";
    private static final String EMAIL_LEWIS_WALSH = "lewiswalsh@example.com";

    private static final String DATA_INTEGRITY_EXCEPTION_MESSAGE = "new row for relation \"user_details\" "
            + "violates check constraint";

    private final UserService userService;

    @Order(1)
    @Test
    void shouldSaveUserDetailsSuccessfullyWhenUserExists(SoftAssertions softly) {
        final User user = createUser(1L, FIRST_NAME_JOHN, LAST_NAME_WALSH, EMAIL_JOHN_WALSH);

        final UserDetails userDetailsToSave = new UserDetails();
        userDetailsToSave.setTelegramId(NEW_TELEGRAM_ID);
        userDetailsToSave.setPhoneNumber(PHONE_NUMBER);
        userDetailsToSave.setUser(user);

        final UserDetails savedUserDetails = userService.save(userDetailsToSave);

        softly.assertThat(savedUserDetails).isNotNull();
        softly.assertThat(savedUserDetails.getId()).isNotNull();
        softly.assertThat(savedUserDetails.getTelegramId()).isEqualTo(NEW_TELEGRAM_ID);
        softly.assertThat(savedUserDetails.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        softly.assertThat(savedUserDetails.getCreatedAt()).isNotNull();
        softly.assertThat(savedUserDetails.getUpdatedAt()).isNotNull();

        softly.assertThat(savedUserDetails.getUser()).isNotNull();
        softly.assertThat(savedUserDetails.getUser())
                .returns(FIRST_NAME_JOHN, User::getFirstName)
                .returns(LAST_NAME_WALSH, User::getLastName)
                .returns(EMAIL_JOHN_WALSH, User::getEmail);
    }

    @Order(2)
    @Test
    void shouldUpdateExistingUserDetailsSuccessfully(SoftAssertions softly) {
        final User user = createUser(3L, FIRST_NAME_LEWIS, LAST_NAME_WALSH, EMAIL_LEWIS_WALSH);

        final UserDetails userDetailsToUpdate = new UserDetails();
        userDetailsToUpdate.setId(3L);
        userDetailsToUpdate.setTelegramId(FIRST_TELEGRAM_ID_UPDATED);
        userDetailsToUpdate.setPhoneNumber(FIRST_TELEGRAM_ID_PHONE_NUMBER_UPDATED);
        user.setUserDetails(userDetailsToUpdate);

        final UserDetails updatedUserDetails = userService.save(userDetailsToUpdate);

        softly.assertThat(updatedUserDetails).isNotNull();
        softly.assertThat(updatedUserDetails.getId()).isEqualTo(3L);
        softly.assertThat(updatedUserDetails.getPhoneNumber()).isEqualTo(FIRST_TELEGRAM_ID_PHONE_NUMBER_UPDATED);
        softly.assertThat(updatedUserDetails.getTelegramId()).isEqualTo(FIRST_TELEGRAM_ID_UPDATED);
        softly.assertThat(updatedUserDetails.getUser()).isNotNull();
    }

    @Order(3)
    @Test
    void shouldRetrieveExistingUserDetails_withRequestedId(SoftAssertions softly) {
        final UserDetails userDetails = userService.findUserDetailsById(2L);
        softly.assertThat(userDetails).isNotNull();
        softly.assertThat(userDetails.getId()).isEqualTo(2L);
        softly.assertThat(userDetails.getPhoneNumber()).isEqualTo(SECOND_TELEGRAM_ID_PHONE_NUMBER);
        softly.assertThat(userDetails.getTelegramId()).isEqualTo(SECOND_TELEGRAM_ID);
        softly.assertThat(userDetails.getUser()).isNotNull();
        softly.assertThat(userDetails.getCreatedAt()).isNotNull();
        softly.assertThat(userDetails.getUpdatedAt()).isNotNull();
    }

    @Order(4)
    @Test
    void shouldRetrieveExistingUserDetails_withRequestedUserId(SoftAssertions softly) {
        final UserDetails userDetails = userService.findUserDetailsByUserId(2L);
        softly.assertThat(userDetails).isNotNull();
        softly.assertThat(userDetails.getId()).isEqualTo(2L);
        softly.assertThat(userDetails.getPhoneNumber()).isEqualTo(SECOND_TELEGRAM_ID_PHONE_NUMBER);
        softly.assertThat(userDetails.getTelegramId()).isEqualTo(SECOND_TELEGRAM_ID);
        softly.assertThat(userDetails.getUser()).isNotNull();
        softly.assertThat(userDetails.getCreatedAt()).isNotNull();
        softly.assertThat(userDetails.getUpdatedAt()).isNotNull();
    }

    @Order(5)
    @Test
    void shouldThrowResourceNotFoundException_whenUserDetailsWithSpecificIdDoesNotExist() {
        assertThatThrownBy(() -> userService.findUserDetailsById(10000L))
                .isExactlyInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User Details not found with id: 10000");
    }

    @Order(6)
    @Test
    void shouldThrowResourceNotFoundException_whenUserDetailsWithSpecificUserIdDoesNotExist() {
        assertThatThrownBy(() -> userService.findUserDetailsByUserId(100000L))
                .isExactlyInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User Details not found user details with user id: 100000");
    }

    @Order(7)
    @Test
    void shouldThrowDataIntegrityViolationException_whenUserDetailsTriesToSaveWithBrokenTelegramId() {
        final UserDetails userDetails = new UserDetails();
        userDetails.setPhoneNumber("+4253111115");
        userDetails.setTelegramId("brokenId");
        userDetails.setUser(createUser(1L, FIRST_NAME_JOHN, LAST_NAME_WALSH, EMAIL_JOHN_WALSH));
        assertThatThrownBy(() -> userService.save(userDetails))
                .isExactlyInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining(DATA_INTEGRITY_EXCEPTION_MESSAGE + " \"telegram_id_check\"");
    }

    @Order(8)
    @Test
    void shouldThrowDataIntegrityViolationException_whenUserDetailsSavesWithBrokenPhoneNumber() {
        final UserDetails userDetails = new UserDetails();
        userDetails.setPhoneNumber("4253111115");
        userDetails.setTelegramId("@working_id");
        userDetails.setUser(createUser(1L, FIRST_NAME_JOHN, LAST_NAME_WALSH, EMAIL_JOHN_WALSH));
        assertThatThrownBy(() -> userService.save(userDetails))
                .isExactlyInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining(DATA_INTEGRITY_EXCEPTION_MESSAGE + " \"phone_number_check\"");
    }


    /**
     * Creates a new User instance with the provided details.
     *
     * @param id The unique identifier for the new user.
     * @param firstName The first name of the new user.
     * @param lastName The last name of the new user.
     * @param email The email address of the new user.
     * @return A new User object with the specified details.
     */
    public User createUser(final long id, final String firstName, final String lastName, final String email) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }
}
