package com.mykytaaa.user.profile.userprofileservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * User.
 * Columns:
 *   id (PK)
 *   first name (varchar, length 50 chars, not null)
 *   last name (varchar, length 50 chars, not null)
 *   email (varchar, unique, not null
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractBaseEntity {

    /**
     * User first name.
     */
    @Column(length = 50, nullable = false)
    private String firstName;

    /**
     * User last name.
     */
    @Column(length = 50, nullable = false)
    private String lastName;


    /**
     * User email address.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * This FK represents a relationship between a User and UserDetails.
     */
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "user_details_id", referencedColumnName = "id")
    private UserDetails userDetails;

    /**
     * Sets the UserDetails for the current user.
     *
     * This method establishes a bidirectional relationship between the user and UserDetails
     * by setting the UserDetails for the user and setting the user for the UserDetails.
     *
     * @param userDetails The UserDetails to associate with the current user.
     */
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
        userDetails.setUser(this);
    }

    /**
     * Returns a string representation of the User object. The string representation
     * includes the user's first name, last name, email, and user details.
     *
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{"
                + "firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", email='" + email + '\''
                + ", userDetails=" + userDetails
                + '}';
    }
}
