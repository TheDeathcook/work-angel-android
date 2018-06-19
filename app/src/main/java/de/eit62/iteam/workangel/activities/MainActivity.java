package de.eit62.iteam.workangel.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import de.eit62.iteam.workangel.R;

public class MainActivity extends AppCompatActivity {
	
	private SwipePlaceHolderView swipeView;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		swipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
		context = getApplicationContext();
		
		swipeView.getBuilder()
				 .setDisplayViewCount(3)
				 .setSwipeDecor(new SwipeDecor()
						 .setPaddingTop(20)
						 .setRelativeScale(0.01f)
						 .setSwipeInMsgLayoutId(R.layout.workangel_swipe_in)
						 .setSwipeOutMsgLayoutId(R.layout.workangel_swipe_out));
		
		//TODO: Get Profiles
		
		findViewById(R.id.rejectBtn).setOnClickListener(v -> swipeView.doSwipe(false));
		
		findViewById(R.id.acceptBtn).setOnClickListener(v -> swipeView.doSwipe(true));
		
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
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
