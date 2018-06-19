package de.eit62.iteam.workangel.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.eit62.iteam.workangel.beans.Employer;
import de.eit62.iteam.workangel.beans.Match;
import de.eit62.iteam.workangel.beans.Skill;
import de.eit62.iteam.workangel.beans.User;

/**
 *
 * @author Felix Bergemann
 */
public class Util {
	
	private static final String TAG = "Utils";
	
	public static List<User> loadProfiles(Context context) {
		try {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			JSONArray array = new JSONArray(loadJSONFromAsset(context, "profiles.json"));
			List<User> userList = new ArrayList<>();
			for (int i = 0; i < array.length(); i++) {
				User user = gson.fromJson(array.getString(i), User.class);
				userList.add(user);
			}
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String loadJSONFromAsset(Context context, String jsonFileName) {
		String json = null;
		InputStream is = null;
		try {
			AssetManager manager = context.getAssets();
			Log.d(TAG, "path " + jsonFileName);
			is = manager.open(jsonFileName);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
	
	public static ArrayList<Match> createMatchListForUser(User user,
														  ArrayList<Employer> empList,
														  int minComp) {
		ArrayList<Match> returnMatchList = new ArrayList<>();
		Match tempMatch;
		for (Employer emp : empList) {
			tempMatch = createNewMatch(emp, user);
			if (tempMatch.getCompatibility() >= minComp) {
				returnMatchList.add(tempMatch);
			}
		}
		return returnMatchList;
	}
	
	private static List<Match> createMatchListForEmployer(Employer employer,
														  List<User> userList,
														  int minComp) {
		List<Match> returnMatchList = new ArrayList<>();
		Match tempMatch;
		for (User user : userList) {
			tempMatch = createNewMatch(employer, user);
			if (tempMatch.getCompatibility() >= minComp) {
				returnMatchList.add(tempMatch);
			}
		}
		return returnMatchList;
	}
	
	private static Match createNewMatch(Employer employer, User user) {
		int compatibility = 0;
		List<Skill> userSkills = user.getSkills();
		List<Skill> employerSkills = employer.getSkills();
		
		for (Skill singleEmployerSkill : employerSkills) {
			for (Skill singleUserSkill : userSkills) {
				if (singleEmployerSkill == singleUserSkill) {
					compatibility = compatibility + 1;
				}
			}
		}
		return new Match(employer.getEmployerID(), user.getUserID(), compatibility, null, null);
	}
	
	private static List<Match> orderMatchesCompability(List<Match> matchList) {
		matchList.sort(Comparator.comparingInt(Match::getCompatibility));
		return matchList;
	}
	
}
