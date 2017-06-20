package com.example.core;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.core.manage.Config;
import com.example.core.manage.Debugger;
import com.example.core.manage.ExceptionHandler;
import com.example.core.manage.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Hwang on 2016-10-15.
 * 작성자 : 황의택
 * 내용 : 예외 관리, 공통 데이터 관리 담당
 *          v0.1.0 : 어플리케이션이 백그라운드로 관리되고 있을 때 데이터가 소실되는 현상이 발생한다고 함
 *                   (직접 목격하지는 못했음)
 *                   만약 위와 같은 현상이 나타날 경우 commonData가 소실되면 앱에 큰 문제가 발생됨
 *                   방지하기 위해서는 onTrimMemory 함수에서 데이터를 직렬화하여 로컬로 보관될 필요성이 있음
 *                   추후 구현이 필요함
 *
 *                   SharedPreference로 저장한 데이터들은 앱을 강제 종료 시키면 휘발되는 현상이 발생함
 *                   Config 파일은 추후 안드로이드 설정이 아닌 파일로 별도로 관리 할 예정
 */
public class AppCore extends Application {
    private static AppCore appCore = null;

    private boolean isBackground = false;

    private HashMap<Object, Object> commonData;
    private Config config;

    public AppCore() {
        super();
    }
    public synchronized static AppCore getApplication() {
        return appCore;
    }

    /**
     * 공통 저장소에서 데이터를 가져오는 함수
     * AppCore.getApplication().getCommonData().get()과 동일하다
     * @param key 키
     * @return 키에 저장된 값
     */
    public static Object get(String key) { return appCore.getCommonData().get(key); }
    /**
     * 공통 저장소에 저장하는 함수
     * AppCore.getApplication().getCommonData().put()과 동일하다
     * @param key 저장 키
     * @param value 저장 값
     */
    public static void put(Object key, Object value) {
        if (value != null) {
            appCore.getCommonData().put(key, value);
        }
    }
    /**
     * 공통 저장소와 시스템 Config에 같이 저장하는 함수
     * AppCore.getApplication().getCommonData().put()
     * AppCore.getApplication().getConfig().set();
     * 상위 두개를 호출한 것과 동일하다
     * @param key 저장 키
     * @param value 저장 값
     */
    public static Config save(String key, Object value) {
        if (value != null) {
            appCore.getCommonData().put(key, value);
            appCore.getConfig().set(key, value);
        }
        return appCore.getConfig();
    }
    /*
    * 공통 저장소와 시스템 Config에 모든 데이터를 저장하는 함수
    * @param result 저장 값 Map
    * @param isAutoLogin 자동 로그인 값
    */
    public static Config saveAll(Map result) {
        Iterator<String> iterator = result.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            appCore.save(key, result.get(key));
        }
        appCore.getConfig().commit();
        return appCore.getConfig();
    }
    /**
     * 공통 저장소에서 데이터를 삭제하는 함수
     * AppCore.getApplication().getCommonData().put()과 동일하다
     * @param key 삭제하는 데이터의 키
     */
    public static void remove(Object key) { appCore.getCommonData().remove(key); }

    /**
     * 공통 저장소에서 모든 데이터를 삭제하는 함수
     */
    public static void removeAll() {
        Iterator<?> iterator = appCore.getConfig().getIterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            appCore.commonData.remove(key);
        }
        appCore.getConfig().clear();
        appCore.getConfig().delete();
    }
    /**
     * 앱에서 저장한 환경설정을 가져올 수 있다
     *  - config 저장을 SharedPreferences에서 json 파일 저장으로 변경
     * @return
     */
    public Config getConfig() {
        if (config == null) {
            config = new Config(getFilesDir() + "/config.json");
        }
        return config;
    }
    /**
     * 공통 저장소를 가져오는 함수
     * @return 공통 HashMap
     */
    public HashMap<Object, Object> getCommonData() {
        return commonData;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();

        listenForForeground();
        listenForScreenTurningOff();
        notifyForeground();
    }
    public void initApplication() {
        //디버거 초기화
        Debugger.initialize(this);
        //로거 초기화
        Logger.initialize(this);
        //전역 예외 핸들러 선언
        new ExceptionHandler(this);
        //Application 싱글톤 저장
        AppCore.appCore = this;
        //공통 데이터 저장소
        commonData = new HashMap();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackground = true;
            notifyBackground();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void listenForForeground() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
            @Override
            public void onActivityStarted(Activity activity) {}
            @Override
            public void onActivityResumed(Activity activity) {
                if (isBackground) {
                    isBackground = false;
                    notifyForeground();
                }
            }
            @Override
            public void onActivityPaused(Activity activity) {}
            @Override
            public void onActivityStopped(Activity activity) {}
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            @Override
            public void onActivityDestroyed(Activity activity) {}
        });
    }
    private void listenForScreenTurningOff() {
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isBackground = true;
                notifyBackground();
            }
        }, screenStateFilter);
    }

    protected void notifyForeground() {
        commonData.put("DIR_LOG", appCore.getApplicationContext().getFilesDir() + "/log/log");
        commonData.put("DIR_ERROR", appCore.getApplicationContext().getFilesDir() + "/log/error");
        commonData.put("DIR_DATA", appCore.getApplicationContext().getFilesDir() + "/data");
        commonData.put("DIR_IMAGE", appCore.getApplicationContext().getFilesDir() + "/image");
    }

    protected void notifyBackground() {
    }
}
