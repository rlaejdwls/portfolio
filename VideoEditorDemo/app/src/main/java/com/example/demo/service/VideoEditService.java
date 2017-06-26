package com.example.demo.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.demo.R;
import com.example.demo.manage.VideoEditor;
import com.example.demo.manage.model.VideoClip;

/**
 * Created by Hwang on 2017-06-13.
 * 작성자 : 황의택
 * 내용 : 비디오를 편집하기 위한 서비스
 */
public class VideoEditService extends IntentService {
    private static final String SERVICE_NAME = "video_edit_service";

    public VideoEditService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final int ID_INDETERMINATE_SERVICE = 9000;
        final int ID_NOTIFICATION_COMPLETE = 400;

        String srcPath = intent.getStringExtra("srcPath");
        String targetPath = intent.getStringExtra("targetPath");
        double startTime = intent.getDoubleExtra("startTime", 0);
        double stopTime = intent.getDoubleExtra("stopTime", 0);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Video Editor")
                .setContentText("Editing Now ...")
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        notification.setProgress(0, 0, true);
        startForeground(ID_INDETERMINATE_SERVICE, notification.build());

        VideoEditor.builder()
                .setSrcPath(srcPath)
                .setTargetPath(targetPath)
                .setVideoClip(new VideoClip(startTime / 1000, stopTime / 1000))
                .build()
                .cut();

        notification.setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setProgress(0, 0, false)
                .setContentText("You have successfully finished editing the video.")
                .setLights(ContextCompat.getColor(
                        getApplicationContext(), R.color.colorPrimary), 1000, 1000)
                .setVibrate(new long[]{ 800, 800, 800, 800 })
                .setDefaults(Notification.DEFAULT_SOUND);

        /*************************************************************
         * Our startForeground() notification will be removed when we
         * call stopSelf(), so we supply a new ID to create a notification
         * we want persisted.
         *************************************************************/
        notificationManager.notify(ID_NOTIFICATION_COMPLETE, notification.build());

        stopSelf();
    }
}
