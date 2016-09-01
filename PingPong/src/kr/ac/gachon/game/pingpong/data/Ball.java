package kr.ac.gachon.game.pingpong.data;

import kr.ac.gachon.game.pingpong.object.CircleObject;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.gson.annotations.Expose;

public class Ball extends CircleObject {
	class Moving extends Thread {
		private Ball ball;
		private boolean isStarted;
		
		public Moving(Ball ball) {
			this.ball = ball;
		}
		public void startThread() {
			this.isStarted = true;
		}
		public void stopThread() {
			this.isStarted = false;
		}
		
		@Override
		public void run() {
			super.run();
			while (isStarted) {
				this.ball.onMove(this.ball.getDistanceX(), this.ball.getDistanceY());
				try {
					Thread.sleep(this.ball.getMoveTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Expose private float distanceX;
	@Expose private float distanceY;
	
	@Expose private int moveTime;
	
	@Expose(serialize = false, deserialize = false)
	private Moving moving;
	
	@Expose private boolean visible;
	
	public Ball(float leftTopX, float leftTopY, float r) {
		super(leftTopX, leftTopY, r);
		init();
	}
	public Ball(float leftTopX, float leftTopY, float r, Paint paint) {
		super(leftTopX, leftTopY, r, paint);
		init();
	}
	public void init() {
		moveTime = 10;
		distanceX = 2.6f;
		distanceY = 2.6f;
		visible = true;
	}
	
	public float getDistanceX() {
		return distanceX;
	}
	public void setDistanceX(float distanceX) {
		this.distanceX = distanceX;
	}
	public float getDistanceY() {
		return distanceY;
	}
	public void setDistanceY(float distanceY) {
		this.distanceY = distanceY;
	}
	public int getMoveTime() {
		return moveTime;
	}
	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void onMove(float x, float y) {
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
	}
	@Override
	public void onPaint(Canvas canvas) {
		if (visible) super.onPaint(canvas);
	}
	@Override
	public void onDestroy() {
		this.stopMoving();
	}
	public void startMoving() {
		moving = new Moving(this);
		moving.startThread();
		moving.start();
	}
	public void stopMoving() {
		if (moving != null) {
			moving.stopThread();
			moving = null;
		}
	}
	@Override
	public String toString() {
		return "Ball [distanceX=" + distanceX + ", distanceY=" + distanceY
				+ ", moveTime=" + moveTime + ", moving=" + moving
				+ ", visible=" + visible + "]";
	}
}
