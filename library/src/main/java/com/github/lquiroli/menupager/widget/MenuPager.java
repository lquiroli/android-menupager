package com.github.lquiroli.menupager.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * MenuPager
 * Created by lorenzo.quiroli on 29/04/2015.
 */
public class MenuPager extends FrameLayout {

    private BaseMenuFragmentAdapter mAdapter;
    private OnMenuItemClickListener mOnMenuItemClickListener;

    public MenuPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuPager(Context context) {
        super(context);
    }

    public void setAdapter(BaseMenuFragmentAdapter adapter) {
        mAdapter = adapter;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mOnMenuItemClickListener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mAdapter != null) {
            mAdapter.onViewReady(this);
        }

    }

    public interface OnMenuItemClickListener {
        public void onMenuItemClick(View itemView, int index);
    }

    public static abstract class Adapter<VH extends MenuPager.ViewHolder> extends RecyclerView.Adapter<VH> {

        MenuPager menuPagerInternal;
        private RecyclerView mRecyclerView;

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            mRecyclerView = recyclerView;
        }

        @Override
        public void onViewAttachedToWindow(VH holder) {
            super.onViewAttachedToWindow(holder);
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mRecyclerView.getChildAdapterPosition(v);
                    menuPagerInternal.mAdapter.moveForward(index);
                    if (menuPagerInternal.mOnMenuItemClickListener != null) {
                        menuPagerInternal.mOnMenuItemClickListener.onMenuItemClick(v, mRecyclerView.getChildAdapterPosition(v));
                    }
                }
            });
        }

    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
