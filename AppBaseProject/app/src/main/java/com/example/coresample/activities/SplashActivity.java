package com.example.coresample.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.core.event.OnSingleClickListener;
import com.example.core.manage.Binder;
import com.example.core.manage.Logger;
import com.example.core.manage.annotation.Bind;
import com.example.core.util.StringUtils;
import com.example.coresample.R;
import com.example.coresample.activities.model.OptionModel;
import com.example.coresample.activities.model.SelectItemModel;
import com.example.coresample.dialog.GlobalDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@GlideExtension
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
                                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
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
                                List<SelectItemModel> items = new ArrayList<>();
                                items.add(new SelectItemModel("1", "Normal", 480, true));
                                items.add(new SelectItemModel("2", "High", 720, false));
                                items.add(new SelectItemModel("3", "Very High", 1080, false));

                                List<OptionModel> options = new ArrayList<>();
                                options.add(new OptionModel(items, new String[] { "1" }, "radio", "Set image quality"));

                                String json = new Gson().toJson(options, new TypeToken<List<OptionModel>>() {}.getType());
                                Logger.d(json);
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
                        }
                    }
                }, R.id.btn_glide_test, R.id.btn_dialog_test,
                        R.id.btn_realm, R.id.btn_test,
                        R.id.btn_image, R.id.btn_library,
                        R.id.btn_pager);
    }
}
