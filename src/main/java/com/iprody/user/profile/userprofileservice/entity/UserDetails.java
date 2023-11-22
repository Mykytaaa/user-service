package com.iprody.user.profile.userprofileservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class UserDetails extends AbstractBaseEntity {

    /**
     * Represents the telegram id of a user.
     * The telegram id follows the format ^@[a-zA-Z0-9_]{5,32}$. For example '@exampleTelegram_id'.
     */
    @Column(length = 32, nullable = false)
    private String telegramId;

    /**
     * Represents the phone number of a user.
     * The phone number follows the format ^\+[1-9]\d{1,14}$. For example '+15095629821'.
     */
    @Column(length = 14, nullable = false)
    private String phoneNumber;

    /**
     * The user ID of the user.
     */
    @OneToOne(mappedBy = "userDetails")
    private User user;
}
