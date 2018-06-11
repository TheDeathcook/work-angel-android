package de.eit62.iteam.workangel.beans;

import android.graphics.Bitmap;

/**
 * Bean for users.
 *
 * @author Lukas Tegethoff
 */
public class User {
	
	private int user_ID;
	private String lastName;
	private String forename;
	private String description;
	private String username;
	private Bitmap userPicture;
	
	public User(int user_ID, String lastName, String forename, String description, String username, Bitmap userPicture) {
		this.user_ID = user_ID;
		this.lastName = lastName;
		this.forename = forename;
		this.description = description;
		this.username = username;
		this.userPicture = userPicture;
	}
}
