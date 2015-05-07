package com.github.lquiroli.menupager.sample;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.lquiroli.menupager.widget.MenuPager;
import com.github.lquiroli.menupager.widget.SimpleMenuAdapter;

import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 05/05/2015.
 */
public class MyFragmentAdapter extends SimpleMenuAdapter {

    public MyFragmentAdapter(FragmentManager fm, ArrayList items) {
        super(fm, items);
    }

    @Override
    protected RecyclerView onCreateView(int pageIndex, MenuPager parent) {
        RecyclerView recyclerView = super.onCreateView(pageIndex, parent);
        if (pageIndex != 2)
            recyclerView.setLayoutManager(new GridLayoutManager(parent.getContext(), 2));
        return recyclerView;
    }

    @Override
    protected MenuPager.Adapter onProvideAdapter(int pageIndex, RecyclerView view, ArrayList data) {
        return new ShopAdapter(data);
    }
}
