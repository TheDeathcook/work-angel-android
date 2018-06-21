package de.eit62.iteam.workangel.beans;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Skill {
	
	@SerializedName("Skill_ID")
	private int skill_ID;
	
	@SerializedName("Name")
	private String name;
	
	@SerializedName("Description")
	private String description;
	
	public Skill() {
		// necessary for Gson
	}
	
	public Skill(int skill_ID, String name, String description) {
		this.skill_ID = skill_ID;
		this.name = name;
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Skill skill = (Skill) o;
		return skill_ID == skill.skill_ID;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(skill_ID);
	}
}
