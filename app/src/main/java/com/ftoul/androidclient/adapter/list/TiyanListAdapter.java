package com.ftoul.androidclient.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.ftoul.androidclient.R;

/**
 * Created by 蜂投网-HzJ on 2017/6/2.
 */

public class TiyanListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public TiyanListAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        if(convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = mInflater.inflate(R.layout.item_tiyan_listview, null);
//            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.listview_ll);
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        convertView = mInflater.inflate(R.layout.item_tiyan_listview, null);
        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.listview_ll);

        if(position%2 == 0) {
            ll.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        return convertView;
    }

    class ViewHolder {
        private LinearLayout ll;
    }
}
