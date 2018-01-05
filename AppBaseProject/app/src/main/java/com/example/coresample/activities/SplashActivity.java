package com.example.coresample.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.core.AppCore;
import com.example.core.event.OnSingleClickListener;
import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;
import com.example.core.util.StringUtils;
import com.example.coresample.R;
import com.example.coresample.dialog.GlobalDialog;
import com.example.coresample.utils.FileUtil;
import com.example.coresample.widget.WidgetListActivity;
import com.example.coresample.widget.imageview.multitouchzoom.ImageViewActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//@GlideExtension
public class SplashActivity extends AppCompatActivity/* implements View.OnClickListener*/ {
    @Bind private TextView txtView01;
    @Bind private TextView txtView02;
    @Bind private TextView txtView03;
    @Bind private TextView txtView04;
    @Bind private ImageView imgTest;
    @Bind private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this)
                .onClick(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        switch (v.getId()) {
                            case R.id.btn_glide_test:
                                Glide.with(SplashActivity.this)
                                        .load("http://treegames.co.kr/test/uploads/temp3.jpg")
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                return false;
                                            }
                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }
                                        })
                                        .thumbnail(0.5f)
                                        .apply(RequestOptions.centerCropTransform()
                                                .circleCrop()
                                                .placeholder(R.drawable.anim_image_loading)
                                                .signature(new ObjectKey(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())))
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .error(R.drawable.img_error))
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(imgTest);
                                break;
                            case R.id.btn_dialog_test:
                                GlobalDialog.get()
                                        .with(SplashActivity.this)
                                        .build(StringUtils.toAlias(RealmActivity.class.getName()))
                                        .show();
                                break;
                            case R.id.btn_realm:
                                startActivity(new Intent(SplashActivity.this, RealmActivity.class));
                                break;
                            case R.id.btn_test:
                                TedPermission.with(SplashActivity.this)
                                        .setPermissionListener(new PermissionListener() {
                                            @Override
                                            public void onPermissionGranted() {
                                                String url = "https://file.tigrison.com/file/download/8619922511673ddab6d395d1eb60940b/1";
                                                String cookie = "_tigris_sid=fe2844854185b21cc21ee825a0cb10a7;domain=tigrison.com;path=/";
                                                String fileName = "error_20171207.log.txt";

                                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                                                request.addRequestHeader("Cookie", cookie);
                                                request.setDescription("Tigris Downlaod");
                                                request.setTitle(fileName);
                                                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileUtil.getExtension(fileName));
                                                request.setMimeType(mimeType);
                                                request.allowScanningByMediaScanner();
                                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                                                // get download service and enqueue file
                                                DownloadManager manager = (DownloadManager) AppCore.getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
                                                manager.enqueue(request);
                                            }
                                            @Override
                                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {}
                                        })
                                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                        .check();
//                                List<SelectItemModel> items = new ArrayList<>();
//                                items.add(new SelectItemModel("1", "Normal", 480, true));
//                                items.add(new SelectItemModel("2", "High", 720, false));
//                                items.add(new SelectItemModel("3", "Very High", 1080, false));
//
//                                List<OptionModel> options = new ArrayList<>();
//                                options.add(new OptionModel(items, new String[] { "1" }, "radio", "Set image quality"));
//
//                                String json = new Gson().toJson(options, new TypeToken<List<OptionModel>>() {}.getType());
//                                Logger.d(json);
                                break;
                            case R.id.btn_image:
                                startActivity(new Intent(SplashActivity.this, ImageActivity.class));
                                break;
                            case R.id.btn_library:
                                startActivity(new Intent(SplashActivity.this, LibraryActivity.class));
                                break;
                            case R.id.btn_pager:
                                startActivity(new Intent(SplashActivity.this, ImageViewActivity.class));
                                break;
                            case R.id.btn_widget:
                                startActivity(new Intent(SplashActivity.this, WidgetListActivity.class));
                                break;
                        }
                    }
                }, R.id.btn_glide_test, R.id.btn_dialog_test,
                        R.id.btn_realm, R.id.btn_test,
                        R.id.btn_image, R.id.btn_library,
                        R.id.btn_pager, R.id.btn_widget);
    }
}
