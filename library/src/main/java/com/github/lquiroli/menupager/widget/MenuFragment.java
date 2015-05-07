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
    RecyclerView mRecyclerView;
    ArrayList mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable("adapter") != null) {
                mAdapter = (BaseMenuFragmentAdapter) savedInstanceState.getSerializable("adapter");
            }
            if (savedInstanceState.getSerializable("data") != null) {
                mData = (ArrayList) savedInstanceState.getSerializable("data");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mAdapter != null) {
            mRecyclerView = mAdapter.onCreateView(mAdapter.getCurrentPageIndex(), mAdapter.getMenuPager());
            mRecyclerView.setAdapter(mAdapter.onProvideAdapter(mAdapter.getCurrentPageIndex(), mRecyclerView, mData));
            if (mRecyclerView.getAdapter() != null) {
                if (!(mRecyclerView.getAdapter() instanceof MenuPager.Adapter)) {
                    throw new ClassCastException("RecyclerView adapter must extend " + MenuPager.Adapter.class.getName());
                } else {
                    ((MenuPager.Adapter) mRecyclerView.getAdapter()).menuPagerInternal = mAdapter.getMenuPager();
                    ((MenuPager.Adapter) mRecyclerView.getAdapter()).adapterInternal = mAdapter;
                }
            }
        }

        return mRecyclerView;

    }

}
