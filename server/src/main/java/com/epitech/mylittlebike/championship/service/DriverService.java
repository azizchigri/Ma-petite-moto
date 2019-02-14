package com.epitech.mylittlebike.championship.service;

import com.epitech.mylittlebike.championship.model.Championship;
import com.epitech.mylittlebike.championship.model.ChampionshipPlayer;
import com.epitech.mylittlebike.championship.model.Driver;
import com.epitech.mylittlebike.championship.repository.ChampionshipPlayerRepository;
import com.epitech.mylittlebike.championship.repository.DriverRepository;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Driver service.
 */
@Service
public class DriverService {

    private static Map<String, Map<String, Pair<String, Integer>>> championships = new HashMap<>();

    /**
     * The Driver repository.
     */
    @Autowired
    public DriverRepository driverRepository;

    /**
     * The Championship player repository.
     */
    @Autowired
    public ChampionshipPlayerRepository championshipPlayerRepository;

    private JSONArray driverConfig = new JSONArray("[ { \"id\": \"sr:competitor:21999\", \"name\": \"Marquez, Marc\", \"gender\": \"male\", \"nationality\": \"Spain\", \"country_code\": \"ESP\", \"team\": \"Repsol Honda Team\", \"price\":100, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/7444\", \"wiki\":\"https://fr.wikipedia.org/wiki/Marc_M%C3%A1rquez\", \"number\":\"93\" }, { \"id\": \"sr:competitor:192706\", \"name\": \"Quartararo, Fabio\", \"gender\": \"male\", \"nationality\": \"French\", \"country_code\": \"FRA\", \"team\": \"Yamaha Petronas SRT\", \"price\":10, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/8520\", \"wiki\":\"https://fr.wikipedia.org/wiki/Fabio_Quartararo\", \"number\":\"20\" }, { \"id\": \"sr:competitor:245579\", \"name\": \"Mir, Joan\", \"gender\": \"male\", \"nationality\": \"Spain\", \"country_code\": \"ESP\", \"team\": \"Suzuki ECSTAR Team\", \"price\":15, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/8141\", \"wiki\":\"https://fr.wikipedia.org/wiki/Joan_Mir\", \"number\":\"36\" }, { \"id\": \"sr:competitor:93533\", \"name\": \"Bagnaia, Francesco\", \"gender\": \"male\", \"nationality\": \"Italy\", \"country_code\": \"ITA\", \"team\": \"Alma Pramac Racing\", \"price\":50, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/8273\", \"wiki\":\"https://fr.wikipedia.org/wiki/Francesco_Bagnaia\", \"number\":\"63\" }, { \"id\": \"sr:competitor:49894\", \"name\": \"Oliveira, Miguel\", \"gender\": \"male\", \"nationality\": \"Portugal\", \"country_code\": \"POR\", \"team\": \"KTM Tech3 Racing\", \"price\":15, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/7199\", \"wiki\":\"https://fr.wikipedia.org/wiki/Miguel_Oliveira_(pilote_moto)\", \"number\":\"88\" }, { \"id\": \"sr:competitor:63668\", \"name\": \"PETRUCCI, DANILO\", \"gender\": \"male\", \"nationality\": \"Italy\", \"country_code\": \"ITA\", \"team\": \"Ducati Team\", \"price\":65, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/8148\", \"wiki\":\"https://fr.wikipedia.org/wiki/Danilo_Petrucci\", \"number\":\"9\" }, { \"id\": \"sr:competitor:4540\", \"name\": \"Rossi, Valentino\", \"gender\": \"male\", \"nationality\": \"Italy\", \"country_code\": \"ITA\", \"team\": \"Movistar Yamaha MotoGP\", \"price\":75, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/158\", \"wiki\":\"https://fr.wikipedia.org/wiki/Valentino_Rossi\", \"number\":\"46\" }, { \"id\": \"sr:competitor:53145\", \"name\": \"MILLER, JACK\", \"gender\": \"male\", \"nationality\": \"Australia\", \"country_code\": \"AUS\", \"team\": \"Alma Pramac Racing\", \"price\":35, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/8049\", \"wiki\":\"https://fr.wikipedia.org/wiki/Jack_Miller_(pilote_moto)\", \"number\":\"43\" }, { \"id\": \"sr:competitor:4603\", \"name\": \"Lorenzo, Jorge\", \"gender\": \"male\", \"nationality\": \"Spain\", \"country_code\": \"ESP\", \"team\": \"Repsol Honda Team\", \"price\":80, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/6060\", \"wiki\":\"https://fr.wikipedia.org/wiki/Jorge_Lorenzo\", \"number\":\"99\" }, { \"id\": \"sr:competitor:49896\", \"name\": \"VINALES, MAVERICK\", \"gender\": \"male\", \"nationality\": \"Spain\", \"country_code\": \"ESP\", \"team\": \"Movistar Yamaha MotoGP\", \"price\":85, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/7409\", \"wiki\":\"https://fr.wikipedia.org/wiki/Maverick_Vi%C3%B1ales\", \"number\":\"12\" }, { \"id\": \"sr:competitor:49401\", \"name\": \"CRUTCHLOW, CAL\", \"gender\": \"male\", \"nationality\": \"Great Britain\", \"country_code\": \"GBR\", \"team\": \"LCR Honda MotoGP\", \"price\":45, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/8010\", \"wiki\":\"https://fr.wikipedia.org/wiki/Cal_Crutchlow\", \"number\":\"35\" }, { \"id\": \"sr:competitor:4976\", \"name\": \"Espargaro, Aleix\", \"gender\": \"male\", \"nationality\": \"Spain\", \"country_code\": \"ESP\", \"team\": \"APRILIA RACING TEAM GRESINI\", \"price\":25, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/6854\", \"wiki\":\"https://fr.wikipedia.org/wiki/Aleix_Espargar%C3%B3\", \"number\":\"41\" }, { \"id\": \"sr:competitor:63740\", \"name\": \"RINS, ALEX\", \"gender\": \"male\", \"nationality\": \"Spain\", \"country_code\": \"ESP\", \"team\": \"TEAM SUZUKI ECSTAR MOTOGP\", \"price\":55, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/8150\", \"wiki\":\"https://fr.wikipedia.org/wiki/%C3%81lex_Rins\", \"number\":\"42\" }, { \"id\": \"sr:competitor:5953\", \"name\": \"Espargaro, Pol\", \"gender\": \"male\", \"nationality\": \"Spain\", \"country_code\": \"ESP\", \"team\": \"Red Bull KTM Factory Racing\", \"price\":20, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/7086\", \"wiki\":\"https://fr.wikipedia.org/wiki/Pol_Espargar%C3%B3\", \"number\":\"44\" }, { \"id\": \"sr:competitor:59051\", \"name\": \"SYAHRIN ABDULLAH, HAFIZH\", \"gender\": \"male\", \"nationality\": \"Malaysia\", \"country_code\": \"MYS\", \"team\": \"Monster Yamaha Tech 3\", \"price\":20, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/8132\", \"wiki\":\"https://fr.wikipedia.org/wiki/Hafizh_Syahrin\", \"number\":\"55\" }, { \"id\": \"sr:competitor:145684\", \"name\": \"MORBIDELLI, FRANCO\", \"gender\": \"male\", \"nationality\": \"Italy\", \"team\":\"Yamaha Petronas SRT\", \"price\":30, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/7741\", \"wiki\":\"https://fr.wikipedia.org/wiki/Franco_Morbidelli\", \"number\":\"21\" }, { \"id\": \"sr:competitor:8003\", \"name\": \"Nakagami, Takaaki\", \"gender\": \"male\", \"nationality\": \"Japan\", \"country_code\": \"JPN\", \"team\": \"LCR Honda MotoGP\", \"price\":30, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/6976\", \"wiki\":\"https://fr.wikipedia.org/wiki/Takaaki_Nakagami\", \"number\":\"30\" }, { \"id\": \"sr:competitor:4983\", \"name\": \"Abraham, Karel\", \"gender\": \"male\", \"nationality\": \"Czech Republic\", \"country_code\": \"CZE\", \"team\": \"Reale Avintia Racing\", \"price\":10, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/6867\", \"wiki\":\"https://fr.wikipedia.org/wiki/Karel_Abraham\", \"number\":\"17\" }, { \"id\": \"sr:competitor:5557\", \"name\": \"Rabat, Esteve\", \"gender\": \"male\", \"nationality\": \"Spain\", \"country_code\": \"ESP\", \"team\": \"Reale Avintia Racing\", \"price\":40, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/7013\", \"wiki\":\"https://fr.wikipedia.org/wiki/Esteve_Rabat\", \"number\":\"53\" }, { \"id\": \"sr:competitor:34971\", \"name\": \"Zarco, Johann\", \"gender\": \"male\", \"nationality\": \"France\", \"country_code\": \"FRA\", \"team\": \"Red Bull KTM Factory Racing\", \"price\":35, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/7236\", \"wiki\":\"https://fr.wikipedia.org/wiki/Johann_Zarco\", \"number\":\"5\" }, { \"id\": \"sr:competitor:4596\", \"name\": \"Dovizioso, Andrea\", \"gender\": \"male\", \"nationality\": \"Italy\", \"country_code\": \"ITA\", \"team\": \"Ducati Team\", \"price\":90, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/5885\", \"wiki\":\"https://fr.wikipedia.org/wiki/Andrea_Dovizioso\", \"number\":\"4\" }, { \"id\": \"sr:competitor:4986\", \"name\": \"Iannone, Andrea\", \"gender\": \"male\", \"nationality\": \"Italy\", \"country_code\": \"ITA\", \"team\": \"APRILIA RACING TEAM GRESINI\", \"price\":35, \"url\":\"http://www.motogp.com/en/api/rider/photo/grid/old/6848\", \"wiki\":\"https://fr.wikipedia.org/wiki/Andrea_Iannone\", \"number\":\"29\" }] ");

    /**
     * Sets drivers.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void setupDrivers() {
        for (Object driverO : driverConfig) {
            JSONObject driver = new JSONObject(driverO.toString());
            driverRepository.save(new Driver(driver.getString("name"), driver.getString("id"), driver.getString("team"), new Long((Integer)driver.get("price")), driver.getString("url"), driver.getString("number"), driver.getString("wiki")));
        }
    }

    /**
     * Add championship.
     *
     * @param championshipId the championship id
     */
    public void addChampionship(String championshipId) {
        Map<String, Pair<String, Integer>> champ = new HashMap<>();
        List<Driver> driverList = driverRepository.findAll();
        for (Driver d: driverList) {
            champ.put(d.getSportRadarId(), null);
        }
        championships.put(championshipId, champ);
    }

    private void removeChampionship(String championshipId) {
        championships.remove(championshipId);
    }

    private Map<String, Pair<String, Integer>> finishBetManagement(Championship championship, Map<String, Pair<String, Integer>> champ, Integer champPlayerSize) {
        int driverPicked = 0;
        for (String key : champ.keySet()) {
            if (champ.get(key) != null && champ.get(key).getValue() != null)
                driverPicked += 1;
        }
        if (driverPicked != champPlayerSize * 2)
            return champ;
        for (String key : champ.keySet()) {
            if (champ.get(key) != null) {
                List<ChampionshipPlayer> players = championshipPlayerRepository.findAllByUsername(champ.get(key).getKey());
                for (ChampionshipPlayer player : players) {
                    if (player.getChampionship().getId().equals(championship.getId())) {
                        if (player != null && player.getFirstDriver() == null) {
                            player.setFirstDriver(key);
                            player.setFirstDriverBet(champ.get(key).getValue());
                        } else if (player != null && player.getSecondDriver() == null) {
                            player.setSecondDriver(key);
                            player.setSecondDriverBet(champ.get(key).getValue());
                        }
                        player.setWallet(player.getWallet() - champ.get(key).getValue());
                        championshipPlayerRepository.save(player);
                    }
                }
            }
        }
        removeChampionship(championship.getId().toString());
        return null;
    }

    /**
     * Manage bet map.
     *
     * @param championship    the championship
     * @param driverId        the driver id
     * @param player          the player
     * @param bet             the bet
     * @param champPlayerSize the champ player size
     * @return the map
     */
    public Map<String, Pair<String, Integer>> manageBet(Championship championship, String driverId, ChampionshipPlayer player, Integer bet, Integer champPlayerSize) throws Exception{
        if (!championships.containsKey(championship.getId().toString()))
            throw new Exception("Championship doesn't exist");
        Map<String, Pair<String, Integer>> champ = championships.get(championship.getId().toString());
        if (champ.get(driverId) == null || champ.get(driverId).getValue() < bet) {
            champ.put(driverId, new Pair<>(player.getUsername(), bet));
        }
        championships.put(championship.getId().toString(), champ);
        return finishBetManagement(championship, champ, champPlayerSize);
    }

    /**
     * Convert map for front map.
     *
     * @param origin the origin
     * @return the map
     */
    public Map<String, String> convertMapForFront(Map<String, Pair<String, Integer>> origin) {
        Map<String, String> res = new HashMap<>();

        if (origin == null)
            return null;
        for (String key : origin.keySet()) {
            if (origin.get(key) != null)
                res.put(key, origin.get(key).getKey());
            else
                res.put(key, null);
        }
        return res;
    }

    /**
     * Gets actual bet.
     *
     * @param championshipId the championship id
     * @return the actual bet
     */
    public Map<String, Pair<String, Integer>> getActualBet(String championshipId) {
        return championships.get(championshipId);
    }
}
