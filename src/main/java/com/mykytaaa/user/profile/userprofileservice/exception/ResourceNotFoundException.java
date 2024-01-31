package com.mykytaaa.user.profile.userprofileservice.exception;

/**
 * This class is used to represent an occurred exception.
 */

public final class ResourceNotFoundException extends RuntimeException {

    /**
     * This constructor calls a super class constructor.
     * @param errorMessage the formatted String with a description of a reason.
     */
    public ResourceNotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    /**
     * This static class is used to return a formatted error message.
     */
    public static final class ErrorMessage {

        /**
         * Error message template when an entity is not found with a specific email address.
         */
        private static final String NOT_FOUND_WITH_EMAIL = "%s not found with email: %s";

        /**
         * Error message template when an entity is not found with a specific ID.
         */
        private static final String NOT_FOUND_WITH_ID = "%s not found with id: %d";

        /**
         * Error message template when a User Details entity is not found with a specific User ID.
         */
        private static final String NOT_FOUND_USER_DETAILS_WITH_USER_ID = "%s not found user details with user id: %d";

        /**
         * The final form of error message.
         */
        private final String formattedMessage;

        private ErrorMessage(final String format, final Object... args) {
            formattedMessage = format.formatted(args);
        }

        /**
         * Create an ErrorMessage instance for an entity not found with a specific email.
         *
         * @param domain the entity name where the problem occurred.
         * @param email the email to construct an error string.
         * @return a formatted exception.
         */
        public static ErrorMessage ofMail(String domain, String email) {
            return new ErrorMessage(NOT_FOUND_WITH_EMAIL, domain, email);
        }

        /**
         * Create an ErrorMessage instance for an entity not found with a specific id.
         *
         * @param domain the entity name where the problem occurred.
         * @param id the id to construct an error string.
         * @return a formatted exception.
         */
        public static ErrorMessage ofId(String domain, long id) {
            return new ErrorMessage(NOT_FOUND_WITH_ID, domain, id);
        }

        /**
         * Create an ErrorMessage instance for an entity not found with a specific id.
         *
         * @param domain the entity name where the problem occurred.
         * @param id the id to construct an error string.
         * @return a formatted exception.
         */
        public static ErrorMessage ofUserDetailsByUserId(String domain, long id) {
            return new ErrorMessage(NOT_FOUND_USER_DETAILS_WITH_USER_ID, domain, id);
        }

        private String getMessage() {
            return formattedMessage;
        }

    }
}
