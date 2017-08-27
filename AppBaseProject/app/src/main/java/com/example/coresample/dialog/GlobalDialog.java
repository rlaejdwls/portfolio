package com.example.coresample.dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.coresample.R;

/**
 * Created by rlaej on 2017-08-26.
 */
public class GlobalDialog {
    private static GlobalDialog instance;
    private Dialog dialog;
    private Context context;

    private GlobalDialog() {
    }

    public synchronized static GlobalDialog get() {
        if (instance == null) {
            instance = new GlobalDialog();
        }
        return instance;
    }

    public GlobalDialog with(Context context) {
        this.context = context;
        return this;
    }

    public GlobalDialog build(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog_lime_orange);
//        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        String positiveText = context.getString(R.string.retry);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        String negativeText = context.getString(R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog = builder.create();
        dialog.show();
        return this;
    }

    public GlobalDialog build() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new Dialog(context, R.style.dialog_transparent);
        dialog.setCancelable(false);
        dialog.addContentView(new ProgressBar(context),
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        //배경의 반투명 그림자 제거
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return this;
    }

    public void show() {
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing() && dialog != null) {
                dialog.show();
            }
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
