package com.example.coresample.widget.listview.expandable.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.manage.annotation.Bind;
import com.example.coresample.R;
import com.example.coresample.widget.listview.expandable.adapter.ExpandableRecyclerViewAdapter;
import com.example.coresample.widget.listview.expandable.event.OnListItemClickListener;
import com.example.coresample.widget.listview.expandable.model.ExpandableChild;

/**
 * Created by tigris on 2017-09-27.
 */
public class TestExpandableRecyclerViewAdapter extends ExpandableRecyclerViewAdapter<TestExpandableGroup> {
    private OnListItemClickListener onGroupItemClickListener;
    private OnListItemClickListener onChildItemClickListener;

    public TestExpandableRecyclerViewAdapter(Context context) {
        super(context);
    }

    public void setOnGroupItemClickListener(OnListItemClickListener listener) {
        this.onGroupItemClickListener = listener;
    }
    public void setOnChildItemClickListener(OnListItemClickListener listener) {
        this.onChildItemClickListener = listener;
    }

    @Override
    public int getGroupViewType(int position) {
        return items.get(position).getLayoutId();
    }
    @Override
    public int getChildViewType(int position) {
        return items.get(position).getLayoutId();
    }
    @Override
    public boolean isGroup(int viewType) {
        return viewType == R.layout.item_list_exapndable_recycler_view_group;
    }
    @Override
    public boolean isChild(int viewType) {
        return viewType == R.layout.item_list_exapndable_recycler_view_child;
    }
    @Override
    public ExpandableRecyclerViewAdapter.ViewHolder onGroupCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestExpandableRecyclerViewAdapter.GroupViewHolder(inflate(viewType, parent));
    }
    @Override
    public ExpandableRecyclerViewAdapter.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestExpandableRecyclerViewAdapter.ChildViewHolder(inflate(viewType, parent));
    }
    @Override
    public void onGroupBindViewHolder(ExpandableRecyclerViewAdapter.ViewHolder holder, int position) {
        ((GroupViewHolder) holder).bind((TestExpandableGroup) items.get(position));
    }
    @Override
    public void onChildBindViewHolder(ExpandableRecyclerViewAdapter.ViewHolder holder, int position) {
        ((ChildViewHolder) holder).bind((TestExpandableChild) items.get(position));
    }

    public class GroupViewHolder extends ExpandableRecyclerViewAdapter.ViewHolder {
        @Bind private TextView lblTitle;

        public GroupViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected boolean onGroupItemClick(View v, int position, ExpandableChild item, boolean isExpanded) {
            if (onGroupItemClickListener != null) {
                onGroupItemClickListener.onListItemClick(v, position, item);
            }
            return super.onGroupItemClick(v, position, item, isExpanded);
        }
        public void bind(TestExpandableGroup group) {
            lblTitle.setText(group.getTitle());
        }
    }
    public class ChildViewHolder extends ExpandableRecyclerViewAdapter.ViewHolder {
        @Bind private TextView lblTitle;

        public ChildViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void onChildItemClick(View v, int position, ExpandableChild item) {
            if (onChildItemClickListener != null) {
                onChildItemClickListener.onListItemClick(v, position, item);
            }
        }
        public void bind(TestExpandableChild child) {
            lblTitle.setText(child.getTitle());
        }
    }
}
