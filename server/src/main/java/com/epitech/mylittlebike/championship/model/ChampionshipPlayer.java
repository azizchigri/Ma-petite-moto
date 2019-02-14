package com.epitech.mylittlebike.championship.model;

import com.epitech.mylittlebike.user.model.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The type Championship player.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "championship_player")
public class ChampionshipPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "status")
    private Long status;

    @Column(name = "first_driver")
    private String firstDriver;

    @Column(name = "second_driver")
    private String secondDriver;

    @Column(name = "first_driver_bet")
    private Integer firstDriverBet;

    @Column(name = "second_driver_bet")
    private Integer secondDriverBet;

    @Column(name = "wallet")
    private Long wallet;

    @Column(name = "score")
    private Long score;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @JoinColumn
    @ManyToOne
    private Championship championship;

    /**
     * Gets status.
     *
     * @return the status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * Gets first driver.
     *
     * @return the first driver
     */
    public String getFirstDriver() {
        return firstDriver;
    }

    /**
     * Sets first driver.
     *
     * @param firstDriver the first driver
     */
    public void setFirstDriver(String firstDriver) {
        this.firstDriver = firstDriver;
    }

    /**
     * Gets second driver.
     *
     * @return the second driver
     */
    public String getSecondDriver() {
        return secondDriver;
    }

    /**
     * Sets second driver.
     *
     * @param secondDriver the second driver
     */
    public void setSecondDriver(String secondDriver) {
        this.secondDriver = secondDriver;
    }

    /**
     * Gets wallet.
     *
     * @return the wallet
     */
    public Long getWallet() {
        return wallet;
    }

    /**
     * Sets wallet.
     *
     * @param wallet the wallet
     */
    public void setWallet(Long wallet) {
        this.wallet = wallet;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public Long getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(Long score) {
        this.score = score;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets championship.
     *
     * @return the championship
     */
    public Championship getChampionship() {
        return championship;
    }

    /**
     * Sets championship.
     *
     * @param championship the championship
     */
    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    /**
     * Gets first driver bet.
     *
     * @return the first driver bet
     */
    public Integer getFirstDriverBet() {
        return firstDriverBet;
    }

    /**
     * Sets first driver bet.
     *
     * @param firstDriverBet the first driver bet
     */
    public void setFirstDriverBet(Integer firstDriverBet) {
        this.firstDriverBet = firstDriverBet;
    }

    /**
     * Gets second driver bet.
     *
     * @return the second driver bet
     */
    public Integer getSecondDriverBet() {
        return secondDriverBet;
    }

    /**
     * Sets second driver bet.
     *
     * @param secondDriverBet the second driver bet
     */
    public void setSecondDriverBet(Integer secondDriverBet) {
        this.secondDriverBet = secondDriverBet;
    }
}
