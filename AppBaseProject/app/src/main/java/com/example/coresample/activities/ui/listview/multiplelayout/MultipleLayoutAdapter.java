package com.example.coresample.activities.ui.listview.multiplelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.event.OnSingleClickListener;
import com.example.core.manage.Binder;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleLayoutAdapter<T> extends RecyclerView.Adapter<MultipleLayoutAdapter.ViewHolder> {
    protected Context context;
    protected List<T> items = new ArrayList<>();

    public MultipleLayoutAdapter(Context context) {
        this.context = context;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
    @Override
    public int getItemViewType(int position) {
        return ((MultipleLayoutObj) items.get(position)).getLayoutId();
    }

    protected View inflate(int resourceID, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(resourceID, viewGroup, false);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
            Binder.bind(view, this);
            view.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    onItemClick(v);
                }
            });
        }
        protected void onItemClick(View v) {
        }
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    public void addItems(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }
    public List<T> getItems() {
        return items;
    }
}
