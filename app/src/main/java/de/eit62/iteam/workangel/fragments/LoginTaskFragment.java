package de.eit62.iteam.workangel.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import de.eit62.iteam.workangel.beans.AppUser;
import de.eit62.iteam.workangel.interfaces.TaskCallback;
import de.eit62.iteam.workangel.webservice.WAServiceClient;


/**
 * A retained Fragment which wraps the web service call to login a user.
 *
 * @author Lukas Tegethoff
 */
public class LoginTaskFragment extends Fragment {
	
	private final WAServiceClient serviceClient = WAServiceClient.getInstance();
	private TaskCallback<Void, AppUser> callbacks;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		// no instanceof check because if the context is not of this type, it doesn't make sense to continue
		callbacks = (TaskCallback<Void, AppUser>) context;
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this is important so that the task is continued across configuration changes
		setRetainInstance(true);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		callbacks = null;
	}
	
	/**
	 * Initiates the (asynchronous) login.
	 *
	 * @param username the username
	 * @param password the password of the user as {@code char[]}
	 */
	public void executeLogin(String username, char[] password) {
		UserLoginTask loginTask = new UserLoginTask();
		loginTask.execute(username, password);
	}
	
	private class UserLoginTask extends AsyncTask<Object, Void, AppUser> {
		
		private String username;
		private char[] password;
		
		@Override
		protected void onPreExecute() {
			if (callbacks != null) {
				callbacks.onPreExecute();
			}
		}
		
		@Override
		protected AppUser doInBackground(Object... params) {
			
			if (params[0] instanceof String && params[1] instanceof char[]) {
				username = params[0].toString();
				password = (char[]) params[1];
			}
			
			AppUser currentUser = serviceClient.loginUser(username, password);
			if (currentUser != null) {
				return currentUser;
			} else {
				// TODO: register the new account here.
				return null;
			}
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			if (callbacks != null) {
				callbacks.onProgressUpdate(values);
			}
		}
		
		@Override
		protected void onPostExecute(final AppUser user) {
			if (callbacks != null) {
				callbacks.onPostExecute(user);
			}
		}
		
		
		@Override
		protected void onCancelled() {
			if (callbacks != null) {
				callbacks.onCancelled();
			}
		}
	}
}
