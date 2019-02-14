package com.epitech.mylittlebike.user.repository;

import com.epitech.mylittlebike.user.model.ApplicationUser;
import com.epitech.mylittlebike.user.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Application friend repository.
 */
public interface ApplicationFriendRepository extends JpaRepository<Friend, Long> {
}

