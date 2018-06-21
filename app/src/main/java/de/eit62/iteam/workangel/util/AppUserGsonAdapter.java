package de.eit62.iteam.workangel.util;

import com.google.gson.*;
import de.eit62.iteam.workangel.beans.AppUser;

import java.lang.reflect.Type;

/**
 * An adapter to handle the JSON deserialization of {@link AppUser}s (including {@link de.eit62.iteam.workangel.beans.Employer}s and {@link de.eit62.iteam.workangel.beans.User}s).
 *
 * @author Lukas Tegethoff
 */
public class AppUserGsonAdapter implements JsonDeserializer<AppUser> {
	
	
	@Override
	public AppUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String className;
		boolean isUser = jsonObject.entrySet()
								   .stream()
								   .anyMatch(stringJsonElementEntry -> stringJsonElementEntry.getKey()
																							 .equalsIgnoreCase("lastname"));
		if (isUser) {
			className = "de.eit62.iteam.workangel.beans.User";
		} else {
			className = "de.eit62.iteam.workangel.beans.Employer";
		}
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException(e.getMessage(), e);
		}
		return context.deserialize(jsonObject, clazz);
	}
}
