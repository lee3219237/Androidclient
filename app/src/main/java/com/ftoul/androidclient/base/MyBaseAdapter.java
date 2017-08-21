package com.ftoul.androidclient.base;

import android.widget.BaseAdapter;

import com.ftoul.androidclient.utils.Log;

import java.util.List;

/**
 * listview 自定义BaseAdapter
 * Created by ftoul106 on 2016/11/15 0015.
 */

public abstract class MyBaseAdapter<E> extends BaseAdapter {

    protected List<E> list;

    public MyBaseAdapter(List<E> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            Log.e("list==null");
        } else {
            Log.e("list.size" + list.size());
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
