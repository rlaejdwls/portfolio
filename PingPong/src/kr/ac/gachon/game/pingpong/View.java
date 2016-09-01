package kr.ac.gachon.game.pingpong;

import java.util.Timer;

import kr.ac.gachon.game.pingpong.controller.Controller;
import kr.ac.gachon.game.pingpong.controller.timer.GraphicTimer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class View extends SurfaceView implements Callback {
	private Controller controller;
	private SurfaceHolder surfaceHolder;
	private Timer timer;
	
	public View(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public View(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public View(Context context) {
		super(context);
		
		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);
		
		controller = Controller.getInstance();
		controller.setContext(this.getContext());
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		if (timer == null) {
			GraphicTimer graphicTimer = new GraphicTimer(surfaceHolder);
			timer = new Timer();
			timer.scheduleAtFixedRate(graphicTimer, 0, 1000 / 60);
		}
		
		if (controller != null) {
			controller.initialize(this.getWidth(), this.getHeight());
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return controller.onTouch(event);
	}
	public void onBackPressed() {
		controller.onBackPressed();
	}
	public void onDestroy() {
		controller.onDestroy();
	}
}
