package kr.ac.gachon.game.pingpong;

import kr.ac.gachon.game.pingpong.controller.Controller;
import kr.ac.gachon.game.pingpong.event.OnHttpConnectCallBack;
import kr.ac.gachon.game.pingpong.event.model.HttpConnectEvent;
import kr.ac.gachon.game.pingpong.model.LoginModel;
import kr.ac.gachon.game.pingpong.util.HttpConnect;
import kr.ac.gachon.game.pingpong.util.SimpleDialog;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	final private Context context = this;
	private ProgressDialog progress;
	
	private EditText txtId;
	private EditText txtPwd;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_login);
        
        Button btnLogin = (Button) this.findViewById(R.id.btn_login);
        Button btnRegMem = (Button) this.findViewById(R.id.btn_mem_reg);
        
        txtId = (EditText) this.findViewById(R.id.txt_id);
        txtPwd = (EditText) this.findViewById(R.id.txt_pwd);
        
        btnLogin.setOnClickListener(this.btnLoginOnClick);
        btnRegMem.setOnClickListener(this.btnRegMemOnClick);
    }
    
	private View.OnClickListener btnLoginOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			progress = ProgressDialog.show(LoginActivity.this, "Wait", "Login...");
			new HttpConnect(new OnHttpConnectCallBack() {
				@Override
				public void onHttpConnectCallBackEvent(HttpConnectEvent event) {
					try {
						JSONObject object = new JSONObject(event.getResult());
						
						if(object.getString("result").equals("1")){
				            progress.dismiss();
				            
				            Controller.getInstance().getPlayer().setId(txtId.getText().toString());
				            
					    	Intent intent = new Intent(LoginActivity.this, ConnActivity.class);
				            startActivity(intent);
						}else{
		                	progress.dismiss();
		                	SimpleDialog.getOKButtonDialog(context, "알림", "로그인에 실패하셨습니다. 아이디 패스워드를 확인하세요!")
		                		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								})
		                		.show();
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, "http://treegames.co.kr/gachon/selectMember.php", 
				new LoginModel(txtId.getText().toString(), txtPwd.getText().toString()));
		} 
	};
	
	private View.OnClickListener btnRegMemOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			progress = ProgressDialog.show(LoginActivity.this, "Wait", "Register...");
			new HttpConnect(new OnHttpConnectCallBack() {
				@Override
				public void onHttpConnectCallBackEvent(HttpConnectEvent event) {
					try {
						JSONObject object = new JSONObject(event.getResult());
						
						if(object.getString("result").equals("1")){
				            progress.dismiss();
				            SimpleDialog.getOKButtonDialog(context, "알림", "성공적으로 가입되었습니다. 바로 로그인 하시겠습니까?")
					            .setPositiveButton("예", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
										
										Controller.getInstance().getPlayer().setId(txtId.getText().toString());

								    	Intent intent = new Intent(LoginActivity.this, ConnActivity.class);
							            startActivity(intent);
									}
								})
								.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								})
								.show();
						} else {
		                	progress.dismiss();
		                	SimpleDialog.getOKButtonDialog(context, "알림", "등록된 아이디가 존재합니다.")
		                		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								})
		                		.show();
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, "http://treegames.co.kr/gachon/insertMember.php", 
				new LoginModel(txtId.getText().toString(), txtPwd.getText().toString()));
		}
	};
}
