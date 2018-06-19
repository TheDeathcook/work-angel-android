package de.eit62.iteam.workangel.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import de.eit62.iteam.workangel.beans.AppUser;
import de.eit62.iteam.workangel.webservice.WAServiceClient;


/**
 * @author Lukas Tegethoff
 */
public class TaskFragment extends Fragment {
	
	// intentionally raw for the moment
	private TaskCallback callbacks;
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		callbacks = (TaskCallback) context;
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this is important so that the task is continued across configuration changes
		setRetainInstance(true);
	}
	
	/**
	 * Initiates the login.
	 *
	 * @param username the username
	 * @param password the password of the user as {@code char[]}
	 */
	public void executeLogin(String username, char[] password) {
		UserLoginTask loginTask = new UserLoginTask();
		loginTask.execute(username, password);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		callbacks = null;
	}
	
	public interface TaskCallback<Progress, Result> {
		
		void onPreExecute();
		
		void onProgressUpdate(Progress progress);
		
		void onCancelled();
		
		void onPostExecute(Result result);
	}
	
	private class UserLoginTask extends AsyncTask<Object, Void, AppUser> {
		
		private final WAServiceClient serviceClient = WAServiceClient.getInstance();
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
