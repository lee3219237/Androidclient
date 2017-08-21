package com.ftoul.androidclient.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.utils.PerfectClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tp on 2017/5/20.
 * note:劵列表数据源
 */

public class CoupusAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> data;
    private List<Integer> checkValuesList;
    public CoupusAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        data = new ArrayList<>();
        checkValuesList =  new ArrayList<>();
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public List<Integer> getCheckValuesList() {
        return checkValuesList;
    }

    public void setCheckValuesList(List<Integer> checkValuesList) {
        this.checkValuesList = checkValuesList;
    }

    public void refreshUI()
    {
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DeliverListVHolder entity;
        if (convertView == null) {
            entity = new DeliverListVHolder();
            convertView = mInflater
                    .inflate(R.layout.item_list_coupus, null);
            entity.txt_money = (TextView) convertView.findViewById(R.id.txt_money);
            entity.txt_limit = (TextView) convertView.findViewById(R.id.txt_limit);
            entity.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
            entity.frameLayout = (FrameLayout) convertView.findViewById(R.id.framelayout);
            entity.iv_jiaobiao = (ImageView) convertView.findViewById(R.id.iv_jiaobiao);
            convertView.setTag(entity);
        } else {
            entity = (DeliverListVHolder) convertView.getTag();
        }
        entity.frameLayout.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                mOnSelectListener.onSelectd(v,position);
            }
        });
        if (0==checkValuesList.get(position))//未选择
        {
            entity.frameLayout.setPressed(false);
            entity.iv_jiaobiao.setVisibility(View.INVISIBLE);
        }else{//选择
            entity.frameLayout.setPressed(true);
            entity.iv_jiaobiao.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    public interface onSelectListener{
        void onSelectd(View v,int pst);
    }
    private onSelectListener mOnSelectListener;

    public void setmOnSelectListener(onSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
    }

    class DeliverListVHolder {
        private TextView txt_money;
        private TextView txt_limit;
        private TextView txt_date;
        private ImageView iv_jiaobiao;
        private FrameLayout frameLayout;
    }
}
