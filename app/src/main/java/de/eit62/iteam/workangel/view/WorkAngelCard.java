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

import java.util.List;

/**
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
	private List<Match> matchList;
	private Context context;
	private SwipePlaceHolderView swipeView;
	
	public WorkAngelCard(Context context, AppUser profile, SwipePlaceHolderView swipeView) {
		this.context = context;
		this.swipeView = swipeView;
		currentUser = profile;
		
	}
	
	@Resolve
	private void onResolved() {
		Glide.with(context)
			 .load(currentUser.getPicture())
			 .into(profileImageView);
		if (currentUser instanceof User) {
			User user = (User) currentUser;
			nameAgeTxt.setText(user.getForename());
		} else {
			Employer employer = (Employer) currentUser;
			nameAgeTxt.setText(employer.getName());
		}
	}
	
	@SwipeOut
	private void onSwipedOut() {
		Log.d("EVENT", "onSwipedOut");
		swipeView.addView(this);
	}
	
	@SwipeCancelState
	private void onSwipeCancelState() {
		Log.d("EVENT", "onSwipeCancelState");
	}
	
	@SwipeIn
	private void onSwipeIn() {
		Log.d("EVENT", "onSwipedIn");
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