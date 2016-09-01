package kr.ac.gachon.game.pingpong.data;

import kr.ac.gachon.game.pingpong.object.RectObject;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Racket extends RectObject {
	public Racket(float leftTopX, float leftTopY, int width, int height,
			Paint paint) {
		super(leftTopX, leftTopY, width, height, paint);
	}

	@Override
	public void onDestroy() {
	}

	@Override
	protected void onActionDown(MotionEvent event) {
		super.onActionDown(event);
		this.setX(event.getX() - (this.getWidth() / 2));
	}

	@Override
	protected void onActionMove(MotionEvent event) {
		super.onActionMove(event);
		this.setX(event.getX() - (this.getWidth() / 2));
	}
}
