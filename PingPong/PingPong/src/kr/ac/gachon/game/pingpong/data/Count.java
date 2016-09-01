package kr.ac.gachon.game.pingpong.data;

import kr.ac.gachon.game.pingpong.controller.Controller;
import kr.ac.gachon.game.pingpong.object.TextObject;
import android.graphics.Paint;

public class Count extends TextObject {
	class Counting extends Thread {
		private Count count;
		private boolean isStarted;
		
		public Counting(Count count) {
			this.count = count;
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
			try {
				while (this.count.getCount() > 1 && isStarted) {
					this.count.setCount(this.count.getCount() - 1);
					this.count.setText(Integer.toString(this.count.getCount()));
					
					if (isStarted) Thread.sleep(1000);
				}
				this.count.setText("Start");
				if (isStarted) Thread.sleep(1000);
				this.count.setText("");
				if (isStarted) Controller.getInstance().resume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Counting counting;
	private int count;
	
	public Count(int count, float leftTopX, float leftTopY, Paint paint) {
		super("", leftTopX, leftTopY, paint);
		this.count = count;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public void startCount() {
		counting = new Counting(this);
		counting.startThread();
		counting.start();
	}

	@Override
	public void onDestroy() {
		counting.stopThread();
	}

	@Override
	public String toString() {
		return "Count [counting=" + counting + ", count=" + count + "]";
	}
}
