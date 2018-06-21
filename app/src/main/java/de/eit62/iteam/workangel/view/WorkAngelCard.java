package de.eit62.iteam.workangel.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.*;
import de.eit62.iteam.workangel.R;
import de.eit62.iteam.workangel.beans.AppUser;
import de.eit62.iteam.workangel.beans.Employer;
import de.eit62.iteam.workangel.beans.Match;
import de.eit62.iteam.workangel.beans.User;

/**
 * Basically taken from here: https://blog.mindorks.com/android-tinder-swipe-view-example-3eca9b0d4794
 * Modified to work with our user and match structure.
 *
 * @author Felix Bergemann
 * @author Lukas Tegethoff
 */

@Layout(R.layout.workangel_card_view)
public class WorkAngelCard {
	
	@View(R.id.profileImageView)
	private ImageView profileImageView;
	
	@View(R.id.nameAgeTxt)
	private TextView nameAgeTxt;
	
	@View(R.id.locationNameTxt)
	private TextView locationNameTxt;
	
	private AppUser currentUser;
	private AppUser matchUser;
	private Match match;
	private Context context;
	private SwipePlaceHolderView swipeView;
	
	public WorkAngelCard(Context context, AppUser currentUser, AppUser matchUser, Match match, SwipePlaceHolderView swipeView) {
		this.context = context;
		this.swipeView = swipeView;
		this.currentUser = currentUser;
		this.match = match;
		this.matchUser = matchUser;
	}
	
	@Resolve
	private void onResolved() {
		Glide.with(context)
			 .load(matchUser.getPicture())
			 .into(profileImageView);
		if (matchUser instanceof User) {
			User user = (User) matchUser;
			nameAgeTxt.setText(user.getForename());
		} else {
			Employer employer = (Employer) matchUser;
			nameAgeTxt.setText(employer.getName());
		}
	}
	
	@SwipeOut
	private void onSwipedOut() {
		Log.d("EVENT", "onSwipedOut");
		if (currentUser instanceof User) {
			match.setUserAccepted(Boolean.FALSE);
		} else {
			match.setEmployerAccepted(Boolean.FALSE);
		}
		swipeView.addView(this);
	}
	
	@SwipeCancelState
	private void onSwipeCancelState() {
		Log.d("EVENT", "onSwipeCancelState");
	}
	
	@SwipeIn
	private void onSwipedIn() {
		Log.d("EVENT", "onSwipedIn");
		if (currentUser instanceof User) {
			match.setUserAccepted(Boolean.TRUE);
		} else {
			match.setEmployerAccepted(Boolean.TRUE);
		}
	}
	
	@SwipeInState
	private void onSwipeInState() {
		Log.d("EVENT", "onSwipeInState");
	}
	
	@SwipeOutState
	private void onSwipeOutState() {
		Log.d("EVENT", "onSwipeOutState");
	}
}