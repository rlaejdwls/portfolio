package com.example.coresample.widget.listview.expandable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.core.manage.Binder;
import com.example.core.manage.Logger;
import com.example.core.manage.annotation.Bind;
import com.example.coresample.R;
import com.example.coresample.widget.listview.expandable.event.OnGroupToggleListener;
import com.example.coresample.widget.listview.expandable.event.OnListItemClickListener;
import com.example.coresample.widget.listview.expandable.sample.TestExpandableChild;
import com.example.coresample.widget.listview.expandable.sample.TestExpandableGroup;
import com.example.coresample.widget.listview.expandable.sample.TestExpandableRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigris on 2017-09-26.
 */
public class ExpandableRecyclerViewActivity extends AppCompatActivity {
    private TestExpandableRecyclerViewAdapter adapter;

    @Bind private RecyclerView collection;

    private OnListItemClickListener<TestExpandableGroup> onGroupItemClickListener = (v, position, obj) ->
            Logger.d("Group Click:" + obj.getTitle());
    private OnListItemClickListener<TestExpandableChild> onChildItemClickListener = (v, position, obj) ->
            Logger.d("Child Click:" + obj.getTitle());
    private OnGroupToggleListener<TestExpandableGroup> onGroupToggleListener = (position, item, isExpanded) ->
            Logger.d("Toggle:" + item.getTitle() + ", Is expand:" + isExpanded);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this);

        List<TestExpandableGroup> groups = new ArrayList<>();

        TestExpandableGroup group = new TestExpandableGroup();
        group.setChild(getChildren(1));
        group.setTitle("Group 01");
        group.setLayoutId(R.layout.item_list_exapndable_recycler_view_group);
        groups.add(group);
        group = new TestExpandableGroup();
        group.setChild(getChildren(1 + 4));
        group.setTitle("Group 02");
        group.setLayoutId(R.layout.item_list_exapndable_recycler_view_group);
        groups.add(group);
        group = new TestExpandableGroup();
        group.setChild(getChildren(1 + 4 + 4));
        group.setTitle("Group 03");
        group.setLayoutId(R.layout.item_list_exapndable_recycler_view_group);
        groups.add(group);

        adapter = new TestExpandableRecyclerViewAdapter(this);
        adapter.setupItems(groups);
        adapter.notifyDataSetChanged();
        adapter.setOnGroupItemClickListener(onGroupItemClickListener);
        adapter.setOnChildItemClickListener(onChildItemClickListener);
        adapter.setOnGroupToggleListener(onGroupToggleListener);
        adapter.toggle(group);

        collection.setAdapter(adapter);
        collection.setHasFixedSize(true);
        collection.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<TestExpandableChild> getChildren(int index) {
        List<TestExpandableChild> children = new ArrayList<>();

        TestExpandableChild child;
        for (int i = index; i < index + 4; i++) {
            child = new TestExpandableChild();
            child.setTitle("Child " + String.format("%02d", i));
            child.setLayoutId(R.layout.item_list_exapndable_recycler_view_child);
            children.add(child);
        }
        return children;
    }
}