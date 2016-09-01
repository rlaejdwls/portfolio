package kr.ac.gachon.game.pingpong.data;

import kr.ac.gachon.game.pingpong.object.BaseObject;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Player extends BaseObject {
	private String id;
	private byte num;
	private int score;
	
	public Player(byte num, float leftTopX, float leftTopY, Paint paint) {
		super(leftTopX, leftTopY, paint);
		this.num = num;
		this.score = 0;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte getNum() {
		return num;
	}
	public void setNum(byte num) {
		this.num = num;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public void onPaint(Canvas canvas) {
		canvas.drawText(Integer.toString(this.score), this.getX(), this.getY(), this.getPaint());
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", num=" + num + ", score=" + score + "]";
	}
}
