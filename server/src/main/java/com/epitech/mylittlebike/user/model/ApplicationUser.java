package com.epitech.mylittlebike.user.model;

import com.epitech.mylittlebike.championship.model.Championship;
import com.epitech.mylittlebike.championship.model.ChampionshipPlayer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;

/**
 * The type Application user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

	@Column(name = "password")
    @Length(min = 5, message = "Your password must have at least 5 characters")
    @NotEmpty(message = "Please provide your password")
    private String password;
    
    @Column(name = "username", unique=true)
	@Email(message = "Please provide a valid Email")
	@NotEmpty(message = "Please provide an username")
    private String username;

	@Column(name = "last_name")
    private String lastName;
    
    @Column(name = "first_name")
    private String firstName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "profile_picture")
	private String picturePath;

	@Column(name = "active")
	private int active;

	@Builder.Default
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private Collection<Friend> friends = new HashSet<>();

	@Builder.Default
	@OneToMany
	private Collection<Championship> championships = new HashSet<>();

    /**
     * Gets password.
     *
     * @return the password
     */
    @JsonIgnore
	public String getPassword() {
		return password;
	}

    /**
     * Sets password.
     *
     * @param password the password
     */
    @JsonProperty
	public void setPassword(String password) {
		this.password = password;
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
     * @param name the name
     */
    public void setUsername(String name) {
		this.username = name;
	}

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
		return lastName;
	}

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    /**
     * Gets active.
     *
     * @return the active
     */
    public int getActive() {
		return active;
	}

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(int active) {
		this.active = active;
	}

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
		return firstName;
	}

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
		return phoneNumber;
	}

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    /**
     * Gets picture path.
     *
     * @return the picture path
     */
    public String getPicturePath() {
		return picturePath;
	}

    /**
     * Sets picture path.
     *
     * @param picturePath the picture path
     */
    public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

    /**
     * Gets friends.
     *
     * @return the friends
     */
    public Collection<Friend> getFriends() {
		return friends;
	}

    /**
     * Sets friends.
     *
     * @param friends the friends
     */
    public void setFriends(Collection<Friend> friends) {
		this.friends = friends;
	}

    /**
     * Add friends.
     *
     * @param friend the friend
     */
    public void addFriends(Friend friend) {
		this.friends.add(friend);
	}

    /**
     * Remove friends.
     *
     * @param friend the friend
     */
    public void removeFriends(Friend friend) {
		this.friends.remove(friend);
	}
}
