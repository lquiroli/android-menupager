package com.github.lquiroli.menupager.widget;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.github.lquiroli.menupager.R;

import java.util.ArrayList;

/**
 * MenuPagerFragmentAdapter
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public abstract class MenuPagerFragmentAdapter implements FragmentManager.OnBackStackChangedListener {

    private FragmentManager mFragmentManager;
    private int mCurrentPage = 0;
    private View mContainerView;
    private ArrayList<Integer> mPageSelections;
    private ArrayList mItems;
    private int mBackStackStep = 0;

    public MenuPagerFragmentAdapter(FragmentManager fm, ArrayList items) {
        mItems = items;
        mFragmentManager = fm;
        mFragmentManager.addOnBackStackChangedListener(this);
        mPageSelections = new ArrayList<Integer>();
    }

    protected final MenuFragment getFragmentItem(int pageIndex, ArrayList<Integer> pageSelections) {

        ArrayList pageItems = mItems;

        for (int count = 0; count < pageIndex; count++) {
            pageItems = getData(pageItems.get(pageSelections.get(count)));
        }

        MenuFragment fragment = getItem(pageIndex, pageItems);
        fragment.setMenuAdapter(this);
        return fragment;

    }

    public abstract MenuFragment getItem(int pageIndex, ArrayList data);

    public abstract ArrayList getData(Object item);

    public final void onViewReady(View containerView) {

        mContainerView = containerView;
        mFragmentManager.beginTransaction().add(containerView.getId(), getFragmentItem(0, mPageSelections)).commit();
    }

    public void moveForward(int selection) {

        mPageSelections.add(selection);
        mCurrentPage++;
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        int[] animations = onChangePageAnimation();

        switch (animations.length) {
            case 2:
                fTransaction.setCustomAnimations(animations[0], animations[1]);
                break;
            case 4:
                fTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
                break;

        }
        fTransaction.replace(mContainerView.getId(), getFragmentItem(mCurrentPage, mPageSelections));
        fTransaction.addToBackStack(null).commit();
    }

    public int[] onChangePageAnimation() {

        int[] animations = new int[4];
        animations[0] = R.anim.menu_pager_current_in;
        animations[1] = R.anim.menu_pager_current_out;
        animations[2] = R.anim.menu_pager_back_in;
        animations[3] = R.anim.menu_pager_back_out;

        return animations;

    }

    public void moveBackward() {
        mFragmentManager.popBackStackImmediate();
    }

    @Override
    public void onBackStackChanged() {

        if (mBackStackStep > mFragmentManager.getBackStackEntryCount()) {
            //User went back
            mCurrentPage--;
            mPageSelections.remove(mPageSelections.size() - 1);
        }
        mBackStackStep = mFragmentManager.getBackStackEntryCount();
    }
}

