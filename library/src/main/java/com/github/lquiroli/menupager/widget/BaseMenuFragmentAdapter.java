package com.github.lquiroli.menupager.widget;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

import com.github.lquiroli.menupager.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * MenuPagerFragmentAdapter
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public abstract class BaseMenuFragmentAdapter implements Serializable {

    FragmentManager mFragmentManager;
    private ArrayList mItems;

    public BaseMenuFragmentAdapter(FragmentManager fm, ArrayList items) {
        mItems = items;
        mFragmentManager = fm;
    }

    /*
    PRIVATE FUNCTIONS
     */

    public MenuFragment getFragmentItem(int pageIndex, int[] pageSelections) {
        ArrayList pageItems = determineCollection(pageIndex, pageSelections);
        MenuFragment fragment = new MenuFragment();
        fragment.mAdapter = this;
        fragment.mData = pageItems;
        return fragment;
    }

    /*
    PROTECTED FUNCTIONS
     */

    protected final ArrayList determineCollection(int pageIndex, int[] pageSelections) {

        ArrayList pageItems = mItems;
        Object obj = null;
        for (int count = 0; count < pageIndex; count++) {
            obj = pageItems.get(pageSelections[count]);
            pageItems = ReflectUtils.reflectList(obj);
        }

        return pageItems;
    }

    protected final boolean hasChildren(int index, int pageIndex, int[] pageSelections) {
        Object obj = determineCollection(pageIndex, pageSelections).get(index);
        return hasChildren(obj);
    }

    protected final boolean hasChildren(Object obj) {
        return ReflectUtils.reflectList(obj).size() > 0;
    }

    protected abstract RecyclerView onCreateView(int pageIndex, MenuPager parent);

    protected abstract MenuPager.Adapter onProvideAdapter(int pageIndex, RecyclerView view, ArrayList data);

    protected void onForwardAnimation(int oldPageIndex, int newPageIndex, int[] animations) {
        animations[0] = R.anim.menu_pager_current_in;
        animations[1] = R.anim.menu_pager_current_out;
    }

    protected void onBackwardAnimation(int oldPageIndex, int newPageIndex, int[] animations) {
        animations[0] = R.anim.menu_pager_back_in;
        animations[1] = R.anim.menu_pager_back_out;
    }

    /*
    PUBLIC FUNCTIONS
     */

    public Object getItem(int itemIndex, int pageIndex, int[] pageSelections) {

        return determineCollection(pageIndex, pageSelections).get(itemIndex);

    }

}

