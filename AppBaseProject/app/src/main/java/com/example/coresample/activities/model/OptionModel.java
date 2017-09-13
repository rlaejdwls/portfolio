package com.example.coresample.activities.model;

import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rlaej on 2017-09-13.
 */
public class OptionModel {
    @Expose private List<SelectItemModel> items;
    @Expose private String[] selected;
    @Expose private String type;
    @Expose private String title;

    public OptionModel(List<SelectItemModel> items, String[] selected, String type, String title) {
        this.items = items;
        this.selected = selected;
        this.type = type;
        this.title = title;
    }

    public List<SelectItemModel> getItems() {
        return items;
    }
    public void setItems(List<SelectItemModel> items) {
        this.items = items;
    }
    public String[] getSelected() {
        return selected;
    }
    public void setSelected(String[] selected) {
        this.selected = selected;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void refresh() {
        if (items == null || selected == null) return;
        for (SelectItemModel item : items) {
            for (int i = 0; i < selected.length; i++) {
                if (item.getId().equals(selected[i])) {
                    item.setChecked(true);
                    break;
                } else {
                    item.setChecked(false);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "OptionModel{" +
                "items=" + items +
                ", selected=" + Arrays.toString(selected) +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
