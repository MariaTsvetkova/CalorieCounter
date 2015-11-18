package com.example.weightcontroller.database;

import java.io.Serializable;

public class Calories implements Serializable {
	private long id;
	private String activitySport;
	private String date;
	private String time;
	private String calorieValue;
	private String userId;

	public Calories() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getActivitySport() {
		return activitySport;
	}

	public void setActivitySport(String activitySport) {
		this.activitySport = activitySport;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCalorieValue() {
		return calorieValue;
	}

	public void setCalorieValue(String calorieValue) {
		this.calorieValue = calorieValue;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
