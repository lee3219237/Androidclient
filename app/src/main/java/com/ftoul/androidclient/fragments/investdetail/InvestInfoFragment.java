package com.ftoul.androidclient.fragments.investdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.XdtRecycleAdapter;
import com.ftoul.androidclient.base.BaseFragment;

/**
 * Created by pengtang on 2017/5/26.
 * 借款人信息
 */
public class InvestInfoFragment extends BaseFragment {

    protected static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
    protected int mFragmentIndex;
    private XdtRecycleAdapter xdtRecycleAdapter;

    public static InvestInfoFragment newInstance(int index) {
        InvestInfoFragment fragment = new InvestInfoFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public InvestInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFragmentIndex = bundle.getInt(BUNDLE_FRAGMENT_INDEX, 0);
        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invest_detail_info, container, false);
        return view;
    }

    @Override
    protected void initData() {
    }
}
