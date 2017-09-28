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

public abstract class ExpandableRecyclerViewAdapter<EG extends ExpandableGroup>
        extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {
    public static final int TYPE_GROUP = -1;
    public static final int TYPE_CHILD = -2;

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
        if (allItems.contains(items.get(position))) {
            return getGroupViewType(position);
        } else {
            return getChildViewType(position);
        }
    }
    public int getGroupViewType(int position) {
        return TYPE_GROUP;
    }
    public int getChildViewType(int position) {
        return TYPE_CHILD;
    }
    public boolean isGroup(int viewType) {
        return viewType == TYPE_GROUP;
    }
    public boolean isChild(int viewType) {
        return viewType == TYPE_CHILD;
    }

    @Override
    public ExpandableRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isGroup(viewType)) {
            return onGroupCreateViewHolder(parent, viewType);
        } else if (isChild(viewType)){
            return onChildCreateViewHolder(parent, viewType);
        }
        throw new IllegalArgumentException("viewType is not valid");
    }
    @Override
    public void onBindViewHolder(ExpandableRecyclerViewAdapter.ViewHolder holder, int position) {
        if (isGroup(getItemViewType(position))) {
            onGroupBindViewHolder(holder, position);
        } else if (isChild(getItemViewType(position))) {
            onChildBindViewHolder(holder, position);
        }
    }

    public abstract ExpandableRecyclerViewAdapter.ViewHolder onGroupCreateViewHolder(ViewGroup parent, int viewType);
    public abstract ExpandableRecyclerViewAdapter.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType);
    public abstract void onGroupBindViewHolder(ExpandableRecyclerViewAdapter.ViewHolder holder, int position);
    public abstract void onChildBindViewHolder(ExpandableRecyclerViewAdapter.ViewHolder holder, int position);

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
            int index = getAdapterPosition();
            if (!(index < 0) && allItems.contains(items.get(index))) {
                if (onGroupItemClick(v, index, items.get(index))) {
                    toggle(index);
                }
            } else if (!(index < 0)) {
                onChildItemClick(v, index, items.get(index));
            }
        }
        protected boolean onGroupItemClick(View v, int position, ExpandableChild item) {
            return true;
        }
        protected void onChildItemClick(View v, int position, ExpandableChild item) {}
    }

    public void toggle(int index) {
        int groupIndex = allItems.indexOf(items.get(index));
        if (!status.get(groupIndex).isExpanded) {
            expand(groupIndex, index);
        } else {
            collapse(groupIndex, index);
        }
    }
    public void expand(int groupIndex, int index) {
        status.get(groupIndex).isExpanded = true;
        List<ExpandableChild> children = allItems.get(groupIndex).getChild();
        items.addAll(index + 1, children);
        notifyItemRangeInserted(index + 1, children.size());
    }
    public void collapse(int groupIndex,int index) {
        status.get(groupIndex).isExpanded = false;
        List<ExpandableChild> children = allItems.get(groupIndex).getChild();
        items.removeAll(children);
        notifyItemRangeRemoved(index + 1, children.size());
    }
}
