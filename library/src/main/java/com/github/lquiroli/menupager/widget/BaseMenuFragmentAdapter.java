package com.github.lquiroli.menupager.widget;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;

import com.github.lquiroli.menupager.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * MenuPagerFragmentAdapter
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public abstract class BaseMenuFragmentAdapter implements Serializable {

    MenuPager mMenuPager;
    int[] mPageSelections;
    MenuFragment mCurrentFragment;
    private FragmentManager mFragmentManager;
    private ArrayList mItems;

    public BaseMenuFragmentAdapter(FragmentManager fm, ArrayList items) {
        mItems = items;
        mFragmentManager = fm;
        mPageSelections = new int[0];
    }

    /*
    PRIVATE FUNCTIONS
     */

    private final MenuFragment getFragmentItem() {
        return getFragmentItem(mPageSelections.length);
    }

    private final MenuFragment getFragmentItem(int pageIndex) {
        ArrayList pageItems = determineCollection(pageIndex);
        MenuFragment fragment = new MenuFragment();
        fragment.mAdapter = this;
        fragment.mData = pageItems;
        return fragment;
    }

    /*
    PROTECTED FUNCTIONS
     */

    protected final ArrayList determineCollection() {
        return determineCollection(mPageSelections.length);
    }

    protected final ArrayList determineCollection(int pageIndex) {

        ArrayList pageItems = mItems;
        Object obj = null;
        for (int count = 0; count < pageIndex; count++) {
            obj = pageItems.get(mPageSelections[count]);
            pageItems = ReflectUtils.reflectList(obj);
        }

        return pageItems;
    }

    protected MenuPager getMenuPager() {
        return mMenuPager;
    }

    protected final boolean hasChildren(int index) {
        return hasChildren(index, mPageSelections.length);
    }

    protected final boolean hasChildren(int index, int pageIndex) {
        Object obj = determineCollection(pageIndex).get(index);
        return hasChildren(obj);
    }

    protected final boolean hasChildren(Object obj) {
        return ReflectUtils.reflectList(obj).size() > 0;
    }

    protected abstract RecyclerView onCreateView(int pageIndex, MenuPager parent);

    protected abstract MenuPager.Adapter onProvideAdapter(int pageIndex, RecyclerView view, ArrayList data);

    protected void onViewReady(MenuPager menuPager) {

        mMenuPager = menuPager;
        MenuFragment fragment = getFragmentItem();
        mCurrentFragment = fragment;
        mFragmentManager.beginTransaction().add(menuPager.getId(), fragment).commit();

    }

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

    public boolean moveBackward() {

        if (mPageSelections.length > 0) {
            MenuFragment fragment = getFragmentItem(mPageSelections.length - 1);
            mCurrentFragment = fragment;
            FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
            int[] animations = new int[2];
            onBackwardAnimation(mPageSelections.length - 1, mPageSelections.length, animations);
            fTransaction.setCustomAnimations(animations[0], animations[1]);
            fTransaction.replace(mMenuPager.getId(), fragment);
            fTransaction.commit();
            int[] newSelections = new int[mPageSelections.length - 1];
            System.arraycopy(mPageSelections, 0, newSelections, 0, newSelections.length);
            mPageSelections = newSelections;
            return true;
        }
        return false;

    }

    public int getCurrentPageIndex() {
        return mPageSelections.length;
    }

    public Object getItem(int index) {
        return getItem(index, mPageSelections.length);
    }

    public Object getItem(int index, int pageIndex) {

        return determineCollection(pageIndex).get(index);

    }

    public void moveForward(int selection) {

        int[] newSelections = new int[mPageSelections.length + 1];
        System.arraycopy(mPageSelections, 0, newSelections, 0, mPageSelections.length);
        newSelections[newSelections.length - 1] = selection;
        mPageSelections = newSelections;
        MenuFragment fragment = getFragmentItem(mPageSelections.length);
        mCurrentFragment = fragment;
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        int[] animations = new int[2];
        onForwardAnimation(mPageSelections.length - 1, mPageSelections.length, animations);
        fTransaction.setCustomAnimations(animations[0], animations[1]);
        fTransaction.replace(mMenuPager.getId(), fragment);
        fTransaction.commit();
    }

}

