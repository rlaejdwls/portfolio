package com.example.coresample.widget.listview.expandable.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.manage.Binder;
import com.example.coresample.widget.listview.expandable.model.ExpandableChild;
import com.example.coresample.widget.listview.expandable.model.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpandableRecyclerViewAdapter<EG extends ExpandableGroup, EC extends ExpandableChild>
        extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {
    class GroupStatus {
        private int index;
        private boolean isExpanded;

        public GroupStatus(int index, boolean isExpanded) {
            this.index = index;
            this.isExpanded = isExpanded;
        }
    }

    protected Context context;
    protected List<EG> allItems = new ArrayList<>();
    protected List<ExpandableChild> items = new ArrayList<>();
    protected List<GroupStatus> status = new ArrayList<>();

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
        return position;
        //
//        return items.get(position).getLayoutId();
        //
//        if (allItems.contains(items.get(position))) {
//            return TYPE_GROUP;
//        } else {
//            return TYPE_CHILD;
//        }
    }
    public int getLayoutId(int position) {
        return items.get(position).getLayoutId();
    }
    @Override
    public ExpandableRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        if (allItems.contains(items.get(position))) {
            return onGroupCreateViewHolder(parent, position, items.get(position).getLayoutId());
        } else {
            return onChildCreateViewHolder(parent, position, items.get(position).getLayoutId());
        }
    }
//    @Override
//    public void onBindViewHolder(ExpandableRecyclerViewAdapter.ViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//    }

    abstract ExpandableRecyclerViewAdapter.ViewHolder onGroupCreateViewHolder(ViewGroup parent, int position, int layoutId);
    abstract ExpandableRecyclerViewAdapter.ViewHolder onChildCreateViewHolder(ViewGroup parent, int position, int layoutId);

    protected View inflate(int resourceID, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(resourceID, viewGroup, false);
    }

    public void setupItems(List<EG> items) {
        allItems = items;
        List<ExpandableChild> visibleItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            visibleItems.add(items.get(i));
            status.add(new GroupStatus(i, false));
        }
        this.items = visibleItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
            Binder.bind(view, this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v);
                }
            });
        }
        protected void onItemClick(View v) {
            if (allItems.contains(items.get(getAdapterPosition()))) {
//                Toast.makeText(context, "OnClickHeader", Toast.LENGTH_SHORT).show();
                if (!status.get(getAdapterPosition()).isExpanded) {
                    expand(getAdapterPosition());
                } else {
                    collapse(getAdapterPosition());
                }
            }
        }
    }

    public void expand(int index) {
        status.get(index).isExpanded = true;
        List<ExpandableChild> children = allItems.get(index).getChild();
        items.addAll(index + 1, children);
        notifyItemRangeInserted(index + 1, children.size());
    }
    public void collapse(int index) {
        status.get(index).isExpanded = false;
        List<ExpandableChild> children = allItems.get(index).getChild();
        items.removeAll(children);
        notifyItemRangeRemoved(index + 1, children.size());
    }
}
