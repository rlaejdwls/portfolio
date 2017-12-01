package com.example.core.manage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.core.manage.annotation.Bind;
import com.example.core.manage.annotation.Param;
import com.example.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by Hwang on 2017-07-28.
 */
public class Binder {
    private static Binder binder;
    private BindManager bindManager;

    private Binder() {}
    private synchronized static Binder get() {
        if (binder == null) {
            binder = new Binder();
        }
        return binder;
    }
    private BindManager getBindManager(View v, Object container) {
        return new BindManager(v, container);
    }

    public class BindManager {
        private View root;
        private Object container;

        private BindManager(View root, Object container) {
            this.root = root;
            this.container = container;
        }
        public BindManager bind() {
            View view;
            Field[] fields = container.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Bind bind = field.getAnnotation(Bind.class);

                if (bind != null) {
                    int id = root.getContext().getResources().getIdentifier(field.getName(),
                            "id", root.getContext().getPackageName());
                    if (id == 0) {
                        id = root.getContext().getResources().getIdentifier(StringUtils.toAlias(field.getName()),
                                "id", root.getContext().getPackageName());
                    }
                    if (id != 0) {
                        view = root.findViewById(id);

                        try {
                            field.setAccessible(true);
                            field.set(container, view);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return this;
        }
        public BindManager bundle(Intent intent) {
            return bundle(intent.getExtras());
        }
        public BindManager bundle(Bundle bundle) {
            if (bundle != null) {
                Iterator<String> iterator = bundle.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    try {
                        Field field = container.getClass().getDeclaredField(key);
                        field.setAccessible(true);
                        Param param = field.getAnnotation(Param.class);
                        if (param != null) {
                            if (container.getClass().isPrimitive()) {
                                switch (field.getType().getSimpleName()) {
                                    case "boolean":
                                        field.setBoolean(container, bundle.getBoolean(key));
                                        break;
                                    case "int":
                                        field.setInt(container, bundle.getInt(key));
                                        break;
                                    case "long":
                                        field.setLong(container, bundle.getLong(key));
                                        break;
                                    case "float":
                                        field.setFloat(container, bundle.getFloat(key));
                                        break;
                                    case "double":
                                        field.setDouble(container, bundle.getDouble(key));
                                        break;
                                }
                            } else {
                                field.set(container, bundle.get(key));
                            }
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return this;
        }
        public BindManager onClick(int... ids) {
            View[] views =  new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = root.findViewById(ids[i]);
            }
            return onClick(views);
        }
        public BindManager onClick(View... views) {
            if (container instanceof View.OnClickListener) {
                return onClick((View.OnClickListener) container, views);
            }
            return this;
        }
        public BindManager onClick(View.OnClickListener container, int... ids) {
            View[] views =  new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = root.findViewById(ids[i]);
            }
            return onClick(container, views);
        }
        public BindManager onClick(View.OnClickListener container, View... views) {
            for (View view : views) {
                view.setOnClickListener(container);
            }
            return this;
        }
        public BindManager onTouch(int... ids) {
            View[] views =  new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = root.findViewById(ids[i]);
            }
            return onTouch(views);
        }
        public BindManager onTouch(View... views) {
            if (container instanceof View.OnTouchListener) {
                onTouch((View.OnTouchListener) container, views);
            }
            return this;
        }
        public BindManager onTouch(View.OnTouchListener container, View... views) {
            for (View view : views) {
                view.setOnTouchListener(container);
            }
            return this;
        }
    }

    public static BindManager bind(Fragment container) {
        return bind(container.getView(), container);
    }
    public static BindManager bind(Activity container) {
        return bind(container, "activity", true);
    }
    public static BindManager bind(Activity container, String prefix, boolean isSetContentView) {
        if (isSetContentView) {
            container.setContentView(container.getResources().getIdentifier(prefix +
                            StringUtils.toAlias(container.getClass().getSimpleName()
                                    .replace(StringUtils.toAlias("_" + prefix), "")),
                    "layout", container.getPackageName()));
        }
        return bind(container.getWindow().getDecorView(), container);
    }
    public static BindManager bind(View root, Object container) {
        get().bindManager = get().getBindManager(root, container);
        return get().bindManager.bind();
    }
}
