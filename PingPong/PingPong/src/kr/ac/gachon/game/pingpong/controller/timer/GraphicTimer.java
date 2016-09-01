package kr.ac.gachon.game.pingpong.controller.timer;

import java.util.TimerTask;

import kr.ac.gachon.game.pingpong.controller.Controller;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GraphicTimer extends TimerTask {
	private Controller controller;
	private SurfaceHolder surfaceHolder;
	private Canvas canvas;
	
	public GraphicTimer(SurfaceHolder surfaceHolder) {
		controller = Controller.getInstance();
		this.surfaceHolder = surfaceHolder;
	}

	@Override
	public void run() {
		canvas = surfaceHolder.lockCanvas(null);
		
		try {
			synchronized (canvas) {
				controller.onUpdate(canvas);
			}
		} catch (Exception e) {
			Log.i("Exception", e.toString());
		} finally {
			try {
				surfaceHolder.unlockCanvasAndPost(canvas);
			} catch (Exception e) {
				Log.i("Exception", e.toString());
			}
		}
	}
}
