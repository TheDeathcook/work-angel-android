package de.eit62.iteam.workangel.beans;

import android.graphics.Bitmap;

import java.util.List;

public abstract class AppUser {
	
	private String username;
	private Bitmap picture;
	private String description;
	private List<Skill> skills;
	
	public AppUser(String username, Bitmap picture, String description, List<Skill> skills) {
		this.username = username;
		this.picture = picture;
		this.description = description;
		this.skills = skills;
		
	}
	
	public List<Skill> getSkills() {
		return skills;
	}
	
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	public Bitmap getPicture() {
		return picture;
	}
	
	public void setPicture(Bitmap picture) {
		this.picture = picture;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
