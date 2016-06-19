package com.zdvdev.steamgamesstats.domain.model;

import android.util.Pair;

import com.zdvdev.steamgamesstats.domain.model.BasicGame;

import java.util.ArrayList;

public class UsersHoursPerGame {
    public BasicGame game;
    public ArrayList<Pair<String, Float>> usersHours;

    public UsersHoursPerGame(BasicGame game, ArrayList<Pair<String, Float>> usersHours) {
        this.game = game;
        this.usersHours = usersHours;
    }
}
