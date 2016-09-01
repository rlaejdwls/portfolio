package kr.ac.gachon.game.pingpong.object;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public abstract class RectObject extends BaseObject {
	private int width;
	private int height;
	
	private boolean bDrag;
	
	public RectObject(float leftTopX, float leftTopY, int width, int height) {
		super(leftTopX, leftTopY);
		this.width = width;
		this.height = height;
		this.bDrag = false;
	}
	
	public RectObject(float leftTopX, float leftTopY, int width, int height, Paint paint) {
		super(leftTopX, leftTopY, paint);
		this.width = width;
		this.height = height;
		this.bDrag = false;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void onPaint(Canvas canvas) {
		canvas.drawRect(this.getX(), this.getY(), this.getX() + width, 
				this.getY() + height, this.getPaint());
	}
	@Override
	public String toString() {
		return "RectObject [width=" + width + ", height=" + height + "]";
	}
	
	@Override
	public void onEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onActionDown(event);
			if (bDrag == false) {
				bDrag = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			onActionMove(event);
			if (bDrag) {
			}
			break;
		case MotionEvent.ACTION_UP:
			if (bDrag == true) {
				bDrag = false;
			}
			break;
		default:
			break;
		}
	}
	
	protected void onActionDown(MotionEvent event) {
	}
	protected void onActionMove(MotionEvent event) {
	}
}
