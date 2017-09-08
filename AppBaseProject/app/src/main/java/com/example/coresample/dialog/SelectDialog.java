package com.example.coresample.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.manage.Logger;
import com.example.core.util.Utils;
import com.example.coresample.R;
import com.example.coresample.dialog.model.SpinnerData;
import com.example.coresample.utils.FileUtil;
import com.example.coresample.utils.ImageEditor;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by needid on 2016-11-09.
 * 작성자 : 황의택
 * 화면번호 : 없음
 * 내용 : 아이템 선택 Activity
 * Location : Mypage > Select
 */
public class SelectDialog extends ListActivity {
    public final String CAMERA = "1";
    public final String GALLERY = "2";
    public final String DEFAULT = "3";

    private Uri imgUri;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Logger.d("Granted");
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        showDialog();
                    }
                })
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

        int titleDividerId = getResources().getIdentifier("titleDivider", "id", "android");
        View view = this.getWindow().getDecorView().findViewById(titleDividerId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(getColor(R.color.main));
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.main));
        }

        ArrayList<SpinnerData> dataList = new ArrayList<>();
        dataList.add(new SpinnerData(CAMERA, getString(R.string.camera)));
        dataList.add(new SpinnerData(GALLERY, getString(R.string.gallery)));
        dataList.add(new SpinnerData(DEFAULT, getString(R.string.default_image)));

        SimpleListAdapter adapter = new SimpleListAdapter(this, dataList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpinnerData data = ((SimpleListAdapter) parent.getAdapter()).getList().get(position);
                switch (data.getId()) {
                    case CAMERA:
                        takePhoto();
                        break;
                    case GALLERY:
                        pickGallery();
                        break;
                    case DEFAULT:
                        defaultImage();
                        break;
                }
            }
        });
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgUri = getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, ImageEditor.TAKE_PICTURE_FROM_CAMERA);
    }

    public void pickGallery() {
        startActivityForResult(Intent.createChooser(new Intent()
                .setAction(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                , "Select Picture"), ImageEditor.TAKE_PICTURE_FROM_GALLERY);
    }

    public void showDialog() {
        Utils.showConfirmDialog(this,
                Utils.DISABLE,
                R.string.toast_not_have_permission_can_not_proceed,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, false);
    }

    public void defaultImage() {
        Toast.makeText(this, "Default Image", Toast.LENGTH_SHORT).show();
    }

    private Uri getFileUri() {
        File file = new File(FileUtil.getImageTempDir(), "profile0");
        imgPath = file.getAbsolutePath();
        return FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            File file;

            switch (requestCode) {
                case ImageEditor.TAKE_PICTURE_FROM_CAMERA:
                    if (resultCode != RESULT_OK) return;
                    //!modify
                    file = new ImageEditor(imgPath, this).getFileOfFormattedBitmap("profile0", ImageEditor.QUALITY_1080);
                    imgPath = file.getAbsolutePath();
                    break;
                case ImageEditor.TAKE_PICTURE_FROM_GALLERY:
                    if (resultCode != RESULT_OK) return;
                    if (data != null) {
                        try {
                            imgUri = data.getData();

//                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                            final Bitmap bitmap = new ImageEditor(this).getImageResized(imgUri, ImageEditor.QUALITY_960);

                            File dir = FileUtil.getImageTempDir();
                            file = new ImageEditor(this).getFileCacheFromBitmap(bitmap, new File(dir, "profile0"), 100);
                            imgPath = file.getAbsolutePath();
                            bitmap.recycle();
                        } catch (Exception e) {
                            Logger.printStackTrace(e);
                        }
                    }
                    break;
            }

            if (imgPath != null) {
                Intent intent = new Intent();
                intent.putExtra("imgPath", imgPath);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, R.string.toast_failed_import_media_data, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    /**
     * Select Activity용 아답터
     */
    public class SimpleListAdapter extends ArrayAdapter<SpinnerData> {
        private final List<SpinnerData> list;
        private final Activity context;

        public class ViewHolder {
            protected TextView rowDisplayName;
        }

        public SimpleListAdapter(Activity context, List<SpinnerData> list) {
            super(context, R.layout.item_list_select_dialog_row, list);
            this.context = context;
            this.list = list;
        }

        public List<SpinnerData> getList() {
            return list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            if (convertView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                view = inflater.inflate(R.layout.item_list_select_dialog_row, null);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.rowDisplayName = (TextView) view.findViewById(R.id.lblName);
                view.setTag(viewHolder);
            } else {
                view = convertView;
            }

            ViewHolder holder = (ViewHolder) view.getTag();
            holder.rowDisplayName.setText(list.get(position).getDisplayString());
            return view;
        }
    }
}