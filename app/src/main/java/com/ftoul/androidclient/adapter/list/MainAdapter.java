package com.ftoul.androidclient.adapter.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ftoul.androidclient.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by ftoul106 on 2017/4/25 0025.
 */

public class MainAdapter<T> extends MyBaseAdapter<T> {

    public MainAdapter(List<T> list) {
        super(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
