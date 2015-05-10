package com.github.lquiroli.menupager.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.github.lquiroli.menupager.widget.SimpleMenuFragmentAdapter;

import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 05/05/2015.
 */
public class MyFragmentAdapter extends SimpleMenuFragmentAdapter {

    public MyFragmentAdapter(FragmentManager fm, ArrayList items) {
        super(fm, items);
    }

    @Override
    protected Fragment getPage(int pageIndex, ArrayList data) {

        MenuFragment fragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MenuFragment.BUNDLE_DATA, data);
        fragment.setArguments(bundle);

        return fragment;
    }

}
