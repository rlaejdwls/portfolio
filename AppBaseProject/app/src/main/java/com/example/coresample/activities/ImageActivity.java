package com.example.coresample.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;
import com.example.coresample.R;
import com.example.coresample.dialog.SelectDialog;

import java.io.File;

import static com.example.core.util.Utils.REQUEST_CODE;

/**
 * Created by tigris on 2017-09-08.
 */
public class ImageActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind private TextView txtImageInfo;
    @Bind private ImageView imgView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this).onClick(R.id.btn_img_select);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode != RESULT_OK || data == null) return;
                String path = data.getStringExtra("imgPath");
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                imgView.setImageDrawable(Drawable.createFromPath(path));

                BitmapFactory.Options options = new BitmapFactory.Options();
                txtImageInfo.setText("Resolution:" + options.outWidth + "*" + options.outHeight + ", Size:" + (float)(new File(path).length()) / 1024.0f + "KB");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_img_select:
                startActivityForResult(new Intent(ImageActivity.this, SelectDialog.class), REQUEST_CODE);
                break;
        }
    }
}
