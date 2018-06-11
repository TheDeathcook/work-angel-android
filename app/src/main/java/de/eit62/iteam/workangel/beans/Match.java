package de.eit62.iteam.workangel.beans;

/**
 * Bean for match.
 *
 * @author Lukas Tegethoff
 */
public class Match {
	
	private int employer_ID;
	private int user_ID;
	private int compatiblity;
	private boolean user_accepted;
	private boolean employer_accepted;
	
	public Match(int employer_ID, int user_ID, int compatiblity, boolean user_accepted, boolean employer_accepted) {
		this.employer_ID = employer_ID;
		this.user_ID = user_ID;
		this.compatiblity = compatiblity;
		this.user_accepted = user_accepted;
		this.employer_accepted = employer_accepted;
	}
}
