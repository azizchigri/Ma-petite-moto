package com.epitech.mylittlebike.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The type Friend.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private ApplicationUser friend;

    @Column(name = "active")
    private int active;

    /**
     * Gets friend.
     *
     * @return the friend
     */
    public ApplicationUser getFriend() {
        return friend;
    }

    /**
     * Sets friend.
     *
     * @param friend the friend
     */
    public void setFriend(ApplicationUser friend) {
        this.friend = friend;
    }
}
