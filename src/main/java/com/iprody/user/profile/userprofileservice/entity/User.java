package com.iprody.user.profile.userprofileservice.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
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
}
