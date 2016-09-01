package kr.ac.gachon.game.pingpong.model;

import com.google.gson.annotations.Expose;

public class Moving extends Thread {
	@Expose(serialize = false, deserialize = false)
	private TestModel testModel;
	@Expose(serialize = false, deserialize = false)
	private boolean isStarted;
	
	public Moving(TestModel testModel) {
		this.testModel = testModel;
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
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
