package com.epitech.mylittlebike.championship_results.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Update championship results.
 */
@Component
public class UpdateChampionshipResults {

    private static String seasonId = null;

    private static String championshipId = null;

    private static Date championshipDate = null;

    private JSONObject sendRequest(String requestURL, String method) throws IOException {
        URL url = new URL(requestURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setInstanceFollowRedirects(false);
        int status = con.getResponseCode();
        if (status != 200) {
            con.disconnect();
            return null;
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        JSONObject json = new JSONObject(content.toString());
        con.disconnect();
        return json;
    }

    /**
     * Get all motogp complete championship
     * @return
     */
    private Map<String, Object> getMotoGPSeasonList() {
        Map<String, Object> result = new HashMap<>();
        JSONObject obj = null;
        try {
            obj = sendRequest("http://api.sportradar.us/motogp/trial/v2/en/seasons.json?api_key=6r6r8wu659p5u2dbzejtqk3n", "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray array = (JSONArray) obj.get("stages");
        for (int i = 0; i < array.length(); i++) {
            JSONObject row = array.getJSONObject(i);
            if (row.getString("description").contains("MotoGP"))
                result.put(row.getString("description"), row);
        }
        return result;
    }

    /**
     * Get championshipInfo
     * @param id
     * @return
     */
    private JSONObject getSeasonDetails(String id) {
        JSONObject obj = null;
        try {
            obj = sendRequest("https://api.sportradar.us/motogp/trial/v2/en/sport_events/" + id + "/schedule.json?api_key=6r6r8wu659p5u2dbzejtqk3n", "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * Get championship rank
     * @param id
     * @return
     */
    private JSONArray getChampionshipResult(String id) {
        JSONObject obj = null;
        try {
            obj = sendRequest("https://api.sportradar.us/motogp/trial/v2/en/sport_events/" + id + "/summary.json?api_key=6r6r8wu659p5u2dbzejtqk3n", "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (JSONArray) obj.get("competitors");
    }

    private boolean isLastChampionship(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date result = null;
        try {
            result = df.parse(date);
        } catch (ParseException e) {
        }
        boolean res = result != null && (championshipDate == null || (result.after(championshipDate) && result.before(new Date())));
        if (res)
            championshipDate = result;
        return res;
    }

    /**
     * Update season each year
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateSeason() {
        Map<String, Object> result = getMotoGPSeasonList();
        String k = null;
        for (String key : result.keySet()) {
            if (key.contains(new SimpleDateFormat("yyyy").format(new Date()))) {
                k = key;
                break;
            }
        }
        if (k == null)
            return;
        JSONObject o = new JSONObject(result.get(k).toString());
        seasonId = o.get("id").toString();
    }

    /**
     * Update current championship each week
     */
    @Scheduled(cron = "0 0 12 * * MON")
    public void updateChamp() {
        JSONArray result = (JSONArray) getSeasonDetails(seasonId).get("stages");
        for (Object o : result) {
            JSONObject json = new JSONObject(o.toString());
            if (isLastChampionship(json.getString("scheduled_end"))) {
                championshipId = json.getString("id");
            }
        }
    }

    /**
     * Update championship results each week
     */
    @Scheduled(cron = "0 0 12 * * MON")
    public void updateUsersScores() {
        JSONArray rank = getChampionshipResult(championshipId);
        // a finir
    }

    /**
     * Init data.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        updateSeason();
        updateChamp();
        int i = 0;
    }
}
