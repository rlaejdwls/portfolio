package kr.ac.gachon.game.pingpong.controller;

import java.util.ArrayList;

import kr.ac.gachon.game.pingpong.RankActivity;
import kr.ac.gachon.game.pingpong.data.Ball;
import kr.ac.gachon.game.pingpong.data.Count;
import kr.ac.gachon.game.pingpong.data.Player;
import kr.ac.gachon.game.pingpong.data.Racket;
import kr.ac.gachon.game.pingpong.event.OnBluetoothMessageReadCallBack;
import kr.ac.gachon.game.pingpong.event.OnHttpConnectCallBack;
import kr.ac.gachon.game.pingpong.event.model.BluetoothMessageReadEvent;
import kr.ac.gachon.game.pingpong.event.model.HttpConnectEvent;
import kr.ac.gachon.game.pingpong.model.GameState;
import kr.ac.gachon.game.pingpong.model.SendMessageModel;
import kr.ac.gachon.game.pingpong.object.IObject;
import kr.ac.gachon.game.pingpong.util.BluetoothCommService;
import kr.ac.gachon.game.pingpong.util.HttpConnect;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Hwang
 * @version 0.0.1
 * @since 2015/05/27
 * @comment 현재 컨트롤러 클래스에 모든 처리과정이 정의되어 있음으로 추후 나누어줄 필요성이 있음.
 */
public class Controller {
	private static Controller controller;
	private Context context;
	
	private Controller() {
		//player init
		Paint p1 = new Paint();
		p1.setARGB(255, 255, 255, 255);
		objList.add(new Player((byte) 2, 10, 10, p1)); 
	}
	
	public void initialize(int width, int height) {
		//game init
		this.pause();
		isGameOver = false;
		
		//bluetooth init
        mCommService = BluetoothCommService.getService();
        mCommService.mHandler.setOnBluetoothMessageRead(new OnBluetoothMessageReadCallBack() {
			@Override
			public void OnBluetoothMessageReadCallBackEvent(
					BluetoothMessageReadEvent event) {
				String jsonString = event.getObject().toString();
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				SendMessageModel model = gson.fromJson(jsonString, SendMessageModel.class);
				
				if (model.getState() == GameState.VICTORY) {
					Controller.getInstance().over("승", "win=1&defeat=0");
				} else if (model.getState() == GameState.TOSS) {
					Ball receive = model.getBall();
					Ball ball = Controller.getInstance().getBall();
					
					ball.setX(receive.getX());
					ball.setY(ball.getR() + 1);
					
					ball.setDistanceX(receive.getDistanceX());
					ball.setDistanceY(-(receive.getDistanceY()));
	
					ball.startMoving();
					ball.setVisible(true);
				}
			}
		});
		
		//racket init
		Paint p1 = new Paint();
		p1.setARGB(255, 255, 255, 255);
		objList.add(new Racket(width / 2 - 60, height - 14, 120, 14, p1));
		
		//ball init
		p1.setARGB(255, 255, 255, 255);
		Ball ball = new Ball(width / 2, height / 2 - 60, 10, p1);
		if (this.getPlayer().getNum() == 2) {
			ball.setVisible(false);
		}
		objList.add(ball);
		
		//text init
		Paint p2 = new Paint();
		p2.setARGB(255, 255, 255, 255);
		p2.setTextSize(54);
		p2.setTextAlign(Align.CENTER);
		Count count = new Count(4, width / 2, height / 2, p2);		
		objList.add(count);
		count.startCount();
	}
	
	public static synchronized Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}
	public void setContext(Context context) {
		this.context = context;
	}

	private ArrayList<IObject> objList = new ArrayList<IObject>();
	private boolean isGameOver = false;
	private boolean pause = true;	//임시변수
	
    private BluetoothCommService mCommService;
	
	public void onUpdate(Canvas canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		
		canvas.drawRect(0, 0, width, height, new Paint());
		
		Ball ball = null;
		Racket racket = null;
		Player player = null;
		
		/*
		 * 컨트롤러를 상속받은 객체에서 관리 될 수 있도록 변경이 필요함
		 */
		for (IObject obj : objList) {
			obj.onPaint(canvas);
			
			if (obj instanceof Ball) {
				ball = (Ball)obj;
			}
			if (obj instanceof Racket) {
				racket = (Racket)obj;
			}
			if (obj instanceof Player) {
				player = ((Player) obj);
			}
		}
		
		/*
		 * 컨트롤러에서 바로 충돌 체크 구현함
		 * 컨트롤러를 상속 받은 객체에서 구현되도록 변경 시킬 필요성이 있음
		 */
		if ((ball.getY() + ball.getR()) >= (height - racket.getHeight())
				&& (ball.getX() + (ball.getR() / 2)) > racket.getX()
				&& (ball.getX() + (ball.getR() / 2)) < (racket.getX() + racket.getWidth())) {
			SoundManager.getManager().play("hit");
			
			ball.setDistanceX(Math.round((ball.getDistanceX() + (ball.getDistanceX() > 0 ? 0.2f : -0.2f)) * 10) / 10f);
			ball.setDistanceY(Math.round((ball.getDistanceY() + (ball.getDistanceY() > 0 ? 0.2f : -0.2f)) * 10) / 10f);
			
			ball.setDistanceY(-(ball.getDistanceY()));
			ball.setY((height - racket.getHeight()) - ball.getR() - 1);
			
			player.setScore(player.getScore() + 1);
		}
		if (((ball.getY() + ball.getR()) >= height) && !isGameOver) {
			//gameover
			this.isGameOver = true;
    		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			mCommService.write(gson.toJson(new SendMessageModel(GameState.VICTORY, null)));
			this.over("패", "win=0&defeat=1");
			
			//nothing
//			ball.setDistanceY(-(ball.getDistanceY()));
//			ball.setY(height - ball.getR());
		}
		if ((ball.getY() - ball.getR()) <= 0 && ball.isVisible()) {
			//send ball
			ball.setVisible(false);
			ball.stopMoving();
			SendMessageModel sendModel = new SendMessageModel(GameState.TOSS, ball);
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			mCommService.write(gson.toJson(sendModel));
			
			//nothing 
//			ball.setDistanceY(-(ball.getDistanceY()));
//			ball.setY(ball.getR());
		}
		if ((ball.getX() + ball.getR()) >= width) {
			ball.setDistanceX(-(ball.getDistanceX()));
			ball.setX(width - ball.getR());
		}
		if ((ball.getX() - ball.getR()) <= 0) {
			ball.setDistanceX(-(ball.getDistanceX()));
			ball.setX(ball.getR());
		}
	}
	
	/*
	 * 컨트롤러 상속 클래스 만들기 너무 귀찮아서 임시로 여기에 구현함
	 * 당연히 추후 별도로 관리해 줄 필요성이 있음
	 * getPlayer, pause, resume
	 */
	public Player getPlayer() {
		for (IObject obj : objList) {
			if (obj instanceof Player) {
				return (Player) obj;
			}
		}
		return null;
	}
	public Ball getBall() {
		for (IObject obj : objList) {
			if (obj instanceof Ball) {
				return (Ball) obj;
			}
		}
		return null;
	}
	public Count getCount() {
		for (IObject obj : objList) {
			if (obj instanceof Count) {
				return (Count) obj;
			}
		}
		return null;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public void pause() {
		pause = true;
		
		for (IObject obj : objList) {
			if (obj instanceof Ball) {
				((Ball)obj).stopMoving();
			}
		}
	}
	public void resume() {
		//SoundManager.getManager().play("bgm", 0.6f, 0.6f, 1, -1, 1);
		pause = false;
		
		for (IObject obj : objList) {
			if (obj instanceof Ball) {
				if (((Ball)obj).isVisible()) ((Ball)obj).startMoving();
			}
		}
	}
	public void over(String text, String param) {
		this.setGameOver(true);
		this.pause();
		this.getCount().setText(text);
		Message message = new Message();
		Bundle bundle = new Bundle();
		bundle.putString("param", param);
		message.setData(bundle);
		handler.sendMessage(message);
	}
	private Handler handler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			new HttpConnect(new OnHttpConnectCallBack() {
				@Override
				public void onHttpConnectCallBackEvent(HttpConnectEvent event) {
					Log.i("INFO", event.getResult());
				}
			}, 
			"http://treegames.co.kr/gachon/updateRanking.php", 
			"id="+Controller.getInstance().getPlayer().getId() + "&" + msg.getData().getString("param"));
			
			return false;
		}
	});
	
	public boolean onTouch(MotionEvent event) {
		if (!pause) {
			for (IObject obj : objList) {
				obj.onEvent(event);
			}
		}
    	if (isGameOver && (event.getAction() == MotionEvent.ACTION_DOWN)) {
    		this.onDestroy();
    		Intent intent = new Intent(context, RankActivity.class);
    		context.startActivity(intent);
    	}
		return true;
	}
	public boolean onBackPressed() {
		if (isGameOver) {
			this.onDestroy();
    		Intent intent = new Intent(context, RankActivity.class);
    		context.startActivity(intent);
    		return false;
    	} else {
    		this.over("패", "win=0&defeat=1");
    		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			mCommService.write(gson.toJson(new SendMessageModel(GameState.VICTORY, null)));
    	}
		return true;
	}
	public void onDestroy() {
		//SoundManager.getManager().stop("bgm");
		for (int i = objList.size() - 1; i > -1; i--) {
			if (!(objList.get(i) instanceof Player)) {
				IObject obj = objList.get(i);
				obj.onDestroy();
				objList.remove(i);
			}
		}
		if (mCommService != null) mCommService.stop();
		if (this.getPlayer() != null) this.getPlayer().setNum((byte) 2);
	}
}
