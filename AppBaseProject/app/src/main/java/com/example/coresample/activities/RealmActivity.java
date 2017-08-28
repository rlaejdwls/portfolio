package com.example.coresample.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.SampleApp;
import com.example.core.manage.Binder;
import com.example.core.manage.ORDBM;
import com.example.core.manage.annotation.Bind;
import com.example.coresample.R;
import com.example.coresample.activities.model.UserObj;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by rlaej on 2017-08-26.
 */
public class RealmActivity extends AppCompatActivity implements View.OnClickListener {
    private List<UserObj> users = new ArrayList<>();

    @Bind private TextView txtResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this)
                .onClick(R.id.btn_select, R.id.btn_insert, R.id.btn_update, R.id.btn_delete);

        users.add(new UserObj(1, "홍길동", 600));
        users.add(new UserObj(2, "안철수", 79));
        users.add(new UserObj(3, "박만수", 31));
        users.add(new UserObj(4, "백종원", 0));
    }
    @Override
    public void onClick(View view) {
        txtResult.setText("");
        switch(view.getId()) {
            case R.id.btn_select:
                RealmResults<UserObj> results = SampleApp.db().where(UserObj.class).between("id", 2, 3).findAll();
                for (UserObj user : results) {
                    txtResult.setText(txtResult.getText() + user.toString() + "\n");
                }
                break;
            case R.id.btn_insert:
                SampleApp.db().insert(users)
                        .listener(new ORDBM.DefaultCallback() {
                            @Override
                            public void success() {
                                Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                RealmResults<UserObj> results = SampleApp.db().where(UserObj.class).between("id", 2, 3).findAll();
                                for (UserObj user : results) {
                                    txtResult.setText(txtResult.getText() + user.toString() + "\n");
                                }
                            }
                        })
                        .execute();

//                SampleApp.db().listener(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        realm.insertOrUpdate(users);
//                    }
//                }, new ORDBM.DefaultCallback() {
//                    @Override
//                    public void success() {
//                        Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                        RealmResults<UserObj> results = SampleApp.db().where(UserObj.class).between("id", 2, 3).findAll();
//                        for (UserObj user : results) {
//                            txtResult.setText(txtResult.getText() + user.toString() + "\n");
//                        }
//                    }
//                });
                break;
            case R.id.btn_update:
                if (users.get(1).getAge() == 79) {
                    users.get(1).setAge(80);
                } else if (users.get(1).getAge() == 80) {
                    users.get(1).setAge(79);
                }

                SampleApp.db().insertOrUpdate(users.get(1))
                        .listener(new ORDBM.DefaultCallback() {
                            @Override
                            public void success() {
                                Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .execute();
//                SampleApp.db().listener(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        if (users.get(1).getAge() == 79) {
//                            users.get(1).setAge(80);
//                        } else if (users.get(1).getAge() == 80) {
//                            users.get(1).setAge(79);
//                        }
//                        realm.insertOrUpdate(users.get(1));
//                    }
//                }, new ORDBM.DefaultCallback() {
//                    @Override
//                    public void success() {
//                        Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
            case R.id.btn_delete:
                SampleApp.db().deleteAll()
                        .execute();
//                SampleApp.db().listener(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        realm.delete(UserObj.class);
//                    }
//                }, new ORDBM.DefaultCallback() {
//                    @Override
//                    public void success() {
//                        Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
        }
    }

    public <E extends RealmModel> void insertOrUpdate(List<E> model) {
        SampleApp.db().listener(new ORDBM.DefaultTransaction<E>(model) {
            @Override
            public void execute(Realm realm) {
                if (object instanceof List) {
                    realm.insertOrUpdate((List<E>) object);
                }
            }
        }, new ORDBM.DefaultCallback() {
            @Override
            public void success() {
                Toast.makeText(RealmActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                RealmResults<UserObj> results = SampleApp.db().where(UserObj.class).between("id", 2, 3).findAll();
                for (UserObj user : results) {
                    txtResult.setText(txtResult.getText() + user.toString() + "\n");
                }
            }
        });
    }
}
