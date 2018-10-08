package com.binatestation.kickstart.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RKR on 11-09-2018.
 * MultiSelectDataModel.
 */
public class MultiSelectDataModel {
    private boolean selected;
    private Object item;

    public MultiSelectDataModel(Object item) {
        this.selected = false;
        this.item = item;
    }

    public static ArrayList<MultiSelectDataModel> parseData(List<?> data) {
        ArrayList<MultiSelectDataModel> multiSelectDataModels = new ArrayList<>();
        for (Object object : data) {
            multiSelectDataModels.add(new MultiSelectDataModel(object));
        }
        return multiSelectDataModels;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
