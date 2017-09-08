package com.example.coresample.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.example.core.manage.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Created by needid on 2016-11-14.
 * 작성자 : 황의택
 * 내용 : Uri를 통하여 이미지의 사이즈를 조정하거나 압축하는 등의 작업을 수행하는 클래스
 */
public class ImageEditor {
    public static final int QUALITY_1080 = 1080;
    public static final int QUALITY_720 = 720;
    public static final int QUALITY_480 = 480;

    public static final int QUALITY_1440 = 1440;
    public static final int QUALITY_960 = 960;

    public static final int TAKE_PICTURE_FROM_CAMERA = 0;
    public static final int TAKE_PICTURE_FROM_GALLERY = 1;
    public static final int TAKE_PICTURE_FROM_SELECT_ACTIVITY = 2;

    //private Uri uri;
    private Context context;
    private ContentResolver resolver;
    private Matrix orientation;
    private String path;
    private int bitmapWidth;
    private int bitmapHeight;
    private int rotate;

    //!plan max 값 처리는 나중에..
//    static int MAX_WIDTH = 600;
//    static int MAX_HEIGHT = 800;

    public ImageEditor(Context context) {
        this.context = context;
        this.resolver = context.getContentResolver();
    }
    /*
    //!comment
    구글의 URI 접근 정책의 변경에 따라 대부분의 이미지 유틸함수를 사용 못함
    너무 급해서 막 고쳤는데 나중에 정리할 필요가 있음..
     */
    public ImageEditor(String path, Context context) {
        this.path = path;
        this.context = context;
        this.resolver = context.getContentResolver();
    }
//    public ImageEditor(File file, Context context) {
//        this(Uri.fromFile(file), context);
//    }
//    public ImageEditor(Uri uri, Context context) {
//        this.uri = uri;
//        this.context = context;
//        this.resolver = context.getContentResolver();
//    }
    /**
     *
     * @return
     * @throws IOException
     */
//    private boolean getInformation() throws IOException {
//        if (getInformationFromMediaDatabase())
//            return true;
//
//        if (getInformationFromFileSystem())
//            return true;
//
//        return false;
//    }
    /**
     *
     * @return
     */
//    private boolean getInformationFromMediaDatabase() {
//        String[] fields = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
//        Cursor cursor = resolver.query(uri, fields, null, null, null);
//
//        if (cursor == null)
//            return false;
//
//        cursor.moveToFirst();
//        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
//        this.orientation = new Matrix();
//        this.orientation.setRotate(orientation);
//        cursor.close();
//
//        return true;
//    }
    /**
     *
     * @return
     * @throws IOException
     */
    private boolean getInformationFromFileSystem(String path) throws IOException {
        if (path == null)
            return false;

        ExifInterface exif = new ExifInterface(path);
        rotate = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        this.orientation = new Matrix();
        switch (rotate) {
            case ExifInterface.ORIENTATION_NORMAL:
                /* Identity matrix */
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                this.orientation.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                this.orientation.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                this.orientation.setScale(1, -1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                this.orientation.setRotate(90);
                this.orientation.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                this.orientation.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                this.orientation.setRotate(-90);
                this.orientation.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                this.orientation.setRotate(-90);
                break;
        }

        return true;
    }
/*
    private boolean getBitmapDimensions() throws IOException {
        InputStream input = resolver.openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(resolver.openInputStream(uri), null, options);

        //!comment
        보통의 input stream은 닫고 다시 열수 있지만
        decodeStream을 이용한경우 내부에서 1024 제한을 풀고 읽기 때문에 불가능하다
        input.close();

        if (options.outHeight <= 0 || options.outWidth <= 0)
            return false;

        bitmapWidth = options.outWidth;
        bitmapHeight = options.outHeight;

        return true;
    }
*/
    private boolean getBitmapDimensions() throws IOException {
//        InputStream input = resolver.openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,/*resolver.openInputStream(uri), null,*/ options);

        if (options.outHeight <= 0 || options.outWidth <= 0)
            return false;

        bitmapWidth = options.outWidth;
        bitmapHeight = options.outHeight;

        return true;
    }
    public File getFileOfFormattedBitmap(int resolution) throws IOException, OutOfMemoryError {
        return getFileOfFormattedBitmap(null, resolution);
    }
    public File getFileOfFormattedBitmap(String filaName, int resolution) throws IOException, OutOfMemoryError {
        int quality;
        if (resolution >= QUALITY_1080) {
            quality = 100;
        } else if (resolution < QUALITY_1080 && resolution >= QUALITY_720) {
            quality = 90;
        } else {
            quality = 80;
        }

        return getFileOfFormattedBitmap(null, filaName, resolution, quality);
    }
    public File getFileOfFormattedBitmap(Bitmap.CompressFormat format, String fileName, int resolution, int quality)
            throws IOException, OutOfMemoryError {
        /*
        if (!getInformation())
            throw new FileNotFoundException();

        if (!getBitmapDimensions())
            throw new InvalidObjectException(null);

        RectF rect = new RectF(0, 0, bitmapWidth, bitmapHeight);
        orientation.mapRect(rect);
        int width = (int) rect.width();
        int height = (int) rect.height();
        int standard;

        if (rect.width() > rect.height()) {
            standard = height;
        } else {
            standard = width;
        }

        if (width == 0 || height == 0)
            throw new InvalidObjectException(null);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap subSampled = BitmapFactory.decodeStream(resolver.openInputStream(uri), null, options);
        Bitmap picture;

        if (standard <= resolution) {   //원하는 퀄리티(formatted)보다 낮은 해상도면 아무 변환 없이 다음 처리과정 수행
            picture = subSampled;
        } else {                        //원하는 퀄리티보다 높은 해상도일 경우 정형화 처리과정 수행
            if (rect.width() > rect.height()) {
                width = (width * resolution) / height;
                height = resolution;
            } else {
                height = (height * resolution) / width;
                width = resolution;
            }
            picture = Bitmap.createScaledBitmap(subSampled, width, height, true);
            subSampled.recycle();       //out of memory를 방지하는 가장 중요한 부분/Bitmap 처리의 경우 항상 메모리를 염두해두고 사용 할 것
        }*/

        /*
        picture 파일처리
        */
        Bitmap picture = getBitmapOfFormatted(resolution);

        String filePath = context.getCacheDir().getAbsolutePath();
        if (fileName != null) {
            filePath += "/" + fileName;
        } else {
            filePath += "/temp" + System.currentTimeMillis();
        }

        if (format != null) {
            filePath += "." + format.name().toLowerCase();
        }

        Logger.d(filePath);
        File file = new File(filePath);
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        picture.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        fos.close();

        if (picture != null) {
            picture.recycle();
        }

        return file;
    }
    public Bitmap getBitmapOfFormatted(int resolution)
            throws IOException, OutOfMemoryError {
        if (!getInformationFromFileSystem(path))
            throw new FileNotFoundException();

        if (!getBitmapDimensions())
            throw new InvalidObjectException(null);

        RectF rect = new RectF(0, 0, bitmapWidth, bitmapHeight);
        orientation.mapRect(rect);
        int width = (int) rect.width();
        int height = (int) rect.height();
        int standard;

        if (rect.width() > rect.height()) {
            standard = height;
        } else {
            standard = width;
        }

        if (width == 0 || height == 0)
            throw new InvalidObjectException(null);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap subSampled = BitmapFactory.decodeFile(path,/*resolver.openInputStream(uri), null,*/ options);
        Bitmap picture;

        if (standard <= resolution) {   //원하는 퀄리티(formatted)보다 낮은 해상도면 아무 변환 없이 다음 처리과정 수행
            picture = subSampled;
        } else {                        //원하는 퀄리티보다 높은 해상도일 경우 정형화 처리과정 수행
            if (rect.width() > rect.height()) {
                width = (width * resolution) / height;
                height = resolution;
            } else {
                height = (height * resolution) / width;
                width = resolution;
            }

            if (rotate == ExifInterface.ORIENTATION_ROTATE_90 || rotate == ExifInterface.ORIENTATION_ROTATE_270 ||
                    rotate == ExifInterface.ORIENTATION_TRANSPOSE || rotate == ExifInterface.ORIENTATION_TRANSVERSE) {
                picture = Bitmap.createScaledBitmap(subSampled, height, width, true);
            } else {
                picture = Bitmap.createScaledBitmap(subSampled, width, height, true);
            }
            subSampled.recycle();       //out of memory를 방지하는 가장 중요한 부분/Bitmap 처리의 경우 항상 메모리를 염두해두고 사용 할 것
            if (rotate != ExifInterface.ORIENTATION_NORMAL) {
                picture = Bitmap.createBitmap(picture, 0, 0, picture.getWidth(), picture.getHeight(), orientation, false);
            }
        }

        return picture;
    }
    public File getFileCacheFromBitmap(Bitmap bmp, String path, int quality) throws IOException {
        return getFileCacheFromBitmap(bmp, new File(path), quality);
    }
    public File getFileCacheFromBitmap(Bitmap bmp, File file, int quality) throws IOException {
        FileOutputStream fos = null;

        try {
            file.createNewFile();

            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
/*  //!plan 함수 오버로딩은 나중에 하기로.. 시간이 부족함/비트맵이 바로 필요해 졌을 때 만들면 되지.. 뭐..
    public Bitmap getBitmap() throws IOException {
        if (!getInformation())
            throw new FileNotFoundException();

        if (!getStoredDimensions())
            throw new InvalidObjectException(null);

        RectF rect = new RectF(0, 0, storedWidth, storedHeight);
        orientation.mapRect(rect);
        int width = (int) rect.width();
        int height = (int) rect.height();
//        int subSample = 1;

//        while (width > MAX_WIDTH || height > MAX_HEIGHT) {
//            width /= 2;
//            height /= 2;
//            subSample *= 2;
//        }

        if (width == 0 || height == 0)
            throw new InvalidObjectException(null);

        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = subSample;
        Bitmap subSampled = BitmapFactory.decodeStream(resolver.openInputStream(uri), null, options);

        Bitmap picture;
//        if (!orientation.isIdentity()) {
//        Bitmap.createBitmap(subSampled, 0, 0, 1920, 1080, true);
//            picture = Bitmap.createBitmap(subSampled, 0, 0, options.outWidth, options.outHeight,
//                    orientation, false);
        picture = Bitmap.createScaledBitmap(subSampled, (options.outWidth * QUALITY_720) / options.outHeight, QUALITY_720, true);
        subSampled.recycle();
//        } else
//            picture = subSampled;

        return picture;
    }
    */
    public void getRotate(Uri uri) {
//        boolean hasRotation = false;
        String[] projection = { MediaStore.Images.Media.ORIENTATION };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            rotate = cursor.getInt(cursor.getColumnIndex(projection[0]));
//                hasRotation = true;
        }
        this.orientation = new Matrix();
        this.orientation.postRotate(rotate);
        cursor.close();
    }

    public Bitmap getImageResized(String path, int resolution)
            throws IOException, OutOfMemoryError {
        if (!getInformationFromFileSystem(path))
            throw new FileNotFoundException();

        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeFile(path, options);

        int large = (options.outHeight > options.outWidth ?
                options.outHeight : options.outWidth);

        int sampleSize = large / resolution;
        Bitmap subSampled = decodeBitmap(path, (sampleSize < 1 ? 1 : sampleSize));

        int height = subSampled.getHeight();
        int width = subSampled.getWidth();
        large = (height > width ? height : width);
        int small = (height > width ? width : height);
        boolean isHeight = height > width;
        float newWidth = (isHeight ? (resolution * small / large) : resolution );
        float newHeight = (isHeight ? resolution : (resolution * small / large));

        Bitmap picture;
        if (large > resolution) {
            orientation.postScale(newWidth / width, newHeight / height);
//            subSampled = Bitmap.createScaledBitmap(subSampled, (isHeight ? (resolution * small / large) : resolution ), (isHeight ? resolution : (resolution * small / large)), true);
        }
        if ((rotate != ExifInterface.ORIENTATION_NORMAL) || (large > resolution)) {
                picture = Bitmap.createBitmap(subSampled, 0, 0, subSampled.getWidth(), subSampled.getHeight(), orientation, false);
            /*
            //!comment
            Bitmap.createBitmap에서 생성하는 bitmap이 LG(LG-F570S) 스마트폰에서는 새로운 객체에 할당되어 반환되고
            다른 종류의 스마트폰에서는 같은 객체(같은 주소값)에 반환되는 현상이 있음 그래서 새로운 인스턴스로 반환되었을 경우에만 recycle 처리를 해줌
             */
            if (picture != subSampled) {
                subSampled.recycle();
            }
        } else {
            picture = subSampled;
        }
        return picture;
    }
    /**
     * 2017-03-03 추가
     * 이미지가 너무 클 경우 하위 버전의 안드로이드 혹은 일부 기기에서 사진을 불러오지 못하는 현상이 발생하여 추가함
     * //!plan 추후에 이미지 샘플링 함수로 깔끔하게 정리할 필요성이 있음
     * //!comment compile 'com.github.Mariovc:ImagePicker:1.0.5' 참조함
     * @param uri
     * @return
     */
    public Bitmap getImageResized(Uri uri, int resolution)
            throws IOException, OutOfMemoryError {
        String path = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                ? this.getPathForV19AndUp(context, uri)
                : this.getPathForPreV19(context, uri));
        return getImageResized(path, resolution);
//        if (!getInformationFromFileSystem(path))
//            throw new FileNotFoundException();
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        AssetFileDescriptor fileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
//        BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
//
//        int large = (options.outHeight > options.outWidth ?
//                options.outHeight : options.outWidth);
//
//        int sampleSize = large / resolution;
//        final Bitmap subSampled = decodeBitmap(context, uri, (sampleSize < 1 ? 1 : sampleSize));
//
//        int height = subSampled.getHeight();
//        int width = subSampled.getWidth();
//        large = (height > width ? height : width);
//        int small = (height > width ? width : height);
//        boolean isHeight = height > width;
//
//        Bitmap picture;
//        if (rotate != ExifInterface.ORIENTATION_NORMAL) {
//            if (large > resolution) {
//                picture = Bitmap.createScaledBitmap(subSampled, (isHeight ? (resolution * small / large) : resolution ), (isHeight ? resolution : (resolution * small / large)), true);
//            } else {
//                picture = Bitmap.createBitmap(subSampled, 0, 0, subSampled.getWidth(), subSampled.getHeight(), orientation, false);
//            }
//            /*
//            //!comment
//            Bitmap.createBitmap에서 생성하는 bitmap이 LG(LG-F570S) 스마트폰에서는 새로운 객체에 할당되어 반환되고
//            다른 종류의 스마트폰에서는 같은 객체(같은 주소값)에 반환되는 현상이 있음 그래서 새로운 인스턴스로 반환되었을 경우에만 recycle 처리를 해줌
//             */
//            if (picture != subSampled) {
//                subSampled.recycle();
//            }
//        } else {
//            picture = subSampled;
//        }
//
//        return picture;
    }
    public Bitmap decodeBitmap(Context context, Uri theUri, int sampleSize) {
        Bitmap actuallyUsableBitmap = null;
        AssetFileDescriptor fileDescriptor = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(theUri, "r");

            actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
            fileDescriptor.close();
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }

        return actuallyUsableBitmap;
    }
    public Bitmap decodeBitmap(String path, int sampleSize) {
        Bitmap actuallyUsableBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        try {
            actuallyUsableBitmap = BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
        return actuallyUsableBitmap;
    }
    /**
     * Handles pre V19 uri's
     * @param context
     * @param contentUri
     * @return
     */
    public String getPathForPreV19(Context context, Uri contentUri) {
        String res = null;

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();

        return res;
    }
    /**
     * Handles V19 and up uri's
     * @param context
     * @param contentUri
     * @return path
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPathForV19AndUp(Context context, Uri contentUri) {
        String wholeID = DocumentsContract.getDocumentId(contentUri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];
        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = context.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{ id }, null);

        String filePath = "";
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }

        cursor.close();
        return filePath;
    }
}
