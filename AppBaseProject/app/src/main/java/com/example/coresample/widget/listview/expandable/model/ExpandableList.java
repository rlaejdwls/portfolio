package com.example.coresample.widget.listview.expandable.model;

import java.util.List;

/**
 * Created by tigris on 2017-09-26.
 */
public class ExpandableList<EG extends TestExpandableGroup> {
    private List<EG> groups;

    public ExpandableList(List<EG> groups) {
        this.groups = groups;
    }

    public boolean isGroup(EG group) {
        return groups.contains(group);
    }
}
