package de.eit62.iteam.workangel.webservice;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import de.eit62.iteam.workangel.beans.AppUser;
import de.eit62.iteam.workangel.beans.Employer;
import de.eit62.iteam.workangel.beans.User;
import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lukas Tegethoff
 */
public class WAServiceClient {
	
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final String BASE_URL = "http://192.168.178.78:8000/api/";
	
	private static WAServiceClient instance = new WAServiceClient();
	
	private static Gson gson = new Gson();
	
	private WAServiceClient() {
		// just declared so it is private
	}
	
	/**
	 * Gets the instance of the {@code WAServiceClient}.
	 *
	 * @return an instance of {@code WAServiceClient}
	 */
	public static WAServiceClient getInstance() {
		if (instance == null) {
			instance = new WAServiceClient();
		}
		return instance;
	}
	
	/**
	 * Initiates the login of the user and, if successful, returns an object of either
	 * {@link Employer} if the user is an employer or {@link User} if not.
	 *
	 * @param username the username of the current user
	 * @param password the password of the current user as {@code char[]} for safety reasons
	 * @return either {@link Employer} if the user is an employer or {@link User} if not.
	 * {@code null} if the call was not successful.
	 */
	@Nullable
	public AppUser loginUser(String username, char[] password) {
		String json = "{\"user\":\"" + username + "\",\"password\":\"" + Arrays.toString(password) + "\"}";
		String user = getJson(Endpoints.LOGIN_USER, json);
		AppUser loggedInUser = null;
		if (user != null) {
			if (user.toLowerCase()
					.contains("lastname")) {
				// if there is a last name, the user can only be a normal user
				loggedInUser = gson.fromJson(user, User.class);
			} else {
				// conversely, if there is no last name, the user must be an employer
				loggedInUser = gson.fromJson(user, Employer.class);
			}
		}
		return loggedInUser;
	}
	
	public boolean updateUser(User user) {
		byte[] pictureAsBytes = null;
		// convert Bitmap to byte array so it can be saved as blob in DB.
		if (user.getPicture() != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			user.getPicture()
				.compress(Bitmap.CompressFormat.JPEG, 70, stream);
			pictureAsBytes = stream.toByteArray();
		}
		String json = "{" +
				"\"username\":\"" + user.getUsername() + "\"" +
				"\"lastname\":\"" + user.getLastName() + "\"" +
				"\"forename\":\"" + user.getForename() + "\"" +
				"\"descr\":\"" + user.getDescription() + "\"" +
				"\"userpicture\":\"" + Arrays.toString(pictureAsBytes) + "\"" +
				"}";
		
		String response = getJson(Endpoints.UPDATE_USER, json);
		return response != null;
	}
	
	public List<Employer> getEmployers() {
		List<Employer> employerList = new ArrayList<>();
		String result = getJson(Endpoints.GET_EMPLOYERS, null);
		if (result != null) {
			Type employerListType = new TypeToken<List<Employer>>() {
			}.getType();
			try {
				employerList = gson.fromJson(result, employerListType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				// do nothing else
			}
		}
		return employerList;
	}
	
	public List<User> getUsers() {
		List<User> userList = new ArrayList<>();
		String result = getJson(Endpoints.GET_EMPLOYERS, null);
		if (result != null) {
			Type userListType = new TypeToken<List<User>>() {}.getType();
			try {
				userList = gson.fromJson(result, userListType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				// do nothing else
			}
		}
		return userList;
	}
	
	
	/**
	 * Peforms a POST-request to the given {@code endpointName} with the given {@code json} as the request body
	 * and returns the body of the answer if the call was successful. If not, returns {@code null}.
	 *
	 * @param endpoint the endpointName to direct the request to
	 * @param json     the json body to send
	 * @return the body of the answer if the call was successful, {@code null} if not
	 */
	@Nullable
	private String getJson(Endpoints endpoint, @Nullable String json) {
		String url = BASE_URL + endpoint.getEndpointName();
		OkHttpClient client = new OkHttpClient();
		RequestBody body;
		if (json != null) {
			body = RequestBody.create(JSON, json);
		} else {
			body = RequestBody.create(null, new byte[0]);
		}
		Request request = new Request.Builder().url(url)
											   .post(body)
											   .build();
		try (Response response = client.newCall(request)
									   .execute();
			 Reader reader = response.body()
									 .charStream()) {
			char[] array = new char[8 * 1024];
			StringBuilder buffer = new StringBuilder();
			while (reader.read(array, 0, array.length) != -1) {
				buffer.append(array);
			}
			if (buffer.charAt(0) == '[') {
				buffer.deleteCharAt(0)
					  .deleteCharAt(buffer.lastIndexOf("]"));
			}
			return buffer.toString()
						 .trim();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public enum Endpoints {
		LOGIN_USER("loginUser"),
		LOGIN_EMPLOYER("loginEmp"),
		REGISTER_USER("regi"),
		REGISTER_EMPLOYER("regiemployer"),
		UPDATE_USER("setDataUser"),
		UPDATE_EMPLOYER("setDataEmp"),
		DELETE_USER("deleteUser"),
		DELETE_EMPLOYER("deleteEmployer"),
		ADD_SKILL_USER("AddSkill"),
		ADD_SKILL_EMPLOYER("AddEmpSkill"),
		GET_SKILLS("GetSkill"),
		GET_MATCHES("getMatches"),
		ADD_MATCHES("matches"),
		GET_EMPLOYERS("getAllEmp"),
		GET_USERS("getAllUser");
		
		private final String endpointName;
		
		private Endpoints(String endpointName) {
			this.endpointName = endpointName;
		}
		
		public final String getEndpointName() {
			return endpointName;
		}
	}
}
