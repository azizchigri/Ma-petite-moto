package com.epitech.mylittlebike.championship.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The type Driver.
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "driver")
public class Driver {

    /**
     * Instantiates a new Driver.
     */
    public Driver() {
        this.name = "";
        this.sportRadarId = "";
        this.team = "";
        this.priceMin = 0L;
        this.pictureUrl = "";
        this.number = "";
        this.url = "";
    }

    /**
     * Instantiates a new Driver.
     *
     * @param name         the name
     * @param sportRadarId the sport radar id
     * @param team         the team
     * @param priceMin     the price min
     * @param pictureUrl   the picture url
     * @param number       the number
     * @param url          the url
     */
    public Driver(String name, String sportRadarId, String team, Long priceMin, String pictureUrl, String number, String url) {
        this.name = name;
        this.sportRadarId = sportRadarId;
        this.team = team;
        this.priceMin = priceMin;
        this.pictureUrl = pictureUrl;
        this.number = number;
        this.url = url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "sportradar_id")
    private String sportRadarId;

    @Column(name = "team")
    private String team;

    @Column(name = "min_price")
    private Long priceMin;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "number")
    private String number;

    @Column(name = "url")
    private String url;

    /**
     * Gets number.
     *
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets sport radar id.
     *
     * @return the sport radar id
     */
    public String getSportRadarId() {
        return sportRadarId;
    }

    /**
     * Gets team.
     *
     * @return the team
     */
    public String getTeam() {
        return team;
    }

    /**
     * Gets price min.
     *
     * @return the price min
     */
    public Long getPriceMin() {
        return priceMin;
    }

    /**
     * Gets picture url.
     *
     * @return the picture url
     */
    public String getPictureUrl() {
        return pictureUrl;
    }
}
