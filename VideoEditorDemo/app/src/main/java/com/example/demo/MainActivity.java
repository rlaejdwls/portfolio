package com.example.demo;

import android.Manifest;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.core.event.OnSingleClickListener;
import com.example.core.manage.Logger;
import com.example.demo.adapter.SquareGridAdapter;
import com.example.demo.event.DemoPermissionListener;
import com.example.demo.event.OnListItemClickListener;
import com.example.demo.manage.VideoEditor;
import com.example.demo.manage.model.VideoClip;
import com.example.demo.service.VideoEditService;
import com.example.demo.util.NFUtils;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ir.sohreco.androidfilechooser.ExternalStorageNotAvailableException;
import ir.sohreco.androidfilechooser.FileChooserDialog;

public class MainActivity extends AppCompatActivity {
    private final int THUMBNAIL_SIZE = 9;

    private SquareGridAdapter adapter;

    private EditText txtDataPath;

    private PermissionListener permissionlistener = new DemoPermissionListener();
    private String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NFData";
    private String output = "";

    private double startVideoTime = 0;
    private double endVideoTime = 0;

    private boolean isStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

        txtDataPath = (EditText) this.findViewById(R.id.txtDataPath);

        RecyclerView lstFrame = (RecyclerView) this.findViewById(R.id.lstFrame);
        lstFrame.setHasFixedSize(true);
        lstFrame.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new SquareGridAdapter();
        lstFrame.setAdapter(adapter);
        //startActivity(new Intent(MainActivity.this, VideoEditActivity.class));

        this.findViewById(R.id.btnOpen).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try {
                    new FileChooserDialog.Builder(FileChooserDialog.ChooserType.FILE_CHOOSER, new FileChooserDialog.ChooserListener() {
                        @Override
                        public void onSelect(String path) {
                            String[] paths = path.split(FileChooserDialog.FILE_NAMES_SEPARATOR);
                            txtDataPath.setText(paths[0]);

                            if (!new File(txtDataPath.getText().toString()).exists()) {
                                Toast.makeText(MainActivity.this, "File not found, " + txtDataPath.getText().toString(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent intent = new Intent(MainActivity.this, VideoEditActivity.class);
                            intent.putExtra("srcPath", txtDataPath.getText().toString());
                            startActivity(intent);
                        }
                    }).setFileFormats(new String[] { "mp4" })
                            .build().show(getSupportFragmentManager(), null);
                } catch (ExternalStorageNotAvailableException e) {
                    Logger.printStackTrace(e);
                }
            }
        });
        this.findViewById(R.id.btnGetBitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!new File(txtDataPath.getText().toString()).exists()) {
                        Toast.makeText(MainActivity.this, "File not found, " + txtDataPath.getText().toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ArrayList<Map> items = new ArrayList<>();
                    items.clear();
                    MediaMetadataRetriever mRetriever = new MediaMetadataRetriever();
                    mRetriever.setDataSource(txtDataPath.getText().toString());

                    String time = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long milli = Long.parseLong(time);

                    long interval = milli / THUMBNAIL_SIZE;

                    for (int i = 0; i <= THUMBNAIL_SIZE; i++) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("imgScene", mRetriever.getFrameAtTime(interval * 1000 * i,
                                MediaMetadataRetriever.OPTION_CLOSEST_SYNC));
                        item.put("lblTime", NFUtils.getFormatTime(interval * i));
                        item.put("milliSecond", Long.toString(interval * i));
                        items.add(item);
                    }
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.findViewById(R.id.btnCut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new File(txtDataPath.getText().toString()).exists()) {
                    Toast.makeText(MainActivity.this, "File not found, " + txtDataPath.getText().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (startVideoTime >= endVideoTime) {
                    Toast.makeText(MainActivity.this, "Start time must be less than end time.", Toast.LENGTH_LONG).show();
                    return;
                }
                VideoEditor.builder()
                        .setSrcPath(txtDataPath.getText().toString())
                        .setTargetPath(root + "/video/output.mp4")
                        .setVideoClip(new VideoClip(startVideoTime, endVideoTime))
                        .build()
                        .cut();
            }
        });
        this.findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoDecoderActivity.class));
            }
        });
        this.findViewById(R.id.btnCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new File(txtDataPath.getText().toString()).exists()) {
                    Toast.makeText(MainActivity.this, "OK, " + txtDataPath.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "File not found, " + txtDataPath.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.findViewById(R.id.btnTest).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                startService(new Intent(getApplicationContext(), VideoEditService.class));
            }
        });
        adapter.setOnListItemClickListener(new OnListItemClickListener() {
            @Override
            public void onListItemClick(int position, Map item) {
                if (isStart) {
                    Toast.makeText(MainActivity.this, "StartTime:" + item.get("milliSecond"), Toast.LENGTH_SHORT).show();
                    startVideoTime = Long.parseLong((String) item.get("milliSecond")) / 1000;
                    isStart = false;
                } else {
                    Toast.makeText(MainActivity.this, "EndTime:" + item.get("milliSecond"), Toast.LENGTH_SHORT).show();
                    endVideoTime = Long.parseLong((String) item.get("milliSecond")) / 1000;
                    isStart = true;
                }
            }
        });
    }
}
