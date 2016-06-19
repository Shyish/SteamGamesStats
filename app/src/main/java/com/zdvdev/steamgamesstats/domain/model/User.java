package com.zdvdev.steamgamesstats.domain.model;

import android.util.Pair;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class User implements Serializable {
    private long steamID64;

    private List<Game> games;

    private String steamID;

    public long getSteamID64() {
        return steamID64;
    }

    public void setSteamID64(long steamID64) {
        this.steamID64 = steamID64;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getSteamID() {
        return steamID;
    }

    public void setSteamID(String steamID) {
        this.steamID = steamID;
    }

    public ArrayList<UsersHoursPerGame> getUserHoursPerGame() {
        int gamesSize = games.size();
        ArrayList<UsersHoursPerGame> uHPGList = new ArrayList<>(gamesSize);
        for (int j = 0; j < gamesSize; j++) {
            Game game = games.get(j);
            ArrayList<Pair<String, Float>> usersHours = new ArrayList<>(1);
            usersHours.add(new Pair<>(steamID, game.getHoursOnRecord()));

            AbstractCollection<UsersHoursPerGame> commonGames;
            uHPGList.add(new UsersHoursPerGame(game, usersHours));
        }
        return uHPGList;
    }
}