package com.epitech.mylittlebike.user.controller;

import javax.validation.Valid;

import com.epitech.mylittlebike.user.model.ApplicationUser;
import com.epitech.mylittlebike.user.model.Friend;
import com.epitech.mylittlebike.user.repository.ApplicationFriendRepository;
import com.epitech.mylittlebike.user.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ApplicationFriendRepository applicationFriendRepository;

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Instantiates a new User controller.
     *
     * @param applicationUserRepository the application user repository
     * @param bCryptPasswordEncoder     the b crypt password encoder
     */
    public UserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Sign up response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping("/sign-up")
    public ResponseEntity<ApplicationUser> signUp(@RequestBody @Valid ApplicationUser user) {
    	if (applicationUserRepository.findByUsername(user.getUsername()) != null)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (user.getPicturePath() == null || user.getPicturePath().isEmpty())
            user.setPicturePath("https://www.w3schools.com/howto/img_avatar.png");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
        user.setPassword("");
        return ResponseEntity.ok(user);
    }

    /**
     * Update response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<ApplicationUser> update(@RequestBody ApplicationUser user) {
        ApplicationUser entity = applicationUserRepository.findByUsername(user.getUsername());
        if (entity == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (user.getFirstName() != null && !user.getFirstName().isEmpty())
            entity.setFirstName(user.getFirstName());
        if (user.getLastName() != null && !user.getLastName().isEmpty())
            entity.setLastName(user.getLastName());
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty())
            entity.setPhoneNumber(user.getPhoneNumber());
        if (user.getFriends() != null && !user.getFriends().isEmpty())
            entity.setFriends(user.getFriends());
        if (user.getPicturePath() != null && !user.getPicturePath().isEmpty())
            entity.setPicturePath(user.getPicturePath());
        if (user.getPassword() != null && !user.getPassword().isEmpty())
            entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(entity);
        return ResponseEntity.ok(entity);
    }

    /**
     * Add friend response entity.
     *
     * @param friendName the friend name
     * @return the response entity
     */
    @PostMapping("/friend/add")
    public ResponseEntity<ApplicationUser> addFriend(@RequestParam("friendName") String friendName) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser entity = applicationUserRepository.findByUsername(username);
        ApplicationUser friend = applicationUserRepository.findByUsername(friendName);
        if (entity == null || friend == null || friendName.equals(username))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        for (Friend f : entity.getFriends()) {
            if (f.getFriend().getUsername().equals(friend.getUsername()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Friend friendOne = new Friend();
        friendOne.setFriend(friend);
        entity.addFriends(friendOne);
        applicationFriendRepository.save(friendOne);
        friendOne = new Friend();
        friendOne.setFriend(entity);
        friend.addFriends(friendOne);
        applicationFriendRepository.save(friendOne);
        return ResponseEntity.ok(entity);
    }

    /**
     * Delete friend response entity.
     *
     * @param friendName the friend name
     * @return the response entity
     */
    @PostMapping("/friend/delete")
    public ResponseEntity<ApplicationUser> deleteFriend(@RequestParam String friendName) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser entity = applicationUserRepository.findByUsername(username);
        ApplicationUser friend = applicationUserRepository.findByUsername(friendName);
        if (entity == null || friend == null || entity.getFriends() == null || friend.getFriends() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Friend relA = null;
        Friend relB = null;
        for (Friend fr : entity.getFriends()) {
            if (fr.getFriend().getUsername().equals(friendName)) {
                relA = fr;
                break;
            }
        }
        for (Friend fr : friend.getFriends()) {
            if (fr.getFriend().getUsername().equals(entity.getUsername())) {
                relB = fr;
                break;
            }
        }
        if (relA == null || relB == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        entity.removeFriends(relA);
        friend.removeFriends(relB);
        applicationFriendRepository.delete(relA);
        applicationFriendRepository.delete(relB);
        return ResponseEntity.ok(entity);
    }

    /**
     * Gets friends.
     *
     * @param username the username
     * @return the friends
     */
    @GetMapping("/friend")
    public ResponseEntity<Collection<Friend>> getFriends(@RequestParam("username") String username) {
        ApplicationUser entity = applicationUserRepository.findByUsername(username);
        if (entity == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok(entity.getFriends());
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    @GetMapping("")
    public ResponseEntity<ApplicationUser> getUser(@RequestParam("username") String username) {
    	ApplicationUser entity = applicationUserRepository.findByUsername(username);
    	if (entity == null)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok(entity);
    }
}