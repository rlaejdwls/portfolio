package kr.ac.gachon.game.pingpong;

import kr.ac.gachon.game.pingpong.util.ActivityReference;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
	private View view;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new View(this);
        setContentView(view);
        ActivityReference.setActivity(this);
    }
	@Override
	public void onBackPressed() {
		view.onBackPressed();
		super.onBackPressed();
	}
	@Override
	protected void onDestroy() {
		view.onDestroy();
		super.onDestroy();
	}
}
