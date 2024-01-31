package com.mykytaaa.user.profile.userprofileservice.service.service;

import com.mykytaaa.user.profile.userprofileservice.entity.User;
import com.mykytaaa.user.profile.userprofileservice.entity.UserDetails;
import com.mykytaaa.user.profile.userprofileservice.exception.ResourceNotFoundException;
import com.mykytaaa.user.profile.userprofileservice.repository.UserRepository;
import com.mykytaaa.user.profile.userprofileservice.service.AbstractIntegrationTestBase;
import com.mykytaaa.user.profile.userprofileservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
@ExtendWith(SoftAssertionsExtension.class)
class UserServiceIntegrationTest extends AbstractIntegrationTestBase {

    private static final String FIRST_NAME_JOHN = "John";
    private static final String FIRST_NAME_WACKY = "Wacky";
    private static final String FIRST_NAME_LEWIS = "Lewis";
    private static final String FIRST_NAME_KATIE = "Katie";
    private static final String LAST_NAME_WALSH = "Walsh";
    private static final String EMAIL_JOHN_WALSH = "johnwalsh@example.com";
    private static final String EMAIL_WACKY_WALSH = "wackywalsh@example.com";
    private static final String EMAIL_LEWIS_WALSH = "lewiswalsh@example.com";
    private static final String EMAIL_KATIE_WALSH = "katiewalsh@example.com";
    private static final String UPDATED_SUFFIX = " updated";
    private static final String CREATED_AT_FIELD = "createdAt";
    private static final String UPDATED_AT_FIELD = "updatedAt";
    private static final String USER_DETAILS_FIELD = "userDetails";
    private static final String FOURTH_TELEGRAM_ID = "@fourthTelegram_id";
    private static final String PHONE_NUMBER_TO_SAVE = "+15095629821";

    private final UserService userService;

    private final UserRepository userRepository;

    @Order(1)
    @Test
    void shouldSaveUserSuccessfully(SoftAssertions softly) {
        final UserDetails userDetailsForKatie = new UserDetails();
        userDetailsForKatie.setTelegramId(FOURTH_TELEGRAM_ID);
        userDetailsForKatie.setPhoneNumber(PHONE_NUMBER_TO_SAVE);

        final User userKatieWalsh = new User();
        userKatieWalsh.setFirstName(FIRST_NAME_KATIE);
        userKatieWalsh.setLastName(LAST_NAME_WALSH);
        userKatieWalsh.setEmail(EMAIL_KATIE_WALSH);
        userKatieWalsh.setUserDetails(userDetailsForKatie);

        final User savedKatieWalsh = userService.save(userKatieWalsh);

        softly.assertThat(savedKatieWalsh).isNotNull();
        softly.assertThat(savedKatieWalsh.getId()).isNotNull();
        softly.assertThat(savedKatieWalsh.getFirstName()).isEqualTo(FIRST_NAME_KATIE);
        softly.assertThat(savedKatieWalsh.getLastName()).isEqualTo(LAST_NAME_WALSH);
        softly.assertThat(savedKatieWalsh.getEmail()).isEqualTo(EMAIL_KATIE_WALSH);
        softly.assertThat(savedKatieWalsh.getCreatedAt()).isNotNull();
        softly.assertThat(savedKatieWalsh.getUpdatedAt()).isNotNull();
    }

    @Order(2)
    @Test
    void shouldUpdateExistingUser(SoftAssertions softly) {
        final UserDetails userDetails = new UserDetails();
        userDetails.setId(2);
        userDetails.setTelegramId("+919542348755");
        userDetails.setPhoneNumber("@secondTelegram_id");

        final User userToUpdate = createUser(
                2L, FIRST_NAME_WACKY + UPDATED_SUFFIX, LAST_NAME_WALSH + UPDATED_SUFFIX, EMAIL_WACKY_WALSH);
        userToUpdate.setUserDetails(userDetails);

        final User updatedUser = userService.save(userToUpdate);

        softly.assertThat(updatedUser).isNotNull();
        softly.assertThat(updatedUser.getId()).isEqualTo(2L);
        softly.assertThat(updatedUser.getFirstName()).isEqualTo(FIRST_NAME_WACKY + UPDATED_SUFFIX);
        softly.assertThat(updatedUser.getLastName()).isEqualTo(LAST_NAME_WALSH + UPDATED_SUFFIX);
        softly.assertThat(updatedUser.getEmail()).isEqualTo(EMAIL_WACKY_WALSH);
        softly.assertThat(updatedUser.getUpdatedAt()).isNotNull();
    }

    @Order(3)
    @Test
    void shouldRetrieveExistingUser_withRequestedId(SoftAssertions softly) {
        final User userToFind = userService.findUserById(1L);

        softly.assertThat(userToFind).isNotNull();
        softly.assertThat(userToFind.getId()).isEqualTo(1L);
        softly.assertThat(userToFind.getCreatedAt()).isNotNull();
        softly.assertThat(userToFind.getUpdatedAt()).isNotNull();
        softly.assertThat(userToFind.getFirstName()).isEqualTo(FIRST_NAME_JOHN);
        softly.assertThat(userToFind.getLastName()).isEqualTo(LAST_NAME_WALSH);
        softly.assertThat(userToFind.getEmail()).isEqualTo(EMAIL_JOHN_WALSH);
    }

    @Order(4)
    @Test
    void shouldRetrieveExistingUser_withRequestedEmail(SoftAssertions softly) {
        final User userToFind = userService.findByEmail(EMAIL_LEWIS_WALSH);

        softly.assertThat(userToFind).isNotNull();
        softly.assertThat(userToFind.getId()).isEqualTo(3L);
        softly.assertThat(userToFind.getFirstName()).isEqualTo(FIRST_NAME_LEWIS);
        softly.assertThat(userToFind.getLastName()).isEqualTo(LAST_NAME_WALSH);
        softly.assertThat(userToFind.getEmail()).isEqualTo(EMAIL_LEWIS_WALSH);
        softly.assertThat(userToFind.getCreatedAt()).isNotNull();
        softly.assertThat(userToFind.getUpdatedAt()).isNotNull();
    }

    @Order(5)
    @Test
    void shouldRetrievePage_withRequestedPageable(SoftAssertions softly) {
        final List<User> users = userService.findAll(PageRequest.of(2, 1, Sort.by("id"))).getContent();
        softly.assertThat(users).isNotEmpty();
        softly.assertThat(users.size()).isEqualTo(1);

        final User lewisWalsh = createUser(3L, FIRST_NAME_LEWIS, LAST_NAME_WALSH, EMAIL_LEWIS_WALSH);

        final RecursiveComparisonConfiguration comparisonConfiguration = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields(CREATED_AT_FIELD, UPDATED_AT_FIELD, USER_DETAILS_FIELD)
                .build();

        softly.assertThat(users)
                .usingRecursiveFieldByFieldElementComparator(comparisonConfiguration)
                .containsOnly(lewisWalsh);
    }

    @Order(6)
    @Test
    void shouldThrowDataIntegrityViolationException_whenEmailUniquenessConstraintFailed() {
        final UserDetails userDetails = new UserDetails();
        userDetails.setPhoneNumber("+14253021325");
        userDetails.setTelegramId("@dataIntegrity_Violation");

        final User user = new User();
        user.setFirstName(FIRST_NAME_LEWIS);
        user.setLastName(LAST_NAME_WALSH);
        user.setEmail(EMAIL_LEWIS_WALSH);
        user.setUserDetails(userDetails);

        assertThatThrownBy(() -> userService.save(user))
                .isExactlyInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("duplicate key value violates unique constraint \"users_email_key\"");
    }

    @Order(7)
    @Test
    void shouldThrowResourceNotFoundException_whenUserWithSpecificIdDoesNotExist() {
        assertThatThrownBy(() -> userService.findUserById(100000L))
                .isExactlyInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with id: 100000");
    }

    @Order(8)
    @Test
    void shouldThrowResourceNotFoundException_whenUserWithSpecificEmailDoesNotExist() {
        assertThatThrownBy(() -> userService.findByEmail("nonexistendemail@example.com"))
                .isExactlyInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with email: nonexistendemail@example.com");
    }

    public User createUser(final long id, final String firstName, final String lastName, final String email) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }
}
