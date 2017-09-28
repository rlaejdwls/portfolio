package com.example.coresample.widget.listview.expandable.event;

import com.example.coresample.widget.listview.expandable.model.ExpandableGroup;

/**
 * Created by rlaej on 2017-09-28.
 */
public interface OnGroupToggleListener<EG extends ExpandableGroup> {
    void onGroupToggle(int position, EG item, boolean isExpanded);
}
