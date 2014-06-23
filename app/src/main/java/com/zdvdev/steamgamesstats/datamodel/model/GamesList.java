package com.zdvdev.steamgamesstats.datamodel.model;

public class GamesList {
	private String steamID64;

	private Game[] games;

	private String steamID;

	public String getSteamID64() {
		return steamID64;
	}

	public void setSteamID64(String steamID64) {
		this.steamID64 = steamID64;
	}

	public Game[] getGames() {
		return games;
	}

	public void setGames(Game[] games) {
		this.games = games;
	}

	public String getSteamID() {
		return steamID;
	}

	public void setSteamID(String steamID) {
		this.steamID = steamID;
	}
}