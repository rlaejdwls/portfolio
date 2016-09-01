package kr.ac.gachon.game.pingpong;

import java.util.ArrayList;

import kr.ac.gachon.game.pingpong.adapter.RankAdapter;
import kr.ac.gachon.game.pingpong.controller.Controller;
import kr.ac.gachon.game.pingpong.event.OnHttpConnectCallBack;
import kr.ac.gachon.game.pingpong.event.model.HttpConnectEvent;
import kr.ac.gachon.game.pingpong.model.RankModel;
import kr.ac.gachon.game.pingpong.util.ActivityReference;
import kr.ac.gachon.game.pingpong.util.HttpConnect;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class RankActivity extends Activity {
	private ArrayList<RankModel> rankList;
	private Context context;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		
		Button btnSearch = (Button)this.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ActivityReference.getActivity() != null) {
					ActivityReference.getActivity().finish();
				}
				((RankActivity) context).finish();
			}
		});
		
		rankList = new ArrayList<RankModel>();
		context = this;
		progress = ProgressDialog.show(RankActivity.this, "Wait", "Loding...");
		
		new HttpConnect(new OnHttpConnectCallBack() {
			@Override
			public void onHttpConnectCallBackEvent(HttpConnectEvent event) {
				progress.dismiss();
				try {
					rankList.add(new RankModel("순위", "사용자 아이디", "승률", false));
					
					JSONArray array = new JSONArray(event.getResult());
					int arrayLength = array.length();
					boolean isAbsence = true;
					
					for(int i = 0 ; i < arrayLength ; i++){
						JSONObject object = array.getJSONObject(i);
						boolean isPlayer = false;
						
						if (object.getString("recId").equals(Controller.getInstance().getPlayer().getId())) {
							isPlayer = true;
							isAbsence = false;
						}
						
						rankList.add(new RankModel(
								Integer.toString(i + 1), 
								object.getString("recId"), 
								object.getString("recWins") + "%",
								isPlayer));
					}
					
					if (isAbsence) {
						new HttpConnect(new OnHttpConnectCallBack() {
							@Override
							public void onHttpConnectCallBackEvent(HttpConnectEvent event) {
								try {
								JSONObject object = new JSONObject(event.getResult());
								rankList.add(new RankModel(
										object.getString("rank"), 
										object.getString("recId"), 
										(object.getString("recWins").equals("null") ? "None" : object.getString("recWins") + "%"),
										true));
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								RankAdapter adapter = new RankAdapter((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), 
										rankList, R.layout.item_rank_list);
								
								ListView lstRank = (ListView) ((RankActivity) context).findViewById(R.id.lst_rank);
								lstRank.setAdapter(adapter);
							}
						}, 
						"http://treegames.co.kr/gachon/selectRanking.php",
						"mem_id=" + Controller.getInstance().getPlayer().getId());
					}
					
					RankAdapter adapter = new RankAdapter((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), 
							rankList, R.layout.item_rank_list);
					
					ListView lstRank = (ListView) ((RankActivity) context).findViewById(R.id.lst_rank);
					lstRank.setAdapter(adapter);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},
		"http://treegames.co.kr/gachon/selectRecord.php");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		if (ActivityReference.getActivity() != null) {
			ActivityReference.getActivity().finish();
		}
		super.onBackPressed();
	}
}
