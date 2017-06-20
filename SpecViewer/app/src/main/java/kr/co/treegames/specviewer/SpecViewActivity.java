package kr.co.treegames.specviewer;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.lang.reflect.Field;

public class SpecViewActivity extends AppCompatActivity {
    private TextView txtSpec;

    private final String LINE_SEPARATOR = "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec_view);

//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int densityDpi = (int)(metrics.density * 160f);
        txtSpec = (TextView) this.findViewById(R.id.txtSpec);

        StringBuilder sb = new StringBuilder();
        sb.append(LINE_SEPARATOR);
        sb.append("************ DEVICE INFORMATION ***********");
        sb.append(LINE_SEPARATOR);
        sb.append("Brand: ");
        sb.append(Build.BRAND);
        sb.append(LINE_SEPARATOR);
        sb.append("Device: ");
        sb.append(Build.DEVICE);
        sb.append(LINE_SEPARATOR);
        sb.append("Model: ");
        sb.append(Build.MODEL);
        sb.append(LINE_SEPARATOR);
        sb.append("Id: ");
        sb.append(Build.ID);
        sb.append(LINE_SEPARATOR);
        sb.append("Serial: ");
        sb.append(Build.SERIAL);
        sb.append(LINE_SEPARATOR);
        sb.append("Product: ");
        sb.append(Build.PRODUCT);
        sb.append(LINE_SEPARATOR);
        sb.append("************ DEVICE INFORMATION ***********");
        sb.append(LINE_SEPARATOR);

        sb.append(LINE_SEPARATOR);
        sb.append("************ FIRMWARE OF ANDROID ************");
        sb.append(LINE_SEPARATOR);
        sb.append("SDK: ");
        sb.append(Build.VERSION.SDK_INT);
        sb.append(LINE_SEPARATOR);
        sb.append("Release: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append(LINE_SEPARATOR);
        sb.append("Incremental: ");
        sb.append(Build.VERSION.INCREMENTAL);

        String alias = "";
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);

            try {
                Integer value = (Integer) fields[i].get(Build.VERSION_CODES.class);
                if (Build.VERSION.SDK_INT == value) {
                    sb.append(LINE_SEPARATOR);
                    sb.append("Alias: ");
                    alias = fields[i].getName();
                    sb.append(alias);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        switch (alias) {
            case "M":
                sb.append(LINE_SEPARATOR);
                sb.append("Alias: ");
                sb.append("MARSHMALLOW");
                break;
            case "N":
            case "N_MR":
                sb.append(LINE_SEPARATOR);
                sb.append("Alias: ");
                sb.append("NOUGAT");
                break;
        }

        sb.append(LINE_SEPARATOR);
        sb.append("************ FIRMWARE OF ANDROID ************");
        sb.append(LINE_SEPARATOR);

        sb.append(LINE_SEPARATOR);
        sb.append("************ SCREEN INFORMATION ************");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        sb.append(LINE_SEPARATOR);
        sb.append("DPI: ");
        sb.append(density);
        sb.append(LINE_SEPARATOR);
        sb.append("Density: ");
        sb.append((int)(density * 160f));

        sb.append(LINE_SEPARATOR);
        sb.append("DPI Alias: ");
        if (density == 0.75f) {
            sb.append("ldpi");
        } else if (density == 1.0f) {
            sb.append("mdpi");
        } else if (density == 1.5f) {
            sb.append("hdpi");
        } else if (density == 2.0f) {
            sb.append("xhdpi");
        } else if (density == 3.0f) {
            sb.append("xxhdpi");
        } else if (density == 4.0f) {
            sb.append("xxxhdpi");
        } else {
            sb.append((int)(density * 160f));
        }
        sb.append(LINE_SEPARATOR);
        sb.append("Resolution: ");
        sb.append(dm.heightPixels + "x" + dm.widthPixels);
        sb.append(LINE_SEPARATOR);
        sb.append("DP Resolution: ");
        sb.append((int)(dm.heightPixels/density) + "x" + (int)(dm.widthPixels/density));
        sb.append(LINE_SEPARATOR);
        sb.append("************ SCREEN INFORMATION ************");
        sb.append(LINE_SEPARATOR);

        txtSpec.setText(sb.toString());
    }
}
