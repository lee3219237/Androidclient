package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.XdtDetailActivity;
import com.ftoul.androidclient.bean.response.PersonalAssetsCodeOut;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.MyViewHolder> {


    private int[] imgsRes = {R.mipmap.xinsfl, R.mipmap.xiaodt, R.mipmap.fengjh, R.mipmap.fengjx};
   private List<PersonalAssetsCodeOut.AssetListBean> list;

    private Activity context;

    public PersonalAdapter(Activity context, List<PersonalAssetsCodeOut.AssetListBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_personal, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PersonalAssetsCodeOut.AssetListBean bean = list.get(position);
        holder.llProductItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, XdtDetailActivity.class);
                intent.putExtra(XdtDetailActivity.PRODUCT_NAME, bean.getProductName());
                intent.putExtra(XdtDetailActivity.PRODUCT_ID, bean.getProductId());
                context.startActivity(intent);
            }
        });
        holder.ivLeft.setImageResource(imgsRes[position % imgsRes.length]);
        holder.tvProductName.setText(bean.getProductName());
        holder.tvProductMoney.setText(bean.getTotal());
        if(position==list.size()-1){//最后一条
            holder.viewBottom.setVisibility(View.VISIBLE);
            holder.viewLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void reFresh(List<PersonalAssetsCodeOut.AssetListBean> list){
        this.list =list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_left)
        ImageView ivLeft;
        @BindView(R.id.ll_product_item)
        LinearLayout llProductItem;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_money)
        TextView tvProductMoney;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.view_bottom)
        View viewBottom;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
