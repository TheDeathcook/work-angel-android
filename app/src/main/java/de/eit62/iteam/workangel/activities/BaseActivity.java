package de.eit62.iteam.workangel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import de.eit62.iteam.workangel.R;
import de.eit62.iteam.workangel.beans.AppUser;
import de.eit62.iteam.workangel.beans.Employer;
import de.eit62.iteam.workangel.beans.User;
import de.eit62.iteam.workangel.util.Util;

/**
 * Abstract super class for all {@code Activities} in the app.
 * Handles the toolbar options and the current user.
 *
 * @author Lukas Tegethoff
 */
public abstract class BaseActivity extends AppCompatActivity {
	
	private AppUser currentUser;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentUser = getCurrentUser();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		switch (item.getItemId()) {
			case R.id.switch_profile:
				Intent intent = new Intent(this, ProfileActivity.class);
				startActivity(intent);
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
		
	}
	
	public AppUser getCurrentUser() {
		if (currentUser == null) {
			String user = getSharedPreferences("users", MODE_PRIVATE).getString("currentUser", null);
			if (user != null) {
				if (user.toLowerCase()
						.contains("lastname")) {
					currentUser = Util.GSON.fromJson(user, User.class);
				} else {
					currentUser = Util.GSON.fromJson(user, Employer.class);
				}
			}
		}
		return this.currentUser;
	}
	
	public void setCurrentUser(AppUser currentUser) {
		this.currentUser = currentUser;
	}
}
