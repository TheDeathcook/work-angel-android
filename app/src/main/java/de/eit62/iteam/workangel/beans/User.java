package de.eit62.iteam.workangel.beans;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Bean for users.
 *
 * @author Lukas Tegethoff
 */
public class User extends AppUser {
	
	private int userID;
	private String lastName;
	private String forename;
	
	public User(int userID,
				String lastName,
				String forename,
				String description,
				String username,
				Bitmap userPicture,
				List<Skill> skills) {
		super(username, userPicture, description, skills);
		this.userID = userID;
		this.lastName = lastName;
		this.forename = forename;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
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
}
