package de.eit62.iteam.workangel.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import de.eit62.iteam.workangel.R;
import de.eit62.iteam.workangel.beans.AppUser;
import de.eit62.iteam.workangel.beans.Match;
import de.eit62.iteam.workangel.beans.User;
import de.eit62.iteam.workangel.fragments.MatchTaskFragment;
import de.eit62.iteam.workangel.interfaces.TaskCallback;
import de.eit62.iteam.workangel.util.Util;
import de.eit62.iteam.workangel.view.WorkAngelCard;

import java.util.List;

/**
 *
 * @author Felix Bergemann
 * @author Lukas Tegethoff
 */
public class MatchingActivity extends BaseActivity implements TaskCallback<Void, List<Match>> {
	
	private static final String TAG_MATCH_TASK_FRAGMENT = "match_task_fragment";
	
	private View matchingProgress;
	private SwipePlaceHolderView swipeView;
	private Context context;
	private MatchTaskFragment matchTaskFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matching);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		matchingProgress = findViewById(R.id.matching_progress);
		swipeView = findViewById(R.id.swipeView);
		context = getApplicationContext();
		
		swipeView.getBuilder()
				 .setDisplayViewCount(3)
				 .setSwipeDecor(new SwipeDecor()
						 .setPaddingTop(20)
						 .setRelativeScale(0.01f)
						 .setSwipeInMsgLayoutId(R.layout.workangel_swipe_in)
						 .setSwipeOutMsgLayoutId(R.layout.workangel_swipe_out));
		
		FragmentManager fragmentManager = getFragmentManager();
		this.matchTaskFragment = (MatchTaskFragment) fragmentManager.findFragmentByTag(TAG_MATCH_TASK_FRAGMENT);
		
		if (this.matchTaskFragment == null) {
			this.matchTaskFragment = new MatchTaskFragment();
			fragmentManager.beginTransaction()
						   .add(this.matchTaskFragment, TAG_MATCH_TASK_FRAGMENT)
						   .commit();
		}
		
		findViewById(R.id.rejectBtn).setOnClickListener(v -> swipeView.doSwipe(false));
		
		findViewById(R.id.acceptBtn).setOnClickListener(v -> swipeView.doSwipe(true));
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		showProgress(true);
		matchTaskFragment.executeMatchTask(getCurrentUser());
	}
	
	private void showProgress(final boolean show) {
		matchingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
		swipeView.setVisibility(show ? View.GONE : View.VISIBLE);
	}
	
	@Override
	public void onPreExecute() {
	}
	
	@Override
	public void onProgressUpdate(Void... progress) {
	
	}
	
	@Override
	public void onCancelled() {
		showProgress(false);
	}
	
	@Override
	public void onPostExecute(List<Match> matches) {
		for (Match match : matches) {
			AppUser matchUser;
			if (getCurrentUser() instanceof User) {
				matchUser = Util.getEmployerForMatch(match, getSharedPreferences("users", MODE_PRIVATE));
			} else {
				matchUser = Util.getUserForMatch(match, getSharedPreferences("users", MODE_PRIVATE));
			}
			swipeView.addView(new WorkAngelCard(context, getCurrentUser(), matchUser, match, swipeView));
		}
		showProgress(false);
	}
}
