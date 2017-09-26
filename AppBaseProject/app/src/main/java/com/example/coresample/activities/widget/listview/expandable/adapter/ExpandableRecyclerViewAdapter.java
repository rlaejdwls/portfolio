package com.example.coresample.activities.widget.listview.expandable.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.event.OnSingleClickListener;
import com.example.core.manage.Binder;
import com.example.coresample.activities.widget.listview.expandable.model.ExpandableGroup;
import com.example.coresample.activities.widget.listview.expandable.model.ExpandableList;
import com.example.coresample.activities.widget.listview.multiplelayout.MultipleLayoutObj;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpandableRecyclerViewAdapter<GVH extends ExpandableRecyclerViewAdapter.GroupViewHolder,
        CVH extends ExpandableRecyclerViewAdapter.ChildViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int GROUP = 0;
    public final int CHILD = 1;

    protected Context context;
    protected ExpandableList list;
    protected List<?> items = new ArrayList<>();

    public ExpandableRecyclerViewAdapter(Context context) {
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
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    protected View inflate(int resourceID, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(resourceID, viewGroup, false);
    }
    protected <EG extends ExpandableGroup> void setItems(List<EG> items) {
        items.clear();
        list = new ExpandableList<>(items);
        notifyDataSetChanged();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        public GroupViewHolder(View view) {
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
    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public ChildViewHolder(View view) {
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
}
