package com.github.lquiroli.menupager.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * MenuFragment
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public final class MenuFragment extends Fragment {

    BaseMenuFragmentAdapter mAdapter;
    ArrayList mData;
    MenuPager mMenuPager;
    int mPageIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView mRecyclerView = mAdapter.onCreateView(mPageIndex, mMenuPager);
        mRecyclerView.setAdapter(mAdapter.onProvideAdapter(mPageIndex, mRecyclerView, mData));
        if (mRecyclerView.getAdapter() != null) {
            if (!(mRecyclerView.getAdapter() instanceof MenuPager.Adapter)) {
                throw new ClassCastException("RecyclerView adapter must extend " + MenuPager.Adapter.class.getName());
            } else {
                //TODO serve?
                ((MenuPager.Adapter) mRecyclerView.getAdapter()).menuPagerInternal = mMenuPager;
                ((MenuPager.Adapter) mRecyclerView.getAdapter()).adapterInternal = mAdapter;
            }
        }


        return mRecyclerView;

    }

}
