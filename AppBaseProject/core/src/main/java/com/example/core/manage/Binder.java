package com.example.core.manage;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.core.event.OnSingleClickListener;
import com.example.core.manage.annotation.Bind;
import com.example.core.util.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by tigris on 2017-07-28.
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
        public BindManager onClick(int... ids) {
            View[] views =  new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = root.findViewById(ids[i]);
            }
            return onClick(views);
        }
        public BindManager onClick(View... views) {
            return onClick(container, views);
        }
        public BindManager onClick(View.OnClickListener container, int... ids) {
            View[] views =  new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = root.findViewById(ids[i]);
            }
            return onClick(container, views);
        }
        public BindManager onClick(Object container, View... views) {
            if (container instanceof View.OnClickListener ||
                    container instanceof OnSingleClickListener) {
                for (View view : views) {
                    view.setOnClickListener((View.OnClickListener) container);
                }
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
