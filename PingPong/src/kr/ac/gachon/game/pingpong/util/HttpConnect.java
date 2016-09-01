package kr.ac.gachon.game.pingpong.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import kr.ac.gachon.game.pingpong.event.OnHttpConnectCallBack;
import kr.ac.gachon.game.pingpong.event.model.HttpConnectEvent;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class HttpConnect {
	private HttpConnectThread thread;
	
	private OnHttpConnectCallBack onHttpConnectCallBack;
	
	public HttpConnect(OnHttpConnectCallBack callBack, String url, Object object){
		this.onHttpConnectCallBack = callBack;
		this.thread = new HttpConnectThread(url, createParam(object));
		this.thread.start();
	}
	public HttpConnect(OnHttpConnectCallBack callBack, String url, String param){
		this.onHttpConnectCallBack = callBack;
		this.thread = new HttpConnectThread(url, param);
		this.thread.start();
	}
	public HttpConnect(OnHttpConnectCallBack callBack, String url){
		this.onHttpConnectCallBack = callBack;
		this.thread = new HttpConnectThread(url, null);
		this.thread.start();
	}
	
	private String createParam(Object object) {
		String result = "";
		
		for (Method method : object.getClass().getMethods()) {
			String name = method.getName();
			
			if (name.indexOf("get") == 0 &&
					!name.equals("getClass")) {
				name = name.substring(3);
				
				char ch = name.charAt(0);
				ch = (char) (ch + 32);
				result += ch;
				
				for (int i = 1; i < name.length(); i++) {
					ch = name.charAt(i);
					if (ch >= 65 && ch <= 90) {
						result += "_";
						result += (char)(ch + 32);
					} else {
						result += ch;
					}
				}
				
				result += "=";
				try {
					result += URLEncoder.encode(method.invoke(object, new Object[]{}).toString(), "UTF-8");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result += "&";
			}
		}
		
		result = result.substring(0, result.length() - 1);
		return result;
	}
	
	class HttpConnectThread extends Thread {
		private String param;
		private String url;
		private String result;

		HttpConnectThread(String url, String param) {
			this.param = param;
			this.url = url;
			this.result = "";
		}
		
		public void run() {
			StringBuilder html = new StringBuilder(); 
			try {
				URL objURL = new URL(url);
				HttpURLConnection conn = (HttpURLConnection)objURL.openConnection();
				if (conn != null) {  
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					
					conn.setDoOutput(true);
					DataOutputStream outParam = new DataOutputStream(conn.getOutputStream());
					if (param != null) outParam.writeBytes(param);
					outParam.flush();
					outParam.close();
					
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream()));
						for (;;) { 
							String line = br.readLine();
							if (line == null) break;
							html.append(line + '\n'); 
						}
						br.close();
						this.result = html.toString();
					}
					conn.disconnect();
				}
			} 
			catch (Exception ex) {
				ex.printStackTrace();
			}
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("result", result);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (onHttpConnectCallBack != null) {
				HttpConnectEvent event = new HttpConnectEvent(msg.getData().getString("result"));
				onHttpConnectCallBack.onHttpConnectCallBackEvent(event);
			}
		}
	};
}
