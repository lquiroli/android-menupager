package com.github.lquiroli.menupager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * MenuPager
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class MenuPager extends FrameLayout {


    private MenuPagerFragmentAdapter mAdapter;
    private Context mContext;

    public MenuPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public MenuPager(Context context) {
        super(context);
        mContext = context;
    }

    public void setAdapter(MenuPagerFragmentAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mAdapter != null) {
            mAdapter.onViewReady(this);
        }

    }

}
