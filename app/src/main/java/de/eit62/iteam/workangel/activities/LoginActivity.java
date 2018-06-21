package de.eit62.iteam.workangel.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import de.eit62.iteam.workangel.R;
import de.eit62.iteam.workangel.beans.AppUser;
import de.eit62.iteam.workangel.beans.Employer;
import de.eit62.iteam.workangel.beans.User;
import de.eit62.iteam.workangel.fragments.LoginTaskFragment;
import de.eit62.iteam.workangel.interfaces.TaskCallback;
import de.eit62.iteam.workangel.util.Util;

/**
 * A login screen that offers login via email/password.
 *
 * @author Mark Tohai
 * @author Lukas Tegethoff
 */
public class LoginActivity extends BaseActivity implements TaskCallback<Void, AppUser> {
	
	private static final String TAG_LOGIN_TASK_FRAGMENT = "login_task_fragment";
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private LoginTaskFragment loginTaskFragment = null;
	
	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		FragmentManager fragmentManager = getFragmentManager();
		this.loginTaskFragment = (LoginTaskFragment) fragmentManager.findFragmentByTag(TAG_LOGIN_TASK_FRAGMENT);
		
		if (this.loginTaskFragment == null) {
			this.loginTaskFragment = new LoginTaskFragment();
			fragmentManager.beginTransaction()
						   .add(this.loginTaskFragment, TAG_LOGIN_TASK_FRAGMENT)
						   .commit();
		}
		
		// Set up the login form.
		mEmailView = findViewById(R.id.email);
		
		mPasswordView = findViewById(R.id.password);
		mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
			if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
				attemptLogin();
				return true;
			}
			return false;
		});
		
		Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(view -> attemptLogin());
		
		
		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	private void attemptLogin() {
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		
		// Store values at the time of the login attempt.
		String username = mEmailView.getText()
									.toString();
		// Store password in char[] as it is more secure.
		char[] password = new char[mPasswordView.length()];
		mPasswordView.getText()
					 .getChars(0, mPasswordView.length(), password, 0);
		
		boolean cancel = false;
		View focusView = null;
		
		// Check for a valid password, if the user entered one.
		if (password.length <= 0 && !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		
		// Check for a valid username.
		if (TextUtils.isEmpty(username)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}
		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			this.loginTaskFragment.executeLogin(username, password);
		}
		
	}
	
	private boolean isPasswordValid(char[] password) {
		return password.length > 4;
	}
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	private void showProgress(final boolean show) {
		mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
		mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
	}
	
	@Override
	public void onPreExecute() {
		// nothing to do
	}
	
	@Override
	public void onProgressUpdate(Void... progress) {
		// publishProgress is not used for LoginTask, so nothing to do here.
	}
	
	@Override
	public void onCancelled() {
		showProgress(false);
	}
	
	@Override
	public void onPostExecute(AppUser appUser) {
		if (appUser != null) {
			if (appUser instanceof User) {
				User user = (User) appUser;
				getSharedPreferences("users", MODE_PRIVATE).edit().putString("currentUser", Util.GSON.toJson(user)).apply();
			} else {
				Employer employer = (Employer) appUser;
				getSharedPreferences("users", MODE_PRIVATE).edit().putString("currentUser", Util.GSON.toJson(employer)).apply();
			}
			setCurrentUser(appUser);
			Intent i = new Intent(this, MatchingActivity.class);
			startActivity(i);
		} else {
			mPasswordView.setError(getString(R.string.error_communication));
			mPasswordView.requestFocus();
		}
		showProgress(false);
	}
}

