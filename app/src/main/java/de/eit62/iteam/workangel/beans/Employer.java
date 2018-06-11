package de.eit62.iteam.workangel.beans;

import android.graphics.Bitmap;

/**
 * Bean for employer.
 *
 * @author Lukas Tegethoff
 */
public class Employer {

	private int employer_ID;
	private String name;
	private String description;
	private String username;
	private Bitmap employerPicture;
	
	public Employer(int employer_ID, String name, String description, String username, Bitmap employerPicture) {
		this.employer_ID = employer_ID;
		this.name = name;
		this.description = description;
		this.username = username;
		this.employerPicture = employerPicture;
	}
}
