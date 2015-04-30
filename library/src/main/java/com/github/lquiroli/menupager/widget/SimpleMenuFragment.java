package com.github.lquiroli.menupager.widget;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lquiroli.menupager.R;

/**
 * SimpleMenuFragment
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class SimpleMenuFragment extends MenuFragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.simple_menu_fragment, null);
        return mRecyclerView;

    }
}
