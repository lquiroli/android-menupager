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
 * Created by lorenzo.quiroli on 07/05/2015.
 */
public class MenuFragment extends Fragment {

    public static final String BUNDLE_DATA = "data";
    private ArrayList<MenuItem> mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = (ArrayList<MenuItem>) getArguments().getSerializable(BUNDLE_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView view = new RecyclerView(getActivity());
        view.setHasFixedSize(true);
        view.setAdapter(new SimpleMenuRecyclerAdapter(mData));
        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }
}
