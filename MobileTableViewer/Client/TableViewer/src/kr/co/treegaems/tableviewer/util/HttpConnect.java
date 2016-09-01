package kr.co.treegaems.tableviewer.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import kr.co.treegaems.tableviewer.event.OnHttpConnectCallBack;
import kr.co.treegaems.tableviewer.event.model.HttpConnectEvent;
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

		HttpConnectThread(String url, String param) {
			this.param = param;
			this.url = url;
		}
		
		public void run() {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try {
				URL url = new URL(this.url);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				if (conn == null) return;
				
				conn.setConnectTimeout(10000);
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				OutputStreamWriter osw = new OutputStreamWriter( conn.getOutputStream() );
				osw.write(param);
				osw.flush();
				
				int resCode = conn.getResponseCode();
				
				System.out.println("Response Code : " + resCode);
				
				if (resCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					is = conn.getInputStream();
					byte[] buff = new byte[4096];
					int n;

					while ((n = is.read(buff)) > 0) {
						baos.write(buff, 0, n);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putByteArray("result", baos.toByteArray());
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (onHttpConnectCallBack != null) {
				HttpConnectEvent event = new HttpConnectEvent(msg.getData().getByteArray("result"));
				onHttpConnectCallBack.onHttpConnectCallBackEvent(event);
			}
		}
	};
}
