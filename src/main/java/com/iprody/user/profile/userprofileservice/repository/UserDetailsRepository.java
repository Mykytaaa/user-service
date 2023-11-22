package com.iprody.user.profile.userprofileservice.repository;

import com.iprody.user.profile.userprofileservice.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    /**
     * Finds UserDetails by User.
     *
     * @param user The id of the User to find UserDetails.
     * @return Optional UserDetails if found, otherwise Optional#empty().
     */
    Optional<UserDetails> findUserDetailsByUserId(long user);
}
