package kr.ac.gachon.game.pingpong.object;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class TextObject extends BaseObject {
	private String text;
	
	public TextObject(String text, float leftTopX, float leftTopY, Paint paint) {
		super(leftTopX, leftTopY, paint);
		this.text = text;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void onPaint(Canvas canvas) {
		canvas.drawText(text, this.getX(), this.getY(), this.getPaint());
	}

	@Override
	public String toString() {
		return "TextObject [text=" + text + "]";
	}
}
