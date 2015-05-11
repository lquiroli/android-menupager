package com.github.lquiroli.menupager.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lquiroli.menupager.widget.SimpleMenuRecyclerAdapter;

import java.util.ArrayList;

/**
 * A simple fragment that renders a menu page
 * <p>Created by lorenzo.quiroli</p>
 */
public class MenuFragment extends Fragment {

    public static final String BUNDLE_DATA = "data";
    private ArrayList<MenuItem> mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We read the parcelable data
        mData = getArguments().getParcelableArrayList(BUNDLE_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //We create the layout
        RecyclerView view = new RecyclerView(getActivity());
        view.setHasFixedSize(true);
        view.setAdapter(new SimpleMenuRecyclerAdapter(mData));
        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }
}
