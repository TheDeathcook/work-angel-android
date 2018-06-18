package de.eit62.iteam.workangel.beans;

/**
 * Bean for match.
 *
 * @author Lukas Tegethoff
 */
public class Match {
	
	private int employerID;
	private int userID;
	private int compatibility;
	private Boolean userAccepted;
	private Boolean employerAccepted;
	
	public Match(int employerID,
				 int userID,
				 int compatibility,
				 Boolean userAccepted,
				 Boolean employerAccepted) {
		this.employerID = employerID;
		this.userID = userID;
		this.compatibility = compatibility;
		this.userAccepted = userAccepted;
		this.employerAccepted = employerAccepted;
	}
	
	public int getEmployerID() {
		return employerID;
	}
	
	public void setEmployerID(int employerID) {
		this.employerID = employerID;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getCompatibility() {
		return compatibility;
	}
	
	public void setCompatibility(int compatibility) {
		this.compatibility = compatibility;
	}
	
	public Boolean isUserAccepted() {
		return userAccepted;
	}
	
	public void setUserAccepted(Boolean userAccepted) {
		this.userAccepted = userAccepted;
	}
	
	public Boolean isEmployerAccepted() {
		return employerAccepted;
	}
	
	public void setEmployerAccepted(Boolean employerAccepted) {
		this.employerAccepted = employerAccepted;
	}
}
