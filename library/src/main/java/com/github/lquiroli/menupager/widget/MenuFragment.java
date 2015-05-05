package com.github.lquiroli.menupager.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * MenuFragment
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public final class MenuFragment extends Fragment {

    BaseMenuFragmentAdapter mAdapter;
    RecyclerView mRecyclerView;
    List mData;

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRecyclerView = mAdapter.onCreateView(mAdapter.getCurrentPageIndex(), mData, mAdapter.getMenuPager());
        if (mRecyclerView.getAdapter() != null) {
            if (!(mRecyclerView.getAdapter() instanceof MenuPager.Adapter)) {
                throw new ClassCastException("RecyclerView adapter must extend " + MenuPager.Adapter.class.getName());
            } else {
                ((MenuPager.Adapter) mRecyclerView.getAdapter()).menuPagerInternal = mAdapter.getMenuPager();
            }
        }
        return mRecyclerView;

    }

}
