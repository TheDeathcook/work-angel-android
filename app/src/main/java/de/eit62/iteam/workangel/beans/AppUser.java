package de.eit62.iteam.workangel.beans;

import android.graphics.Bitmap;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Abstract super class for a general user of the app.
 *
 * @author Lukas Tegthoff
 */
public abstract class AppUser {
	
	@SerializedName("Username")
	private String username;
	
	@SerializedName("UserPicture")
	private Bitmap picture;
	
	@SerializedName("Description")
	private String description;
	
	private List<Skill> skills;
	
	public AppUser() {
		// necessary for Gson
	}
	
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
