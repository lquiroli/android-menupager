package com.github.lquiroli.menupager.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.github.lquiroli.menupager.widget.SimpleMenuFragmentAdapter;

import java.util.ArrayList;

/**
 * A custom implementation of a {@link com.github.lquiroli.menupager.widget.SimpleMenuFragmentAdapter}. Since we decorated our class {@link com.github.lquiroli.menupager.sample.MenuItem}
 * we only need to implement {@link #getPage(int, java.util.ArrayList)} method
 * <p>Created by lorenzo.quiroli</p>
 */
public class MyFragmentAdapter extends SimpleMenuFragmentAdapter {

    public MyFragmentAdapter(FragmentManager fm, ArrayList items) {
        super(fm, items);
    }

    @Override
    protected Fragment getPage(int pageIndex, ArrayList data) {

        MenuFragment fragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MenuFragment.BUNDLE_DATA, data);
        fragment.setArguments(bundle);

        return fragment;
    }

}
