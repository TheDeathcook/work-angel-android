package de.eit62.iteam.workangel.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import de.eit62.iteam.workangel.R;
import de.eit62.iteam.workangel.beans.Employer;
import de.eit62.iteam.workangel.beans.User;

/**
 * This is a placeholder Activity because a member of the group fell ill and was not able to commit his Profile.
 *
 * @author Lukas Tegethoff
 */
public class ProfileActivity extends BaseActivity {
	
	
	private EditText firstnameText;
	private EditText lastnameText;
	private EditText descriptionText;
	private Button saveButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		
		this.firstnameText = findViewById(R.id.firstnameText);
		this.lastnameText = findViewById(R.id.lastnameText);
		this.descriptionText = findViewById(R.id.descriptionText);
		this.saveButton = findViewById(R.id.saveButton);
		this.saveButton.setOnClickListener(v -> saveUserData());
		
		if (getCurrentUser() instanceof User) {
			User user = (User) getCurrentUser();
			this.firstnameText.setText(user.getForename());
			this.lastnameText.setText(user.getLastName());
			this.descriptionText.setText(user.getDescription());
		} else {
			Employer employer = (Employer) getCurrentUser();
			this.firstnameText.setVisibility(View.GONE);
			this.lastnameText.setText(employer.getName());
			this.descriptionText.setText(employer.getDescription());
		}
	}
	
	public void saveUserData() {
		AlertDialog dialog = new AlertDialog.Builder(this).setMessage("not implemented").create();
		dialog.show();
	}
}
