package kr.ac.gachon.game.pingpong.object;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.google.gson.annotations.Expose;

public abstract class BaseObject implements IObject {
	@Expose private float leftTopX;
	@Expose private float leftTopY;
	
	@Expose(serialize = false, deserialize = false)
	private Paint paint;
	
	public BaseObject(float leftTopX, float leftTopY) {
		super();
		this.leftTopX = leftTopX;
		this.leftTopY = leftTopY;
		
		paint = new Paint();
		paint.setARGB(255, 100, 100, 20);
	}
	public BaseObject(float leftTopX, float leftTopY, Paint paint) {
		super();
		this.leftTopX = leftTopX;
		this.leftTopY = leftTopY;
		this.paint = paint;
	}

	public float getX() {
		return leftTopX;
	}
	public void setX(float leftTopX) {
		this.leftTopX = leftTopX;
	}
	public float getY() {
		return leftTopY;
	}
	public void setY(float leftTopY) {
		this.leftTopY = leftTopY;
	}
	public Paint getPaint() {
		return paint;
	}
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	@Override
	public void onEvent(MotionEvent event) {
	}
	@Override
	public String toString() {
		return "BaseObject [leftTopX=" + leftTopX + ", leftTopY=" + leftTopY
				+ ", paint=" + paint + "]";
	}
}
