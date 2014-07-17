package com.zdvdev.steamgamesstats.datamodel.model;

import java.io.Serializable;
import java.util.List;

public class GamesList implements Serializable {
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
}