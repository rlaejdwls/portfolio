package kr.ac.gachon.game.pingpong.object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.gson.annotations.Expose;

public abstract class CircleObject extends BaseObject {
	@Expose private float r;
	
	public CircleObject(float leftTopX, float leftTopY, float r) {
		super(leftTopX, leftTopY);
		this.r = r;
	}
	public CircleObject(float leftTopX, float leftTopY, float r, Paint paint) {
		super(leftTopX, leftTopY, paint);
		this.r = r;
	}
	
	public float getR() {
		return r;
	}
	public void setR(float r) {
		this.r = r;
	}
	
	@Override
	public void onPaint(Canvas canvas) {
		canvas.drawCircle(this.getX(), this.getY(), r, this.getPaint());
	}
	@Override
	public String toString() {
		return "CircleObject [r=" + r + "]";
	}
}
