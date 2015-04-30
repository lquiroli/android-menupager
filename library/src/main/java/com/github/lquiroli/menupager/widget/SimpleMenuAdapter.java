package com.github.lquiroli.menupager.widget;

import android.support.v4.app.FragmentManager;

import com.github.lquiroli.menupager.model.MenuItem;

import java.util.ArrayList;

/**
 * SimpleMenuAdapter
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class SimpleMenuAdapter extends MenuPagerFragmentAdapter {


    public SimpleMenuAdapter(FragmentManager fm, ArrayList items) {
        super(fm, items);

    }

    @Override
    public MenuFragment getItem(int pageIndex, ArrayList data) {

        SimpleMenuFragment fragment = new SimpleMenuFragment();
        return fragment;

    }

    @Override
    public ArrayList getData(Object item) {
        return ((MenuItem) item).getChildren();
    }

}
