package com.example.core.manage;

import android.content.Context;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by Hwang on 2017-08-28.
 */
public class ORDBM {
    private final int SCHEMA_VERSION = 1;

    private Context context;
    private Realm realm;
    private RealmAsyncTask task;

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
    public abstract static class DefaultTransaction<E extends RealmModel> implements Realm.Transaction {
        protected Object object;

        public DefaultTransaction(E object) {
            this.object = object;
        }
        public DefaultTransaction(List<E> object) {
            this.object = object;
        }
    }
    public abstract static class DefaultCallback
            implements Realm.Transaction.OnSuccess, Realm.Transaction.OnError {
        @Override
        public void onSuccess() {
            success();
        }
        @Override
        public void onError(Throwable error) {
            Logger.printStackTrace(error);
            error();
        }
        public abstract void success();
        public void error() {}
    }
    public ORDBM listener(Realm.Transaction transaction, DefaultCallback callback) {
        task = realm.executeTransactionAsync(transaction, callback, callback);
        return this;
    }
    public <E extends RealmModel> RealmQuery<E> where(Class<E> clazz) {
        return realm.where(clazz);
    }
    public void cancel() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }

    private enum Type {
        INSERT,
        INSERT_OR_UPDATE,
        DELETE_ALL
    }

    public <T extends RealmModel> SQLManager insert(T model) {
        return new SQLManager<>(model, Type.INSERT);
    }
    public <T extends RealmObject> SQLManager insert(List<T> model) {
        return new SQLManager<>(model, Type.INSERT);
    }
    public <T extends RealmModel> SQLManager insertOrUpdate(T model) {
        return new SQLManager<>(model, Type.INSERT_OR_UPDATE);
    }
    public <T extends RealmObject> SQLManager insertOrUpdate(List<T> model) {
        return new SQLManager<>(model, Type.INSERT_OR_UPDATE);
    }
    public SQLManager deleteAll() {
        return new SQLManager<>(Type.DELETE_ALL);
    }

    public class SQLManager<E extends RealmModel> {
        private Object model;
        private Type type;

        private SQLManager(Type type) {
            this.type = type;
        }
        private SQLManager(E object, Type type) {
            this.model = object;
            this.type = type;
        }
        private SQLManager(List<E> objects, Type type) {
            this.model = objects;
            this.type = type;
        }

        public class DefaultTransaction implements Realm.Transaction {
            @Override
            public void execute(Realm realm) {
                try {
                    switch (type) {
                        case INSERT:
                            if (model instanceof List) { realm.insert((Collection<? extends RealmModel>) model); } else { realm.insert((RealmModel) model); }
                            break;
                        case INSERT_OR_UPDATE:
                            if (model instanceof List) { realm.insertOrUpdate((Collection<? extends RealmModel>) model); } else { realm.insertOrUpdate((RealmModel) model); }
                            break;
                        case DELETE_ALL:
                            realm.deleteAll();
                            break;
                    }
//                    if (model instanceof List) {
//                        Method method = realm.getClass().getMethod(methodName, Collection.class);
//                        method.invoke(realm, model);
//                    } else if (model instanceof RealmModel) {
//                        Method method = realm.getClass().getMethod(methodName, RealmModel.class);
//                        method.invoke(realm, model);
//                    }
                } catch (Exception e) {
                    Logger.printStackTrace(e);
                    throw new RuntimeException(e);
                }
            }
        }

        private DefaultTransaction transaction;
        private DefaultCallback callback;

        public SQLManager listener(DefaultCallback callback) {
            this.transaction = new DefaultTransaction();
            this.callback = callback;
            return this;
        }
        public void execute() {
            realm.executeTransactionAsync(transaction, callback, callback);
        }
    }
}
