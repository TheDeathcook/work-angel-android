package de.eit62.iteam.workangel.beans;

public abstract class AppUser {
	
	String username;
	
	public AppUser(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
