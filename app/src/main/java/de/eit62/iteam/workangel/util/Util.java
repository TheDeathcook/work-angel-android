package de.eit62.iteam.workangel.util;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.eit62.iteam.workangel.beans.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 *
 * @author Felix Bergemann
 */
public class Util {
	
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(AppUser.class, new AppUserGsonAdapter()).create();
	
	public static User getUserForMatch(Match match, SharedPreferences preferences) {
		int userId = match.getUserID();
		String userListJson = preferences.getString("users", null);
		if (userListJson != null) {
			Type userListType = new TypeToken<List<User>>() {}.getType();
			List<User> userList = GSON.fromJson(userListJson, userListType);
			return userList.stream().filter((user) -> user.getUserID() == userId).findFirst().orElse(null);
		}
		return null;
	}
	
	public static Employer getEmployerForMatch(Match match, SharedPreferences preferences) {
		int employerId = match.getEmployerID();
		String employerListJson = preferences.getString("employers", null);
		if (employerListJson != null) {
			Type employerListType = new TypeToken<List<Employer>>() {}.getType();
			List<Employer> employerList = GSON.fromJson(employerListJson, employerListType);
			return employerList.stream().filter((employer) -> employer.getEmployerID() == employerId).findFirst().orElse(null);
		}
		return null;
	}
	
	public static List<Match> createMatchListForUser(User user,
													 List<Employer> empList,
													 int minComp) {
		List<Match> returnMatchList = new ArrayList<>();
		for (Employer emp : empList) {
			Match tempMatch = createNewMatch(emp, user);
			if (tempMatch.getCompatibility() >= minComp) {
				returnMatchList.add(tempMatch);
			}
		}
		returnMatchList.sort(Comparator.comparingInt(Match::getCompatibility));
		return returnMatchList;
	}
	
	public static List<Match> createMatchListForEmployer(Employer employer,
														 List<User> userList,
														 int minComp) {
		List<Match> returnMatchList = new ArrayList<>();
		for (User user : userList) {
			Match tempMatch = createNewMatch(employer, user);
			if (tempMatch.getCompatibility() >= minComp) {
				returnMatchList.add(tempMatch);
			}
		}
		returnMatchList.sort(Comparator.comparingInt(Match::getCompatibility));
		return returnMatchList;
	}
	
	private static Match createNewMatch(Employer employer, User user) {
		int compatibility = 0;
		List<Skill> userSkills = user.getSkills();
		List<Skill> employerSkills = employer.getSkills();
		
		if (userSkills != null && employerSkills != null) {
			for (Skill employerSkill : employerSkills) {
				for (Skill userSkill : userSkills) {
					if (employerSkill.equals(userSkill)) {
						compatibility++;
					}
				}
			}
		}
		return new Match(employer.getEmployerID(), user.getUserID(), compatibility, null, null);
	}
}
