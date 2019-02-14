package com.epitech.mylittlebike.championship.repository;

import com.epitech.mylittlebike.championship.model.Championship;
import com.epitech.mylittlebike.championship.model.ChampionshipPlayer;
import com.epitech.mylittlebike.user.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Championship player repository.
 */
public interface ChampionshipPlayerRepository extends JpaRepository<ChampionshipPlayer, Long> {
    /**
     * Find by username championship player.
     *
     * @param username the username
     * @return the championship player
     */
    ChampionshipPlayer findByUsername(String username);

    /**
     * Find all by championship list.
     *
     * @param championship the championship
     * @return the list
     */
    List<ChampionshipPlayer> findAllByChampionship(Championship championship);

    /**
     * Find all by username list.
     *
     * @param username the username
     * @return the list
     */
    List<ChampionshipPlayer> findAllByUsername(String username);
}

