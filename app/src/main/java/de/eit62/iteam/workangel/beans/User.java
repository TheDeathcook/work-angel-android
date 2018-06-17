package de.eit62.iteam.workangel.beans;

import android.graphics.Bitmap;

/**
 * Bean for users.
 *
 * @author Lukas Tegethoff
 */
public class User extends AppUser {
	
	private int user_ID;
	private String lastName;
	private String forename;
	private String description;
	private Bitmap userPicture;
	
	public User(int user_ID, String lastName, String forename, String description,
	
				String username, Bitmap userPicture) {
		super(username);
		this.user_ID = user_ID;
		this.lastName = lastName;
		this.forename = forename;
		this.description = description;
		this.username = username;
		this.userPicture = userPicture;
	}
	
	public int getUser_ID() {
		return user_ID;
	}
	
	public void setUser_ID(int user_ID) {
		this.user_ID = user_ID;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getForename() {
		return forename;
	}
	
	public void setForename(String forename) {
		this.forename = forename;
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
	
	public Bitmap getUserPicture() {
		return userPicture;
	}
	
	public void setUserPicture(Bitmap userPicture) {
		this.userPicture = userPicture;
	}
}
