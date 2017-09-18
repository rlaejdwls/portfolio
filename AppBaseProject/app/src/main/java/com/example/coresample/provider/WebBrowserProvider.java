/*
 * Class Name  : TigrisWebBrowserProvider
 * Description :
 * Author      : JungmoPark
 * Date        : 2016/01/01
 *
 * Copyright ⓒ 2015 by TigerCompany All right reserved.
 */
package com.example.coresample.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class WebBrowserProvider extends ContentProvider {
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri arg0) {
        return "vnd.com.example.coresample.web/vnd.com.example.coresample.web";
    }
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 1;
    }
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 1;
    }
}

