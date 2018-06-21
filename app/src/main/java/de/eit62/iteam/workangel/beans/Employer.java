package de.eit62.iteam.workangel.beans;

import android.graphics.Bitmap;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Bean for employer.
 *
 * @author Lukas Tegethoff
 */
public class Employer extends AppUser {
	
	@SerializedName("Employer_ID")
	private int employerID;
	
	@SerializedName("Name")
	private String name;
	
	public Employer() {
		// necessary for Gson
	}
	
	public Employer(int employerID,
					String name,
					String description,
					String username,
					Bitmap employerPicture,
					List<Skill> skills) {
		super(username, employerPicture, description, skills);
		this.employerID = employerID;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getEmployerID() {
		
		return employerID;
	}
	
	public void setEmployerID(int employerID) {
		this.employerID = employerID;
	}
}
