package com.ftoul.androidclient.bean;

import com.ftoul.androidclient.ui.wheelview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Created by tp on 2017/5/25.
 * --省市区实体
 */

public class MultistageBean implements IPickerViewData {
    public int id;
    public String name;
    public int parent_id;
    public ArrayList<MultistageBean> children;

    @Override
    public String toString() {
        return "MultistageBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent_id=" + parent_id +
                ", children=" + children +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public ArrayList<MultistageBean> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<MultistageBean> children) {
        this.children = children;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
