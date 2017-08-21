package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ftoul.androidclient.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 * 资金记录页筛选器
 */

public class PopuWindowScreenAdapter extends RecyclerView.Adapter<PopuWindowScreenAdapter.MyViewHolder> {
    private int check;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<String> list;
    private Activity context;

    public PopuWindowScreenAdapter(Activity context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popuwindow_screen, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (position == check) {
            holder.checkbox.setChecked(true);
            holder.checkbox.setEnabled(false);
        }else{
            holder.checkbox.setChecked(false);
            holder.checkbox.setEnabled(true);
        }
        if(position==1){
            holder.checkbox.setVisibility(View.INVISIBLE);
        }
        holder.checkbox.setText(list.get(position));
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = position;
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox)
        CheckBox checkbox;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
