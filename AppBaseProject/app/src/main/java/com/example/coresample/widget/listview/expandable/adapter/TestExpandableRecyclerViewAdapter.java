package com.example.coresample.widget.listview.expandable.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.manage.annotation.Bind;
import com.example.coresample.R;
import com.example.coresample.widget.listview.expandable.model.TestExpandableChild;
import com.example.coresample.widget.listview.expandable.model.TestExpandableGroup;

/**
 * Created by tigris on 2017-09-27.
 */
public class TestExpandableRecyclerViewAdapter extends ExpandableRecyclerViewAdapter<TestExpandableGroup, TestExpandableChild> {
    public TestExpandableRecyclerViewAdapter(Context context) {
        super(context);
    }
//    @Override
//    public ExpandableRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case R.layout.item_list_exapndable_recycler_view_group:
//                return new TestExpandableRecyclerViewAdapter.GroupViewHolder(inflate(viewType, parent));
//            default:
//                return new TestExpandableRecyclerViewAdapter.ChildViewHolder(inflate(viewType, parent));
//        }
//    }

    @Override
    ExpandableRecyclerViewAdapter.ViewHolder onGroupCreateViewHolder(ViewGroup parent, int position, int layoutId) {
        return new TestExpandableRecyclerViewAdapter.GroupViewHolder(inflate(layoutId, parent));
    }
    @Override
    ExpandableRecyclerViewAdapter.ViewHolder onChildCreateViewHolder(ViewGroup parent, int position, int layoutId) {
        return new TestExpandableRecyclerViewAdapter.ChildViewHolder(inflate(layoutId, parent));
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerViewAdapter.ViewHolder holder, int position) {
        switch (items.get(position).getLayoutId()) {
            case R.layout.item_list_exapndable_recycler_view_group:
                ((GroupViewHolder) holder).bind((TestExpandableGroup) items.get(position));
                break;
            default:
                ((ChildViewHolder) holder).bind((TestExpandableChild) items.get(position));
                break;
        }
    }

    public class GroupViewHolder extends ExpandableRecyclerViewAdapter.ViewHolder {
        @Bind private TextView lblTitle;

        public GroupViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void onItemClick(View v) {
            super.onItemClick(v);
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
        protected void onItemClick(View v) {
            super.onItemClick(v);
        }
        public void bind(TestExpandableChild child) {
            lblTitle.setText(child.getTitle());
        }
    }
}
