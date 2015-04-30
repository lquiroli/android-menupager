package com.github.lquiroli.menupager.widget;

import android.support.v4.app.Fragment;

/**
 * MenuFragment
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public abstract class MenuFragment extends Fragment {

    private MenuPagerFragmentAdapter mAdapter;

    protected MenuPagerFragmentAdapter getMenuAdapter() {
        return mAdapter;
    }

    public final void setMenuAdapter(MenuPagerFragmentAdapter adapter) {
        mAdapter = adapter;
    }

}
