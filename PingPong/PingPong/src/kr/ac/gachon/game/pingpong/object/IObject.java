package kr.ac.gachon.game.pingpong.object;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface IObject {
	public void onPaint(Canvas canvas);
	public void onEvent(MotionEvent event);
	public void onDestroy();
}
