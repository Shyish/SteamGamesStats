package com.zdvdev.steamgamesstats.datamodel.model;

public class Game {
	public String logo;

	public String storeLink;

	public String statsLink;

	public String globalStatsLink;

	public String name;

	public String hoursOnRecord;

	public String appID;

	public String hoursLast2Weeks;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHoursOnRecord() {
		return hoursOnRecord;
	}

	public void setHoursOnRecord(String hoursOnRecord) {
		this.hoursOnRecord = hoursOnRecord;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getHoursLast2Weeks() {
		return hoursLast2Weeks;
	}

	public void setHoursLast2Weeks(String hoursLast2Weeks) {
		this.hoursLast2Weeks = hoursLast2Weeks;
	}
}