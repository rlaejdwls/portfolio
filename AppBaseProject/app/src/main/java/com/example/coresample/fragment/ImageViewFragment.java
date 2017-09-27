package com.example.coresample.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.core.fragment.NFragment;
import com.example.core.manage.Binder;
import com.example.core.manage.Logger;
import com.example.core.manage.annotation.Bind;
import com.example.coresample.widget.imageview.multitouchzoom.view.MultiTouchZoomImageView;

/**
 * Created by tigris on 2017-09-21.
 */
public class ImageViewFragment extends NFragment {
    @Bind private MultiTouchZoomImageView imgTouchView;
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
                .apply(new RequestOptions()
                        .signature(new ObjectKey(Long.toString(System.currentTimeMillis()))))
                .into(new DrawableImageViewTarget(imgTouchView) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        if (resource != null) {
                            imgTouchView.setImageBitmap(drawableToBitmap(resource));
                        }
                    }
                });

        Logger.d("width:" + imgTouchView.getWidth() + ", height:" + imgTouchView.getHeight());
        imgTouchView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imgTouchView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Logger.d("width:" + imgTouchView.getWidth() + ", height:" + imgTouchView.getHeight());
            }
        });
        imgView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imgView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Logger.d("width:" + imgView.getWidth() + ", height:" + imgView.getHeight());
            }
        });
        return super.onCreateView(view, inflater, container, savedInstanceState);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
