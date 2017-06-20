package com.example.ar.demo;

import android.Manifest;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.ar.demo.event.DemoPermissionListener;
import com.example.ar.demo.event.OnReceiveDataListener;
import com.example.ar.demo.manage.GPSManager;
import com.example.ar.demo.model.LocationObjet;
import com.example.ar.demo.restful.service.GooglePlaceService;
import com.example.ar.demo.view.DisplayVIew;
import com.example.ar.demo.view.OverlayKalmanFilterView;
import com.example.core.manage.Logger;
import com.google.gson.internal.LinkedTreeMap;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hwang on 2017-04-26.
 * 작성자 : 황의택
 * 내용 : 주변의 지역 정보를 표시하는 증강현실 데모 메인
 */
public class MainActivity extends AppCompatActivity {
    private final static int MSG_DATA_RECEIVE_COMPLETE = 0;

    private DataReceiveHandler handler = new DataReceiveHandler();
    private PermissionListener permissionlistener = new DemoPermissionListener();

    private FrameLayout panel;
    private DisplayVIew display;
    private OverlayKalmanFilterView content;

    private GooglePlaceService service;

    private String dataStr;

    private boolean isPause = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

        panel = (FrameLayout) findViewById(R.id.viewDisplay);
/*
        this.findViewById(R.id.btnPause).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (!isPause) {
                    isPause = true;
                    display.onPause();
                } else {
                    isPause = false;
                    display.onResume();
                }
            }
        });
*/
        //서버 정보 초기화
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Uri.Builder().scheme("http").authority("www.treegames.co.kr").build().toString())
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GooglePlaceService.class);

        //GPS 데이터 수신
        GPSManager.with(this)
            .setOnReceiveDataListener(new OnReceiveDataListener() {
                @Override
                public void onReceiveData(Location firstLocation) {
                    //GPS 최초 수신 시 서버에 데이터 요청
                    service.list(
                            Double.toString(firstLocation.getLatitude()) + "," + Double.toString(firstLocation.getLongitude()),
                            "50",       //반경
                            "food",     //검색 장소 타입
                            getString(R.string.google_api_key)
                    ).enqueue(new Callback<HashMap>() {
                        @Override
                        public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                            int responseCode = response.code();

                            if (responseCode == 200) {
                                //정상 데이터 반환
                                HashMap result = response.body();
                                if (result.containsKey("status") && result.get("status").toString().equals("OK")) {
                                    JSONArray array = new JSONArray();
                                    for (LinkedTreeMap data : (List<LinkedTreeMap>) result.get("results")) {
                                        JSONObject obj = new JSONObject();
                                        try {
                                            obj.put("id", data.get("id").toString());
                                            obj.put("name", (data.containsKey("name") ? (String) data.get("name") : (String) data.get("id")));
                                            obj.put("lat", ((LinkedTreeMap) ((LinkedTreeMap) data.get("geometry")).get("location")).get("lat"));
                                            obj.put("lng", ((LinkedTreeMap) ((LinkedTreeMap) data.get("geometry")).get("location")).get("lng"));
                                            array.put(obj);
                                        } catch (JSONException e) {
                                            Logger.printStackTrace(e);
                                        }
                                    }
                                    dataStr = array.toString();
                                    handler.sendEmptyMessage(MSG_DATA_RECEIVE_COMPLETE);
                                } else {
                                    //데이터 수신 실패시 임의 데이터 입력
                                    Map<String, LocationObjet> objets = new HashMap<>();
                                    objets.put("bangbe_police_office", new LocationObjet("방배경찰서", 37.4813957d, 126.9830805d, 21.36230087280273d));
                                    objets.put("isu_school", new LocationObjet("이수초등학교", 37.4780457d, 126.9823607d, 21.36230087280273d));
                                    objets.put("ddoreore_bangbe2", new LocationObjet("또래오래 방배2호점", 37.4825409d, 126.9843516d/*, 21.36230087280273d*/));
                                    objets.put("prebong_bakery", new LocationObjet("프레봉베이커리", 37.4825452d, 126.9839435d/*, 21.36230087280273d*/));
                                    objets.put("solars_bakery", new LocationObjet("Solars Bakery cafe", 37.48257989999999d, 126.9841899d/*, 21.36230087280273d*/));
                                    objets.put("ginmiga", new LocationObjet("진미가", 37.4820249d, 126.9841433d/*, 21.36230087280273d*/));
                                    objets.put("hanra_mart", new LocationObjet("한라마트", 37.4819341d, 126.9837491d/*, 21.36230087280273d*/));
                                    attach(objets);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<HashMap> call, Throwable t) {
                            Logger.printStackTrace(t);
                        }
                    });
                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (content != null) content.onDestroy();
    }

    private void attach(Map<String, LocationObjet> objets) {
        display = new DisplayVIew(MainActivity.this, MainActivity.this);
        panel.addView(display);

        content = new OverlayKalmanFilterView(this);
        content.setObjets(objets);
        panel.addView(content);
    }

    //수신된 JSON 데이터 모델로 변환
    private class DataReceiveHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DATA_RECEIVE_COMPLETE:
                    try {
                        Map<String, LocationObjet> objets = new HashMap<>();

                        JSONArray array = new JSONArray(dataStr);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = (JSONObject) array.get(i);
                            objets.put((String) obj.get("id"), new LocationObjet(
                                    (String) obj.get("name"),
                                    (Double) obj.get("lat"),
                                    (Double) obj.get("lng")
                            ));
                            Logger.d(obj.toString());
                        }

                        attach(objets);
                    } catch (JSONException e) {
                        Logger.printStackTrace(e);
                    }
                    break;
            }
        }
    }
}
