package com.zdvdev.steamgamesstats.domain.model;

@SuppressWarnings("unused")
public class Game extends BasicGame {
	private String storeLink;

	private String statsLink;

	private String globalStatsLink;

	private float hoursOnRecord;

	private float hoursLast2Weeks;

	public String getStoreLink() {
		return storeLink;
	}

	public void setStoreLink(String storeLink) {
		this.storeLink = storeLink;
	}

	public String getStatsLink() {
		return statsLink;
	}

	public void setStatsLink(String statsLink) {
		this.statsLink = statsLink;
	}

	public String getGlobalStatsLink() {
		return globalStatsLink;
	}

	public void setGlobalStatsLink(String globalStatsLink) {
		this.globalStatsLink = globalStatsLink;
	}

	public float getHoursOnRecord() {
		return hoursOnRecord;
	}

	public void setHoursOnRecord(float hoursOnRecord) {
		this.hoursOnRecord = hoursOnRecord;
	}

	public float getHoursLast2Weeks() {
		return hoursLast2Weeks;
	}

	public void setHoursLast2Weeks(float hoursLast2Weeks) {
		this.hoursLast2Weeks = hoursLast2Weeks;
	}
}