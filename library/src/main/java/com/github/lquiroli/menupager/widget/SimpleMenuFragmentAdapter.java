package com.github.lquiroli.menupager.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.github.lquiroli.menupager.R;

import java.util.ArrayList;

/**
 * A simple implementation of {@link com.github.lquiroli.menupager.widget.BaseMenuFragmentAdapter}. This class already performs all the lookup operations
 * over the provided collection. Extend this class and override {@link #getPage(int, java.util.ArrayList)} for an easy customization of your layout.
 * <p/>
 * <p>This class assumes that you decorated your collection with the correct annotations to work properly. Please see {@link com.github.lquiroli.menupager.annotation.Collection}
 * and {@link com.github.lquiroli.menupager.annotation.Label} for further details</p>
 * <p>Created by lorenzo.quiroli</p>
 */
public abstract class SimpleMenuFragmentAdapter extends BaseMenuFragmentAdapter {

    public SimpleMenuFragmentAdapter(FragmentManager fm, ArrayList items) {
        super(fm, items);
    }

    public ArrayList getItemCollection(Object item) {
        return ReflectUtils.reflectList(item);
    }

    protected void onForwardAnimation(int oldPageIndex, int newPageIndex, int[] animations) {
        animations[0] = R.anim.menu_pager_current_in;
        animations[1] = R.anim.menu_pager_current_out;
    }

    protected void onBackwardAnimation(int oldPageIndex, int newPageIndex, int[] animations) {
        animations[0] = R.anim.menu_pager_back_in;
        animations[1] = R.anim.menu_pager_back_out;
    }

    public Object getItem(int itemIndex, int pageIndex, int[] pageSelections) {
        return determinePageCollection(pageIndex, pageSelections).get(itemIndex);
    }

    @Override
    protected void onDeletePage(Fragment page) {
        //Do nothing
    }
}
