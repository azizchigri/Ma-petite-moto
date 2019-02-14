package com.epitech.mylittlebike.championship.repository;

import com.epitech.mylittlebike.championship.model.Championship;
import com.epitech.mylittlebike.championship.model.Driver;
import com.epitech.mylittlebike.user.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Driver repository.
 */
public interface DriverRepository extends JpaRepository<Driver, Long> {
}

