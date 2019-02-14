package com.epitech.mylittlebike.championship.repository;

import com.epitech.mylittlebike.championship.model.Championship;
import com.epitech.mylittlebike.championship.model.ChampionshipPlayer;
import com.epitech.mylittlebike.user.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Championship repository.
 */
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    /**
     * Find all by user list.
     *
     * @param player the player
     * @return the list
     */
    List<Championship> findAllByUser(ApplicationUser player);
}

