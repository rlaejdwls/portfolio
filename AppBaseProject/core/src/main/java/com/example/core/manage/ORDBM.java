package com.example.core.manage;

import android.content.Context;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;

/**
 * Created by Hwang on 2017-08-28.
 */
public class ORDBM {
    private final int SCHEMA_VERSION = 1;

    private Context context;
    private Realm realm;
//    private SQLManager sql;

    private ORDBM(Context context) {
        this.context = context;
        Realm.init(context);
        realm = Realm.getInstance(new RealmConfiguration.Builder()
                .name(context.getPackageName() + ".db")
//                .encryptionKey(Base64.encode("".getBytes()/*key.getBytes()*/, Base64.DEFAULT))
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build()
        );
    }

    public interface ORDBFactory {
        ORDBM build(Context context);
    }
    public final static ORDBFactory DEFAULT_FACTORY = new ORDBFactory() {
        @Override
        public ORDBM build(Context context) {
            return new ORDBM(context);
        }
    };
    public abstract static class DefaultCallback
            implements Realm.Transaction.OnSuccess, Realm.Transaction.OnError {
        @Override
        public void onError(Throwable error) {
            Logger.printStackTrace(error);
        }
    }/*
    public class SQLManager<E extends RealmObject> {
        public class DefaultTransaction implements Realm.Transaction {
            private Realm realm;
            private E model;

            public DefaultTransaction(E model) {
                this.model = model;
            }

            @Override
            public void execute(Realm realm) {
                this.realm = realm;
            }

            public void insert() {
                realm.insert(model);
            }
            public void update() {
                realm.insertOrUpdate(model);
            }
//            public void delete() {
//                realm.delete(model);
//            }
        }

        private DefaultTransaction transaction;

        private SQLManager(E model) {
            transaction = new DefaultTransaction(model);
        }

        public DefaultTransaction listener(DefaultCallback callback) {
            realm.executeTransactionAsync(transaction, callback, callback);
            return transaction;
        }
    }*/
    public class DefaultTransaction implements Realm.Transaction {
        private Realm realm;

        @Override
        public void execute(Realm realm) {
            this.realm = realm;
        }
        public void insert(RealmModel object) {
            realm.insert(object);
        }
        public void insert(Collection<? extends RealmModel> objects) {
            realm.insert(objects);
        }
//        public void update() {
//            realm.insertOrUpdate(model);
//        }
//            public void delete() {
//                realm.delete(model);
//            }
    }
    public DefaultTransaction listener(DefaultCallback callback) {
        DefaultTransaction transaction = new DefaultTransaction();
        realm.executeTransactionAsync(transaction, callback, callback);
        return transaction;
    }

    public Realm getRealm() {
        return realm;
    }
//    public <T extends RealmObject> SQLManager insert(T model) {
//        return new SQLManager<>(model);
//    }
//    public <T extends RealmObject> SQLManager update(T model) {
//        return new SQLManager<>(model);
//    }
//    public <T extends RealmObject> SQLManager delete(T model) {
//        return new SQLManager<>(model);
//    }
}
