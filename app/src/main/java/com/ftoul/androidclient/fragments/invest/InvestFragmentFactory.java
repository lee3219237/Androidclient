package com.ftoul.androidclient.fragments.invest;


import android.support.v4.app.Fragment;


/**
 * Created by chenxiaoli on 2016/11/4.
 */
public class InvestFragmentFactory {
    /**
     * 根据不同的position生产对应的Fragment对象
     *
     * @param position
     * @return
     */
    public static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new NewUserFragment();
                break;
            case 1:
                fragment = new RegularFragment();
                break;
//            case 2:
//                fragment = new AssignFragment();
//                break;
        }
        return fragment;
    }
}
