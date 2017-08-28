package com.example.coresample.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.core.SampleApp;
import com.example.core.manage.Binder;
import com.example.core.manage.Logger;
import com.example.core.manage.ORDBM;
import com.example.coresample.R;
import com.example.coresample.activities.model.UserObj;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rlaej on 2017-08-26.
 */
public class RealmActivity extends AppCompatActivity implements View.OnClickListener {
//    private Realm realm;
    private List<UserObj> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this)
                .onClick(R.id.btn_select, R.id.btn_insert, R.id.btn_update, R.id.btn_delete);
//        Realm.init(getApplicationContext());
//        realm = Realm.getDefaultInstance();

        users.add(new UserObj(1, "홍길동", 600));
        users.add(new UserObj(2, "안철수", 79));
        users.add(new UserObj(3, "박만수", 31));
        users.add(new UserObj(4, "백종원", 0));
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_select:
                RealmResults<UserObj> results = SampleApp.db().getRealm().where(UserObj.class).between("id", 2, 3).findAll();
                for (UserObj user : results) {
                    Logger.d(user.toString());
                }
                break;
            case R.id.btn_insert:
                SampleApp.db().listener(new ORDBM.DefaultCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                });/*.insert(users)*/;
//                realm.executeTransactionAsync(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm bgRealm) {
//                        bgRealm.insert(users);
//                    }
//                }, new Realm.Transaction.OnSuccess() {
//                    @Override
//                    public void onSuccess() {
//                        // 트랜잭션이 성공하였습니다.
//                        Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                    }
//                }, new Realm.Transaction.OnError() {
//                    @Override
//                    public void onError(Throwable error) {
//                        // 트랜잭션이 실패했고 자동으로 취소되었습니다.
//                        Toast.makeText(RealmActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
            case R.id.btn_update:
                SampleApp.db().getRealm().executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (users.get(1).getAge() == 79) {
                            users.get(1).setAge(80);
                        } else if (users.get(1).getAge() == 80) {
                            users.get(1).setAge(79);
                        }
                        realm.insertOrUpdate(users.get(1));
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        // 트랜잭션이 성공하였습니다.
                        Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        // 트랜잭션이 실패했고 자동으로 취소되었습니다.
                        Toast.makeText(RealmActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_delete:
                SampleApp.db().getRealm().beginTransaction();
                SampleApp.db().getRealm().deleteAll();
                SampleApp.db().getRealm().commitTransaction();
                break;
        }
    }
}
