package com.example.coresample.widget.listview.expandable.model;

import java.util.List;

/**
 * Created by tigris on 2017-09-27.
 */
public interface ExpandableGroup extends ExpandableChild {
    <EC extends ExpandableChild> List<EC> getChildren();
}
