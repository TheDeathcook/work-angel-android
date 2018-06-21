package de.eit62.iteam.workangel.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import de.eit62.iteam.workangel.beans.AppUser;
import de.eit62.iteam.workangel.beans.Employer;
import de.eit62.iteam.workangel.beans.Match;
import de.eit62.iteam.workangel.beans.User;
import de.eit62.iteam.workangel.interfaces.TaskCallback;
import de.eit62.iteam.workangel.util.Util;
import de.eit62.iteam.workangel.webservice.WAServiceClient;

import java.util.ArrayList;
import java.util.List;

public class MatchTaskFragment extends Fragment {
	
	private static final WAServiceClient SERVICE_CLIENT = WAServiceClient.getInstance();
	private SharedPreferences userPreferences;
	private TaskCallback<Void, List<Match>> callbacks;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.userPreferences = context.getSharedPreferences("users", Context.MODE_PRIVATE);
		// no instanceof check because if the context is not of this type, the app should crash.
		callbacks = (TaskCallback<Void, List<Match>>) context;
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
	
	public void executeMatchTask(AppUser user) {
		MatchTask matchTask = new MatchTask();
		matchTask.execute(user);
	}
	
	private class MatchTask extends AsyncTask<AppUser, Void, List<Match>> {
		
		@Override
		protected void onPreExecute() {
			if (callbacks != null) {
				callbacks.onPreExecute();
			}
		}
		
		@Override
		protected List<Match> doInBackground(AppUser... params) {
			List<Match> matches = new ArrayList<>();
			AppUser currentUser = params[0];
			if (currentUser instanceof User) {
				User user = (User) currentUser;
				List<Employer> employers = SERVICE_CLIENT.getEmployers(userPreferences);
				matches = Util.createMatchListForUser(user, employers, 1);
			} else if (currentUser instanceof Employer) {
				Employer employer = (Employer) currentUser;
				List<User> users = SERVICE_CLIENT.getUsers(userPreferences);
				matches = Util.createMatchListForEmployer(employer, users, 1);
			}
			
			return matches;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			if (callbacks != null) {
				callbacks.onProgressUpdate(values);
			}
		}
		
		@Override
		protected void onPostExecute(final List<Match> matches) {
			if (callbacks != null) {
				callbacks.onPostExecute(matches);
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
