package com.example.core.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.core.R;
import com.example.core.manage.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Hwang on 2016-12-06.
 * 작성자 : 황의택
 * 내용 : 기타 잡다구리 유틸
 */
public class Utils {
    public static final int DISABLE = -1;
    public static final int REQUEST_CODE = 0;

    /*
    다이얼로그
     */
    public static void showConfirmDialog(
            Context context,
            int message,
            DialogInterface.OnClickListener positiveListener) {
//        showConfirmDialog(context, R.string.dialog_confirm_title, message, positiveListener, true);
    }
    public static void showConfirmDialog(
            Context context,
            int title,
            int message,
            DialogInterface.OnClickListener positiveListener) {
//        showConfirmDialog(context, title, message, positiveListener, true);
    }
    public static void showConfirmDialog(
            Context context,
            int title,
            int message,
            DialogInterface.OnClickListener positiveListener,
            boolean isShowCancel) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.dialog_lime_orange);
        if (title != DISABLE) dialog.setTitle(title);
        if (message != DISABLE) dialog.setMessage(message);
        dialog.setPositiveButton(R.string.ok, positiveListener);
        dialog.setCancelable(false);
        if (isShowCancel) {
            dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        dialog.create().show();
    }

    /*
    포멧
     */
    public static String formatComma(Object data) {
        if (data != null && !data.equals("")) {
            if (data instanceof String) {
                DecimalFormat formatter = new DecimalFormat("###,###.####");
                return formatter.format(Long.parseLong((String) data));
            } else {
                DecimalFormat formatter = new DecimalFormat("###,###.####");
                return formatter.format(data);
            }
        } else {
            return "0";
        }
    }

    /*
    형변환
     */
    public static JSONObject mapToJson(Map map) {
        JSONObject result = new JSONObject();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                result.put(key, map.get(key));
            } catch (JSONException e) {
                Logger.printStackTrace(e);
            }
        }
        return result;
    }

    /*
    날짜 관련
     */
    public static int getMaximumDayOfMonth(Object obj, SimpleDateFormat format) {
        if (obj != null && !obj.equals("")) {
            String dateString = (String) obj;
            if (isValidDate(dateString, format)) {
                try {
                    Date date = format.parse(dateString);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                } catch (ParseException e) {
                    Logger.printStackTrace(e);
                }
            }
        }
        return 0;
    }
    public static int getDay(Object obj, SimpleDateFormat format) {
        if (obj != null && !obj.equals("")) {
            String dateString = (String) obj;
            if (isValidDate(dateString, format)) {
                try {
                    Date date = format.parse(dateString);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    return calendar.get(Calendar.DAY_OF_MONTH);
                } catch (ParseException e) {
                    Logger.printStackTrace(e);
                }
            }
        }
        return 0;
    }
    public static long diffOfDate(String begin, String end, SimpleDateFormat formatter) {
        try {
            Date beginDate = formatter.parse(begin);
            Date endDate = formatter.parse(end);

            long diff = endDate.getTime() - beginDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffDays;
        } catch (Exception e) {
            return 0;
        }
    }
    public static boolean isValidDate(String inDate, SimpleDateFormat format) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(inDate.trim());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /*
    기타
     */
    public static String floor(Object obj) {
        if (obj instanceof Double) {
            obj = Double.toString((double) obj);
        }
        if (obj != null && !obj.equals("")) {
            String data = ((String) obj);
            int index = data.indexOf(".");
            if (index == -1) {
                index = data.length();
            }
            return Utils.formatComma(data.substring(0, index));
        } else {
            return "0";
        }
    }
}
