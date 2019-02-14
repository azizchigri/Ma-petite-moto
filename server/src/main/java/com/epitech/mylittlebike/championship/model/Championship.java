package com.epitech.mylittlebike.championship.model;

import com.epitech.mylittlebike.user.model.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * The type Championship.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "championship")
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "duration")
    private Long duration;

    @NotNull
    @Column(name = "wallet")
    private Long wallet;

    @Column(name = "start_date")
    private Date startDate;

    @NotNull
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    private ApplicationUser user;

    @Builder.Default
    @OneToMany
    @JoinColumn(name="id")
    private Collection<ChampionshipPlayer> championshipPlayer = new HashSet<>();

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(Long duration) {
        this.duration = duration;
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
     * Gets start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets championship player.
     *
     * @return the championship player
     */
    public Collection<ChampionshipPlayer> getChampionshipPlayer() {
        return championshipPlayer;
    }

    /**
     * Sets championship player.
     *
     * @param championshipPlayer the championship player
     */
    public void setChampionshipPlayer(Collection<ChampionshipPlayer> championshipPlayer) {
        this.championshipPlayer = championshipPlayer;
    }

    /**
     * Add championship player.
     *
     * @param championshipPlayer the championship player
     */
    public void addChampionshipPlayer(ChampionshipPlayer championshipPlayer) {
        this.championshipPlayer.add(championshipPlayer);
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public ApplicationUser getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
