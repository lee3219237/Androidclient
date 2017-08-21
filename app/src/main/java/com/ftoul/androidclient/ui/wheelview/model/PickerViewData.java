package com.ftoul.androidclient.ui.wheelview.model;

/**
 * Created by tp on 2016/12/8.
 * Tips:
 * ----
 * Updated by on 2016/12/8.
 */

public class PickerViewData implements IPickerViewData {
    private String content;
    private long id;
    public PickerViewData(long id,String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getPickerViewText() {
        return content;
    }
}
