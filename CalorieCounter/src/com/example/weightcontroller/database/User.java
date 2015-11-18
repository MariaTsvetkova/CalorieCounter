package com.example.weightcontroller.database;

import java.io.Serializable;

public class User implements Serializable {
	private long id;
	private String name;
	private String birthDate;
	private String height;
	private String weight;
	private String photograph;
	private String professionId;
	private String sex;

	public User() {
	}

	public User(String name, String age, String height, String weight,
			String photography, String professionId, String sex) {
		this.name = name;
		this.birthDate = age;
		this.height = height;
		this.weight = weight;
		this.photograph = photography;
		this.professionId = professionId;
		this.sex = sex;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getBirthDate() {
		return this.birthDate;
	}

	public String getHeight() {
		return this.height;
	}

	public String getWeight() {
		return this.weight;
	}

	public String getPhotograph() {
		return this.photograph;
	}

	public String getProfessionId() {
		return this.professionId;
	}

	public void setProfessionId(String i) {
		this.professionId = i;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setPhotograph(String photograph) {
		this.photograph = photograph;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
