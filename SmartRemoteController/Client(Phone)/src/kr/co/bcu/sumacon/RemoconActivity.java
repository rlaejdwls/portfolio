package kr.co.bcu.sumacon;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
 
public class RemoconActivity extends Activity {
	private PrintWriter pw = null;
	
	private DatagramSocket c_dsock = null;
	
	private int port = 0;
	private InetAddress inetaddr = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.setContentView(R.layout.remocon);
		
		initButton();
		
		//UDP
		Intent intent = this.getIntent();
		String ipAddress = intent.getStringExtra("IP");
		port = Integer.parseInt(intent.getStringExtra("PORT"));
		
		try{
			//DatagramPacket에 들어갈 ip 주소가 
			//InetAddress 형태여야 함
			inetaddr = InetAddress.getByName(ipAddress);
			c_dsock = new DatagramSocket();
		}catch(UnknownHostException e){
			Toast.makeText(this, "잘못된 도메인이나 ip입니다.", Toast.LENGTH_SHORT);
			System.exit(1);
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (pw != null) {
			pw.println("closesocket");
			pw.flush();
			pw.close();
		}
		
		if (c_dsock != null) {
			c_dsock.close();
		}
	}

	public void initButton() {
		//mouse
		findViewById(R.id.btnLeftClick).setOnClickListener(onClickMouseEvent);
		findViewById(R.id.btnWheelClick).setOnClickListener(onClickMouseEvent);
		findViewById(R.id.btnRightClick).setOnClickListener(onClickMouseEvent);
		
		//keyboard
		findViewById(R.id.btnLeft).setOnClickListener(onClickKeyboardEvent);
		findViewById(R.id.btnUp).setOnClickListener(onClickKeyboardEvent);
		findViewById(R.id.btnRight).setOnClickListener(onClickKeyboardEvent);
		findViewById(R.id.btnDown).setOnClickListener(onClickKeyboardEvent);
		findViewById(R.id.btnSpace).setOnClickListener(onClickKeyboardEvent);
		findViewById(R.id.btnComma).setOnClickListener(onClickKeyboardEvent);
		findViewById(R.id.btnPeriod).setOnClickListener(onClickKeyboardEvent);
		findViewById(R.id.btnPageUp).setOnClickListener(onClickKeyboardEvent);
		findViewById(R.id.btnPageDown).setOnClickListener(onClickKeyboardEvent);
	}
	
	//UDP
	private float downX = 0;
	private float downY = 0;
	private int i = 0;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getX();
				downY = event.getY();
				c_dsock.send(getSendData("mudn"));
				break;
			default:
				if (i % 3 == 0) {
					c_dsock.send(getSendData("mumv/" + Integer.toString((int)(event.getX() - downX)) + 
							"/" + Integer.toString((int)(event.getY() - downY))));
				}
				i++;
				if (i == 100000) {
					i = 0;
				}
				break;
			}
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}
	
	public DatagramPacket getSendData(String data) {
		return new DatagramPacket(data.getBytes(), 
				data.getBytes().length, inetaddr, port);
	}

	View.OnClickListener onClickMouseEvent = 
			new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				switch (v.getId()) {
				case R.id.btnLeftClick:
					c_dsock.send(getSendData("leftclick"));
					break;
				case R.id.btnWheelClick:
					c_dsock.send(getSendData("wheelclick"));
					break;
				case R.id.btnRightClick:
					c_dsock.send(getSendData("rightclick"));
					break;
				}
			} catch (IOException ioe) {
			}
		}
	};
	
	View.OnClickListener onClickKeyboardEvent = 
			new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				switch (v.getId()) {
				case R.id.btnUp:
					c_dsock.send(getSendData("UP"));
					break;
				case R.id.btnLeft:
					c_dsock.send(getSendData("LEFT"));
					break;
				case R.id.btnRight:
					c_dsock.send(getSendData("RIGHT"));
					break;
				case R.id.btnDown:
					c_dsock.send(getSendData("DOWN"));
					break;
				case R.id.btnSpace:
					c_dsock.send(getSendData("SPACE"));
					break;
				case R.id.btnComma:
					c_dsock.send(getSendData("OEM_COMMA"));
					break;
				case R.id.btnPeriod:
					c_dsock.send(getSendData("OEM_PERIOD"));
					break;
				case R.id.btnPageUp:
					c_dsock.send(getSendData("PRIOR"));
					break;
				case R.id.btnPageDown:
					c_dsock.send(getSendData("NEXT"));
					break;
				default:
					break;
				}
			} catch (IOException ioe) {
			}
		}
	};
}