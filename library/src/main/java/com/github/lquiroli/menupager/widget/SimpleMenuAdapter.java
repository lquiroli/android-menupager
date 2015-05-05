package com.github.lquiroli.menupager.widget;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.github.lquiroli.menupager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleMenuAdapter
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public final class SimpleMenuAdapter extends BaseMenuFragmentAdapter {

    private RecyclerView mRecyclerView;

    public SimpleMenuAdapter(FragmentManager fm, ArrayList items) {
        super(fm, items);
    }

    @Override
    protected RecyclerView onCreateView(int pageIndex, List data, MenuPager parent) {

        mRecyclerView = (RecyclerView) LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_fragment, parent, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));
        mRecyclerView.setHasFixedSize(true);
        SimpleMenuRecyclerAdapter adpt = new SimpleMenuRecyclerAdapter(data);
        mRecyclerView.setAdapter(adpt);
        return mRecyclerView;

    }

}
