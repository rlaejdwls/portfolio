package kr.ac.gachon.game.pingpong.util;

import android.app.Activity;

public class ActivityReference {
	private static Activity activity;

	public static Activity getActivity() {
		return activity;
	}
	public static void setActivity(Activity activity) {
		ActivityReference.activity = activity;
	}
}
