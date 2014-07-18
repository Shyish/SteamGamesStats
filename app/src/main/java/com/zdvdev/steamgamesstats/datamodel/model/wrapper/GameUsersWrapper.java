package com.zdvdev.steamgamesstats.datamodel.model.wrapper;

import android.util.Pair;
import com.zdvdev.steamgamesstats.datamodel.model.BasicGame;

import java.util.ArrayList;

/**
 * Created with Android Studio.
 *
 * @author aballano
 *         Date: 18/07/14
 */
public class GameUsersWrapper {
	public BasicGame game;
	public ArrayList<Pair<String, Float>> usersHours;

	public GameUsersWrapper(BasicGame game, ArrayList<Pair<String, Float>> usersHours) {
		this.game = game;
		this.usersHours = usersHours;
	}
}
