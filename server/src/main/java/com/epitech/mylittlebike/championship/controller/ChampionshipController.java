package com.epitech.mylittlebike.championship.controller;

import com.epitech.mylittlebike.championship.model.Championship;
import com.epitech.mylittlebike.championship.model.ChampionshipPlayer;
import com.epitech.mylittlebike.championship.model.Driver;
import com.epitech.mylittlebike.championship.repository.ChampionshipPlayerRepository;
import com.epitech.mylittlebike.championship.repository.ChampionshipRepository;
import com.epitech.mylittlebike.championship.repository.DriverRepository;
import com.epitech.mylittlebike.championship.service.DriverService;
import com.epitech.mylittlebike.user.model.ApplicationUser;
import com.epitech.mylittlebike.user.model.Friend;
import com.epitech.mylittlebike.user.repository.ApplicationFriendRepository;
import com.epitech.mylittlebike.user.repository.ApplicationUserRepository;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * The type Championship controller.
 */
@RestController
@RequestMapping("/championship")
public class ChampionshipController {

    @Autowired
    private ChampionshipPlayerRepository championshipPlayerRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverService driverService;

    /**
     * Create response entity.
     *
     * @param championship the championship
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Championship> create(@RequestBody @Valid Championship championship) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
    	if (applicationUser == null)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        championship.setUser(applicationUser);
        championshipRepository.save(championship);
        ChampionshipPlayer player = new ChampionshipPlayer();
        player.setUsername(username);
        player.setWallet(championship.getWallet());
        player.setScore(0L);
        player.setChampionship(championship);
        player.setStatus(1L);
    	championshipPlayerRepository.save(player);
    	driverService.addChampionship(championship.getId().toString());
        return ResponseEntity.ok(championship);
    }

    /**
     * Update response entity.
     *
     * @param championship the championship
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<Championship> update(@RequestBody @Valid Championship championship) {
        if (!championshipRepository.findById(championship.getId()).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Championship entity = championshipRepository.findById(championship.getId()).get();
        entity.setDuration(championship.getDuration());
        entity.setEndDate(championship.getEndDate());
        entity.setWallet(championship.getWallet());
        entity.setChampionshipPlayer(championship.getChampionshipPlayer());
        entity.setStartDate(championship.getStartDate());
        championshipRepository.save(entity);
        return ResponseEntity.ok(entity);
    }

    /**
     * Get response entity.
     *
     * @return the response entity
     */
    @GetMapping("")
    public ResponseEntity<List<Championship>> get() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Championship> result = new ArrayList<>();
        List<ChampionshipPlayer> players = championshipPlayerRepository.findAllByUsername(username);
        for (ChampionshipPlayer p : players) {
            Championship c = p.getChampionship();
            c.setChampionshipPlayer(championshipPlayerRepository.findAllByChampionship(c));
            result.add(c);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Add player to championship response entity.
     *
     * @param championshipId the championship id
     * @param wallet         the wallet
     * @param username       the username
     * @return the response entity
     */
    @PostMapping("/addPlayer")
    public ResponseEntity<Championship> addPlayerToChampionship(@RequestParam("championshipId") Long championshipId, @RequestParam("wallet") Long wallet, @RequestParam("username") String username) {
        if (!championshipRepository.findById(championshipId).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Championship champ = championshipRepository.findById(championshipId).get();
        ChampionshipPlayer player = new ChampionshipPlayer();
        player.setUsername(username);
        player.setWallet(wallet);
        player.setScore(0L);
        player.setChampionship(champ);
        player.setStatus(0L);
        championshipPlayerRepository.save(player);
        return ResponseEntity.ok(champ);
    }

    /**
     * Validate participation response entity.
     *
     * @param championshipId the championship id
     * @return the response entity
     */
    @PostMapping("/validate")
    public ResponseEntity<Championship> validateParticipation(@RequestParam("championshipId") Long championshipId) {
        if (!championshipRepository.findById(championshipId).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        List<ChampionshipPlayer> resultP = new ArrayList<>();
        Championship champ = championshipRepository.findById(championshipId).get();
        List<ChampionshipPlayer> players = championshipPlayerRepository.findAllByChampionship(champ);
        for (ChampionshipPlayer p : players) {
            if (p.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
                p.setStatus(1L);
                championshipPlayerRepository.save(p);
                break;
            }
            resultP.add(p);
        }
        champ.setChampionshipPlayer(resultP);
        return ResponseEntity.ok(champ);
    }

    /**
     * Bet response entity.
     *
     * @param championshipId the championship id
     * @param driverId       the driver id
     * @param price          the price
     * @return the response entity
     */
    @PostMapping("/bet")
    public ResponseEntity<Map<String, String>> bet(@RequestParam("championshipId") Long championshipId, @RequestParam("driverId") String driverId, @RequestParam("price") Integer price) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!championshipRepository.findById(championshipId).isPresent() || username == null || username.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Championship champ = championshipRepository.findById(championshipId).get();
        Integer size = championshipPlayerRepository.findAllByChampionship(champ).size();
        ChampionshipPlayer player = null;
        List<ChampionshipPlayer> players = championshipPlayerRepository.findAllByUsername(username);
        for (ChampionshipPlayer p : players) {
            if (p.getChampionship().getId().equals(champ.getId())) {
                player = p;
                break;
            }
        }
        Map<String, Pair<String, Integer>> res;
        try {
            res = driverService.manageBet(champ, driverId, player, price, size);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(driverService.convertMapForFront(res));
    }

    /**
     * Gets bet.
     *
     * @param championshipId the championship id
     * @return the bet
     */
    @GetMapping("/bet")
    public ResponseEntity<Map<String, String>> getBet(@RequestParam("championshipId") Long championshipId) {
        return ResponseEntity.ok(driverService.convertMapForFront(driverService.getActualBet(championshipId.toString())));
    }

    /**
     * playerInfo response entity.
     *
     * @param championshipId the championship id
     * @return the response entity
     */
    @GetMapping("/playerInfo")
    public ResponseEntity<ChampionshipPlayer> playerInfo(@RequestParam("championshipId") Long championshipId) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!championshipRepository.findById(championshipId).isPresent() || username == null || username.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Championship champ = championshipRepository.findById(championshipId).get();
        for (ChampionshipPlayer p : champ.getChampionshipPlayer()) {
            if (p.getUsername().equals(username))
                return ResponseEntity.ok(p);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Gets drivers.
     *
     * @return the drivers
     */
    @GetMapping("/driver")
    public ResponseEntity<List<Driver>> getDrivers() {
        return ResponseEntity.ok(driverRepository.findAll());
    }

    /**
     * championship response entity.
     *
     * @param championshipId the championship id
     * @return the response entity
     */
    @GetMapping("/findOne")
    public ResponseEntity<Championship> getChampionshipStatus(@RequestParam("championshipId") Long championshipId) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!championshipRepository.findById(championshipId).isPresent() || username == null || username.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Championship champ = championshipRepository.findById(championshipId).get();
        champ.setChampionshipPlayer(championshipPlayerRepository.findAllByChampionship(champ));
        return ResponseEntity.ok(champ);
    }
}