package com.example.coresample.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.core.fragment.NFragment;
import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tigris on 2017-09-21.
 */
public class ImageViewFragment extends NFragment {
    @Bind private ImageView imgView;
    @Bind private ProgressBar progress;

    @Override
    public View onCreateView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Binder.bind(view, this);

        String url = this.getArguments().getString("URL");
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(RequestOptions
                        .signatureOf(new ObjectKey(/*Long.toString(System.currentTimeMillis())*/new SimpleDateFormat("yyyyMMdd").format(new Date()))))
                .into(imgView);
        return super.onCreateView(view, inflater, container, savedInstanceState);
    }
}
