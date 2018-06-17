package de.eit62.iteam.workangel.beans;

import android.graphics.Bitmap;

/**
 * Bean for employer.
 *
 * @author Lukas Tegethoff
 */
public class Employer extends AppUser {
	
	private int employer_ID;
	private String name;
	private String description;
	private Bitmap employerPicture;
	
	public Employer(int employer_ID,
					String name,
					String description,
					String username,
					Bitmap employerPicture) {
		super(username);
		this.employer_ID = employer_ID;
		this.name = name;
		this.description = description;
		this.username = username;
		this.employerPicture = employerPicture;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public Bitmap getEmployerPicture() {
		return employerPicture;
	}
	
	public void setEmployerPicture(Bitmap employerPicture) {
		this.employerPicture = employerPicture;
	}
	
	public int getEmployer_ID() {
		
		return employer_ID;
	}
	
	public void setEmployer_ID(int employer_ID) {
		this.employer_ID = employer_ID;
	}
}
